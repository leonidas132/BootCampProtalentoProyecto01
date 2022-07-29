package com.protalento.jdbc.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.protalento.entidades.Usuario;
import com.protalento.excepciones.PatronExcepcion;
import com.protalento.jdbc.AdministradorDeConexionMariaDB;
import com.protalento.jdbc.interfaces.IUsuario;
import com.protalento.utilidades.Fechas;

// SOLID
public class UsuarioImp implements IUsuario {

	private static Logger logger = LogManager.getLogger();
	private PreparedStatement preparedStatementBuscarPorID;
	private PreparedStatement preparedStatementInsertar;
	private PreparedStatement preparedStatementEliminar;
	private PreparedStatement preparedStatementModificar;
	private PreparedStatement preparedStatementListar;
	private PreparedStatement preparedStatementActualizarFechaUltimoAcceso;
	private PreparedStatement preparedStatementActualizarIntentoFallido;

	private AdministradorDeConexionMariaDB administradorDeConexionMariaDB;

	public UsuarioImp() {
		administradorDeConexionMariaDB = AdministradorDeConexionMariaDB.getInstancia();
	}

	public Usuario buscarPorID(String correo) {
		Usuario usuario = null;
		String sql = "select AES_DECRYPT(clave,?) as clave, fechaCreacion, fechaUltimoAcceso, intentosFallidos  from Usuarios where correo = ?";
		try {
			if (null == preparedStatementBuscarPorID) {
				preparedStatementBuscarPorID = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}

			preparedStatementBuscarPorID.setString(1, administradorDeConexionMariaDB.getLlave());
			preparedStatementBuscarPorID.setString(2, correo);

			ResultSet resultSet = preparedStatementBuscarPorID.executeQuery();

			if (resultSet.next()) {
				usuario = new Usuario();
				usuario.setCorreo(correo);
				usuario.setClave(resultSet.getString("clave"));
				usuario.setFechaCreacion(Fechas.getLocalDate(resultSet.getString("fechaCreacion")));
				usuario.setFechaUltimoAcceso(Fechas.getLocalDateTime(resultSet.getString("fechaUltimoAcceso")));
				usuario.setIntentosFallidos(resultSet.getByte("intentosFallidos"));
			}

			logger.debug(preparedStatementBuscarPorID);

			logger.info(usuario);

		} catch (SQLException | PatronExcepcion e) {
			logger.error(e);
		}

		return usuario;
	}

