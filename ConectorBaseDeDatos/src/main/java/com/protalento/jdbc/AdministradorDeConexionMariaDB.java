package com.protalento.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.protalento.enumerados.BASE_64;
import com.protalento.utilidades.EsquemaBase64;

// Singleton
public final class AdministradorDeConexionMariaDB {
	private static Logger logger = LogManager.getLogger();
	private String llave;
	private Connection conexion;
	private InputStream directorioPropiedades;
	private Properties propiedades;
	private static AdministradorDeConexionMariaDB administradorDeConexionMariaDB;

	private AdministradorDeConexionMariaDB() {
		setConexion();
	}

	public static AdministradorDeConexionMariaDB getInstancia() {

		if (administradorDeConexionMariaDB == null) {
			administradorDeConexionMariaDB = new AdministradorDeConexionMariaDB();
			logger.info("Se crea la intancia del administradorDeConexionMariaDB");
		}

		return administradorDeConexionMariaDB;
	}

	private void setConexion() {
		setPropiedades();
		setLlave(EsquemaBase64.getCadena(propiedades.getProperty("db_llave"), BASE_64.DECODIFICAR));
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

	}

	private void setPropiedades() {
		propiedades = new Properties();
		directorioPropiedades = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("basededatos.properties");
		try {
			propiedades.load(directorioPropiedades);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Connection getConexion() {
		return conexion;
	}

	private void setLlave(String llave) {
		this.llave = llave;
	}

	public String getLlave() {
		return llave;
	}

}
