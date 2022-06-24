package com.protalento.utilidades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.protalento.enumerados.ErroresPatrones;
import com.protalento.interfaces.PatronExcepcion;

public final class Patrones {
	private Patrones() {

	}

	/**
	 * Verifica si tiene un patron de correo electronico
	 * 
	 * @param correo
	 * @return boolean
	 * @throws PatronExcepcion
	 */
	public static boolean esCorreo(String correo) throws PatronExcepcion {
		Pattern patron = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))");
		Matcher comparador = patron.matcher(correo);

		if (!comparador.find()) {
			throw new PatronExcepcion(ErroresPatrones.CORREO);
		}
		return true;
	}

	/**
	 * Verifica si tiene un patron de clave
	 * 
	 * @param clave
	 * @return boolean
	 * @throws PatronExcepcion
	 */
	public static boolean esClave(String clave) throws PatronExcepcion {
		Pattern patron = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
		Matcher comparador = patron.matcher(clave);

		if (!comparador.find()) {
			throw new PatronExcepcion(ErroresPatrones.CLAVE);
		}
		return true;
	}

}
