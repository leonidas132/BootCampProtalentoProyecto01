package com.protalento.enumerados;

//Debe ingresar un formato permitido 'usuario@dominio.ext'
public enum ErroresPatrones {
	CORREO("Debe ingresar un formato permitido 'usuario@dominio.ext'"),
	CLAVE("La clave debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una minúscula y al menos una mayúscula. NO puede tener otros símbolos.");

	private String mensaje;

	ErroresPatrones(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

}
