package com.protalento.enumerados;

public enum Alertas {
	CERRAR_SESION("Ha cerrado correctamente la sesion"),
	USUARIO_BLOQUEADO("Usuario Bloqueado, contacte al administrador del sistema"),
	CREDENCIALES_INCORRECTAS("Credenciales incorrectas, intente nuevamente"),
	INSERTAR("Se ha creado correctamente el registro"), 
	MODIFICAR("Se ha actualizado correctamente el registro"),
	ELIMINAR("Se ha eliminado correctamente el registro"), 
	GUARDAR("Se ha guardado correctamente el registro"),
	REGISTRO_NO_ENCONTRADO("No existen registros con esas caracteristicas");

	private String mensaje;

	private Alertas(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

}
