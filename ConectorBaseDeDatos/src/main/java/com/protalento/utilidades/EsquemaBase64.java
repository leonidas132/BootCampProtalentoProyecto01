package com.protalento.utilidades;

import java.util.Base64;

import com.protalento.enumerados.BASE_64;

public final class EsquemaBase64 {
	private EsquemaBase64() {

	}

	/**
	 * Codificar o Decodificar en Base 64
	 * 
	 * @param cadena
	 * @param base_64
	 * @return cadena
	 */
	public static String getCadena(String cadena, BASE_64 base_64) {
		switch (base_64) {
		case CODIFICAR:
			return Base64.getEncoder().encodeToString(cadena.getBytes());

		case DECODIFICAR:
			return new String(Base64.getDecoder().decode(cadena));
		}
		return null;
	}

}
