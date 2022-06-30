package com.protalento.enumerados;

public enum Alertas {
	CERRAR_SESION("Ha cerrado correctamente la sesion"),
	USUARIO_BLOQUEADO("Usuario Bloqueado, contacte al administrador del sistema"),
	CREDENCIALES_INCORRECTAS("Credenciales incorrectas, intente nuevamente");

	private String mensaje;

	private Alertas(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

}
