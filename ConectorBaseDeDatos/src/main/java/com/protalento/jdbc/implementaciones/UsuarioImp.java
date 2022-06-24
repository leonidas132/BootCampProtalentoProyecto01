package com.protalento.jdbc.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.protalento.entidades.Usuario;
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
	private PreparedStatement preparedStatementBuscarPorCorreoClave;
	private AdministradorDeConexionMariaDB administradorDeConexionMariaDB;

	public UsuarioImp() {
		administradorDeConexionMariaDB = new AdministradorDeConexionMariaDB();
	}

	@Override
	public Usuario buscarPorID(String correo) {
		Usuario usuario = null;
		String sql = "select AES_DECRYPT(clave,?) as clave, fechaCreacion, fechaUltimoAcceso  from Usuarios where correo = ?";
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
			}

		} catch (SQLException e) {
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
			preparedStatementInsertar.setString(4, Fechas.getString(usuario.getFechaCreacion()));
			preparedStatementInsertar.setString(5, Fechas.getString(usuario.getFechaUltimoAcceso()));

			return preparedStatementInsertar.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modificar(Usuario usuario) {

		return false;
	}

	@Override
	public boolean eliminar(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario buscarPorCorreoClave(String correo, String clave) {
		Usuario usuario = null;
		String sql = "select fechaCreacion, fechaUltimoAcceso  from Usuarios where correo = ? and AES_DECRYPT(clave,?) = ?";
		try {
			if (null == preparedStatementBuscarPorCorreoClave) {
				preparedStatementBuscarPorCorreoClave = administradorDeConexionMariaDB.getConexion()
						.prepareStatement(sql);
			}

			preparedStatementBuscarPorCorreoClave.setString(1, correo);
			preparedStatementBuscarPorCorreoClave.setString(2, administradorDeConexionMariaDB.getLlave());
			preparedStatementBuscarPorCorreoClave.setString(3, clave);

			ResultSet resultSet = preparedStatementBuscarPorCorreoClave.executeQuery();

			if (resultSet.next()) {
				usuario = new Usuario();
				usuario.setCorreo(correo);
				usuario.setClave(clave);
				usuario.setFechaCreacion(Fechas.getLocalDate(resultSet.getString("fechaCreacion")));
				usuario.setFechaUltimoAcceso(Fechas.getLocalDateTime(resultSet.getString("fechaUltimoAcceso")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usuario;
	}

	public static void main(String[] args) {
		Usuario usuario = new Usuario("user1@educacionit.com", "1234", Fechas.getLocalDate("2022-03-15"),
				LocalDateTime.now());

		System.out.println(usuario);

		IUsuario iUsuario = new UsuarioImp();

		// iUsuario.insertar(usuario);

		System.out.println(iUsuario.buscarPorID("user1@educacionit.com"));// true
		System.out.println(iUsuario.buscarPorID("user1@edcacionit.com"));
		System.out.println(iUsuario.buscarPorCorreoClave("user1@educacionit.com", "1234"));// true
		System.out.println(iUsuario.buscarPorCorreoClave("user1@educaconit.com", "1234"));
		System.out.println(iUsuario.buscarPorCorreoClave("user1@educacionit.com", "124"));

	}
}
