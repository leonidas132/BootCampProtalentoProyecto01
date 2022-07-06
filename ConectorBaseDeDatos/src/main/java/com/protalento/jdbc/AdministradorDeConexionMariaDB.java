package com.protalento.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.protalento.enumerados.BASE_64;
import com.protalento.utilidades.EsquemaBase64;

public final class AdministradorDeConexionMariaDB {

	private static final InputStream PATH = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("basededatos.properties");
	private static Properties propiedades = new Properties();

	public AdministradorDeConexionMariaDB() {
		try {
			propiedades.load(PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConexion() {
		Connection conexion = null;
		try {

			String DRIVER = propiedades.getProperty("db_driver");
			String URL = propiedades.getProperty("db_url");
			String USUARIO = propiedades.getProperty("db_usuario");
			String CLAVE = propiedades.getProperty("db_clave");

			Class.forName(DRIVER);
			conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conexion;
	}

	public String getLlave() {
		return EsquemaBase64.getCadena(propiedades.getProperty("db_llave"), BASE_64.DECODIFICAR);
	}

}