	public boolean insertar(Usuario usuario) {
		String sql = "insert into Usuarios (correo,clave,fechaCreacion,FechaUltimoAcceso) values (?,AES_ENCRYPT(?,?),?,?)";
		try {
			if (null == preparedStatementInsertar) {
				preparedStatementInsertar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);

			}
			preparedStatementInsertar.setString(1, usuario.getCorreo());
			preparedStatementInsertar.setString(2, usuario.getClave());
			preparedStatementInsertar.setString(3, administradorDeConexionMariaDB.getLlave());
			preparedStatementInsertar.setString(4, Fechas.getString(LocalDate.now()));
			preparedStatementInsertar.setString(5, Fechas.getString(LocalDateTime.now()));

			logger.debug(preparedStatementInsertar);
			logger.info(usuario);

			return preparedStatementInsertar.executeUpdate() == 1;

		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	public boolean modificar(Usuario usuario) {
		String sql = "update usuarios set clave = AES_ENCRYPT(?,?) where correo = ?";
		try {
			if (null == preparedStatementModificar) {
				preparedStatementModificar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}
			preparedStatementModificar.setString(1, usuario.getClave());
			preparedStatementModificar.setString(2, administradorDeConexionMariaDB.getLlave());
			preparedStatementModificar.setString(3, usuario.getCorreo());

			logger.debug(preparedStatementModificar);
			logger.info(usuario);
			return preparedStatementModificar.executeUpdate() == 1;

		} catch (SQLException e) {
			logger.error(e);
		}

		return false;
	}

	public boolean eliminar(Usuario usuario) {
		String sql = "delete from usuarios where correo = ?";
		try {
			if (null == preparedStatementEliminar) {
				preparedStatementEliminar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}
			preparedStatementEliminar.setString(1, usuario.getCorreo());

			logger.debug(preparedStatementEliminar);
			logger.info(usuario);
			return preparedStatementEliminar.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	public List<Usuario> listar() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "select correo, AES_DECRYPT(clave,?) as clave, fechaCreacion, fechaUltimoAcceso, intentosFallidos  from Usuarios";
		try {
			if (null == preparedStatementListar) {
				preparedStatementListar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}

			preparedStatementListar.setString(1, administradorDeConexionMariaDB.getLlave());

			ResultSet resultSet = preparedStatementListar.executeQuery();

			while (resultSet.next()) {
				Usuario usuario = new Usuario();
				usuario.setCorreo(resultSet.getString("correo"));
				usuario.setClave(resultSet.getString("clave"));
				usuario.setFechaCreacion(Fechas.getLocalDate(resultSet.getString("fechaCreacion")));
				usuario.setFechaUltimoAcceso(Fechas.getLocalDateTime(resultSet.getString("fechaUltimoAcceso")));
				usuario.setIntentosFallidos(resultSet.getByte("intentosFallidos"));
				// agregamos a la lista
				usuarios.add(usuario);
			}

			logger.debug(preparedStatementListar);
			logger.info(usuarios);

		} catch (SQLException | PatronExcepcion e) {
			logger.error(e);
		}

		return usuarios;
	}

	public Usuario buscarPorCorreoClave(String correo, String clave) {

		Usuario usuario = buscarPorID(correo);

		if (null != usuario && usuario.getClave().equals(clave)) {
			actualizarFechaUltimoAcceso(usuario);
		} else if (null != usuario && !usuario.getClave().equals(clave)) {
			actualizarIntentoFallido(usuario);
		}
		usuario = buscarPorID(correo);
		return usuario;
	}

	public boolean actualizarFechaUltimoAcceso(Usuario usuario) {
		String sql = "update usuarios set fechaUltimoAcceso = ?, intentosFallidos = ? where correo = ?";
		try {
			if (null == preparedStatementActualizarFechaUltimoAcceso) {
				preparedStatementActualizarFechaUltimoAcceso = administradorDeConexionMariaDB.getConexion()
						.prepareStatement(sql);
			}
			preparedStatementActualizarFechaUltimoAcceso.setString(1, Fechas.getString(LocalDateTime.now()));
			preparedStatementActualizarFechaUltimoAcceso.setByte(2, (byte) 0);
			preparedStatementActualizarFechaUltimoAcceso.setString(3, usuario.getCorreo());

			logger.debug(preparedStatementActualizarFechaUltimoAcceso);
			logger.info(usuario);

			return preparedStatementActualizarFechaUltimoAcceso.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e);
		}

		return false;
	}

	public boolean actualizarIntentoFallido(Usuario usuario) {
		String sql = "update usuarios set intentosFallidos = (intentosFallidos + 1) where correo = ? ";
		try {
			if (null == preparedStatementActualizarIntentoFallido) {
				preparedStatementActualizarIntentoFallido = administradorDeConexionMariaDB.getConexion()
						.prepareStatement(sql);
			}
			preparedStatementActualizarIntentoFallido.setString(1, usuario.getCorreo());

			logger.debug(preparedStatementActualizarIntentoFallido);
			logger.info(usuario);

			return preparedStatementActualizarIntentoFallido.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	public boolean guardar(Usuario usuario) {
		Usuario usuarioAux = buscarPorID(usuario.getCorreo());
		if (usuarioAux == null) {
			return insertar(usuarioAux);
		}
		return modificar(usuario);
	}

	public static void main(String[] args) {

		Usuario usuario;
		try {
			usuario = new Usuario("user2@educacionit.com", "User2.1234", Fechas.getLocalDate("1999-06-01"),
					LocalDateTime.now(), (byte) 0);

			System.out.println(usuario);

			UsuarioImp iUsuario = new UsuarioImp();

			iUsuario.insertar(usuario);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * 
		 * Usuario usuario; try {
		 * 
		 * 
		 * usuario = new Usuario("user5@educacionit.com", "User5.1234",
		 * Fechas.getLocalDate("1999-06-01"), LocalDateTime.now(), (byte) 0);
		 * 
		 * System.out.println(usuario);
		 * 
		 * UsuarioImp iUsuario = new UsuarioImp();
		 * 
		 * iUsuario.insertar(usuario);
		 * 
		 * iUsuario.buscarPorID("user1@educacionit.com");// true
		 * iUsuario.buscarPorID("user1@edcacionit.com");
		 * iUsuario.buscarPorCorreoClave("user1@educacionit.com", "1234");
		 * iUsuario.buscarPorCorreoClave("user1@educaconit.com", "1234");
		 * iUsuario.buscarPorCorreoClave("user1@educacionit.com", "User1.1234");
		 * 
		 * System.out.println(iUsuario.listar()); } catch (PatronExcepcion e) {
		 * e.printStackTrace(); }
		 */
	}

}
