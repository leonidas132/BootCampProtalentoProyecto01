package com.protalento.jdbc.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.protalento.entidades.Usuario;
import com.protalento.excepciones.PatronExcepcion;
import com.protalento.jdbc.AdministradorDeConexionMariaDB;
import com.protalento.jdbc.interfaces.IUsuario;
import com.protalento.utilidades.Fechas;

// SOLID
public class UsuarioImp implements IUsuario {

	private PreparedStatement preparedStatementBuscarPorID;
	private PreparedStatement preparedStatementInsertar;
	private PreparedStatement preparedStatementEliminar;
	private PreparedStatement preparedStatementModificar;
	private PreparedStatement preparedStatementListar;
	private PreparedStatement preparedStatementActualizarFechaUltimoAcceso;
	private PreparedStatement preparedStatementActualizarIntentoFallido;

	private AdministradorDeConexionMariaDB administradorDeConexionMariaDB;

	public UsuarioImp() {
		administradorDeConexionMariaDB = new AdministradorDeConexionMariaDB();
	}

	@Override
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

		} catch (SQLException | PatronExcepcion e) {
			e.printStackTrace();
		}

		return usuario;
	}

	@Override
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

			return preparedStatementInsertar.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modificar(Usuario usuario) {
		String sql = "update usuarios set clave = AES_ENCRYPT(?,?) where correo = ?";
		try {
			if (null == preparedStatementModificar) {
				preparedStatementModificar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}
			preparedStatementModificar.setString(1, usuario.getClave());
			preparedStatementModificar.setString(2, administradorDeConexionMariaDB.getLlave());
			preparedStatementModificar.setString(3, usuario.getCorreo());

			return preparedStatementModificar.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean eliminar(Usuario usuario) {
		String sql = "delete from usuarios where correo = ?";
		try {
			if (null == preparedStatementEliminar) {
				preparedStatementEliminar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}
			preparedStatementEliminar.setString(1, usuario.getCorreo());

			return preparedStatementEliminar.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Usuario> listar() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "select correo, AES_DECRYPT(clave,?) as clave, fechaCreacion, fechaUltimoAcceso, intentosFallidos  from Usuarios";
		try {
			if (null == preparedStatementListar) {
				preparedStatementListar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}

			preparedStatementListar.setString(1, administradorDeConexionMariaDB.getLlave());

			ResultSet resultSet = preparedStatementListar.executeQuery();

			if (resultSet.next()) {
				Usuario usuario = new Usuario();
				usuario.setCorreo(resultSet.getString("correo"));
				usuario.setClave(resultSet.getString("clave"));
				usuario.setFechaCreacion(Fechas.getLocalDate(resultSet.getString("fechaCreacion")));
				usuario.setFechaUltimoAcceso(Fechas.getLocalDateTime(resultSet.getString("fechaUltimoAcceso")));
				usuario.setIntentosFallidos(resultSet.getByte("intentosFallidos"));
				// agregamos a la lista
				usuarios.add(usuario);
			}

		} catch (SQLException | PatronExcepcion e) {
			e.printStackTrace();
		}

		return usuarios;
	}

	@Override
	public Usuario buscarPorCorreoClave(String correo, String clave) {

		Usuario usuario = buscarPorID(correo);

		if (null != usuario && usuario.getClave().equals(clave)) {
			// nos aseguramos que actualizo la informacion
			if (actualizarFechaUltimoAcceso(usuario)) {
				usuario = buscarPorID(correo);
			}
			return usuario;
		} else if (null != usuario && !usuario.getClave().equals(clave)) {
			actualizarIntentoFallido(usuario);
		}
		return null;
	}

	@Override
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

			return preparedStatementActualizarFechaUltimoAcceso.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean actualizarIntentoFallido(Usuario usuario) {
		String sql = "update usuarios set intentosFallidos = (intentosFallidos + 1) where correo = ? ";
		try {
			if (null == preparedStatementActualizarIntentoFallido) {
				preparedStatementActualizarIntentoFallido = administradorDeConexionMariaDB.getConexion()
						.prepareStatement(sql);
			}
			preparedStatementActualizarIntentoFallido.setString(1, usuario.getCorreo());

			return preparedStatementActualizarIntentoFallido.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		Usuario usuario;
		try {
			usuario = new Usuario("user3@educacionit.com", "User3.1234", Fechas.getLocalDate("1999-06-01"),
					LocalDateTime.now(), (byte) 0);

			System.out.println(usuario);

			IUsuario iUsuario = new UsuarioImp();

			iUsuario.insertar(usuario);

			System.out.println(iUsuario.buscarPorID("user1@educacionit.com"));// true
			System.out.println(iUsuario.buscarPorID("user1@edcacionit.com"));
			System.out.println(iUsuario.buscarPorCorreoClave("user1@educacionit.com", "1234"));
			System.out.println(iUsuario.buscarPorCorreoClave("user1@educaconit.com", "1234"));
			System.out.println(iUsuario.buscarPorCorreoClave("user1@educacionit.com", "User1.1234"));
		} catch (PatronExcepcion e) {
			e.printStackTrace();
		}

	}
}
