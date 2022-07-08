package com.protalento.enumerados;

public enum Alertas {
	CERRAR_SESION("Ha cerrado correctamente la sesion","alertWarning"),
	USUARIO_BLOQUEADO("Usuario Bloqueado, contacte al administrador del sistema","alertDanger"),
	CREDENCIALES_INCORRECTAS("Credenciales incorrectas, intente nuevamente","alertDanger"),
	INSERTAR("Se ha creado correctamente el registro","alertSuccess"),
	MODIFICAR("Se ha actualizado correctamente el registro","alertSuccess"),
	ELIMINAR("Se ha eliminado correctamente el registro","alertDanger"), 
	GUARDAR("Se ha guardado correctamente el registro","alertSuccess"),
	REGISTRO_NO_ENCONTRADO("No existen registros con esas caracteristicas","alertWarning");

	private String mensaje;
	private String claseCSS;

	private Alertas(String mensaje, String claseCSS) {
		this.mensaje = mensaje;
		this.claseCSS = claseCSS;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getClaseCSS() {
		return claseCSS;
	}

}
