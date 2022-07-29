package com.protalento.servicios;

import java.util.HashMap;
import java.util.Map;

import com.octaviorobleto.commons.utilities.text.StringUtils;
import com.protalento.entidades.Error;

public final class ErrorServicio {
	public static enum TIPO_ERROR {
		NO_ENCONTRADO, LISTA_VACIA, CREDENCIALES_VACIAS, CREDENCIALES_INCORRECTAS
	}

	public static Error getError(TIPO_ERROR tipo_error, String mensaje) {
		Map<TIPO_ERROR, Error> errores = new HashMap<>();
		errores.put(TIPO_ERROR.NO_ENCONTRADO,
				new Error(1, "El registro no existe" + (StringUtils.isEmpty(mensaje) ? "" : " | " + mensaje)));
		errores.put(TIPO_ERROR.LISTA_VACIA, new Error(2,
				"No existen registros para listar" + (StringUtils.isEmpty(mensaje) ? "" : " | " + mensaje)));
		errores.put(TIPO_ERROR.CREDENCIALES_VACIAS,
				new Error(3, "Credenciales Vacias, por favor verifique la informacion suministrada"
						+ (StringUtils.isEmpty(mensaje) ? "" : " | " + mensaje)));
		errores.put(TIPO_ERROR.CREDENCIALES_INCORRECTAS,
				new Error(3, "Credenciales Incorrectas " + (StringUtils.isEmpty(mensaje) ? "" : " | " + mensaje)));
		return errores.get(tipo_error);
	}
}
