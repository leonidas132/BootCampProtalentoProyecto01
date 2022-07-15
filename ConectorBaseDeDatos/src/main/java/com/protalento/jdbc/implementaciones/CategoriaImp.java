package com.protalento.jdbc.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.protalento.entidades.Categoria;
import com.protalento.jdbc.AdministradorDeConexionMariaDB;
import com.protalento.jdbc.interfaces.ICategoria;

public class CategoriaImp implements ICategoria {

	private static Logger logger = LogManager.getLogger();
	private PreparedStatement preparedStatementBuscarPorID;
	private PreparedStatement preparedStatementInsertar;
	private PreparedStatement preparedStatementEliminar;
	private PreparedStatement preparedStatementModificar;
	private PreparedStatement preparedStatementListar;
	private AdministradorDeConexionMariaDB administradorDeConexionMariaDB;

	public CategoriaImp() {
		administradorDeConexionMariaDB = AdministradorDeConexionMariaDB.getInstancia();
	}

	public Categoria buscarPorID(Long id) {
		Categoria categoria = null;
		String sql = "select id, descripcion from categorias where id = ?";
		try {
			if (null == preparedStatementBuscarPorID) {
				preparedStatementBuscarPorID = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}
			preparedStatementBuscarPorID.setLong(1, id);

			ResultSet resultSet = preparedStatementBuscarPorID.executeQuery();
			if (resultSet.next()) {
				categoria = new Categoria();
				categoria.setId(id);
				categoria.setDescripcion(resultSet.getString("descripcion"));
			}

			logger.debug(preparedStatementBuscarPorID);
			logger.info(categoria);

		} catch (SQLException e) {
			logger.error(e);
		}

		return categoria;
	}

	public boolean insertar(Categoria categoria) {
		boolean inserto = false;
		String sql = "insert into categorias (descripcion) values (?)";
		try {

			if (null == preparedStatementInsertar) {
				preparedStatementInsertar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS);
			}

			preparedStatementInsertar.setString(1, categoria.getDescripcion());

			inserto = preparedStatementInsertar.executeUpdate() == 1;

			// obtener el autoincremental
			if (inserto) {
				ResultSet resultSet = preparedStatementInsertar.getGeneratedKeys();

				if (resultSet.next()) {
					categoria.setId(resultSet.getLong(1));
				}
			}

			logger.debug(preparedStatementInsertar);
			logger.info(categoria);

		} catch (SQLException e) {
			logger.error(e);
		}

		return inserto;
	}

	public boolean modificar(Categoria categoria) {
		String sql = "update categorias set descripcion = ? where id = ?";
		try {
			if (null == preparedStatementModificar) {
				preparedStatementModificar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}
			preparedStatementModificar.setString(1, categoria.getDescripcion());
			preparedStatementModificar.setLong(2, categoria.getId());

			logger.debug(preparedStatementModificar);
			logger.info(categoria);

			return preparedStatementModificar.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e);
		}

		return false;
	}

	public boolean eliminar(Categoria categoria) {
		String sql = "delete from categorias where id = ?";
		try {
			if (null == preparedStatementEliminar) {
				preparedStatementEliminar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}
			preparedStatementEliminar.setLong(1, categoria.getId());

			logger.debug(preparedStatementEliminar);
			logger.info(categoria);

			return preparedStatementEliminar.executeUpdate() == 1;
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	public List<Categoria> listar() {

		List<Categoria> categorias = new ArrayList<>();
		String sql = "select id, descripcion from categorias";
		try {
			if (null == preparedStatementListar) {
				preparedStatementListar = administradorDeConexionMariaDB.getConexion().prepareStatement(sql);
			}

			ResultSet resultSet = preparedStatementListar.executeQuery();
			while (resultSet.next()) {
				Categoria categoria = new Categoria();
				categoria.setId(resultSet.getLong("id"));
				categoria.setDescripcion(resultSet.getString("descripcion"));
				// agrego el objeto a la lista
				categorias.add(categoria);
			}

			logger.debug(preparedStatementListar);
			logger.info(categorias);

		} catch (SQLException e) {
			logger.error(e);
		}

		return categorias;
	}

	public boolean guardar(Categoria categoria) {
		Categoria categoriaAux = buscarPorID(categoria.getId());

		if (categoriaAux == null) {
			return insertar(categoria);
		}
		return modificar(categoria);
	}

	public static void main(String[] args) {
		System.out.println(new CategoriaImp().listar());
	}

}
