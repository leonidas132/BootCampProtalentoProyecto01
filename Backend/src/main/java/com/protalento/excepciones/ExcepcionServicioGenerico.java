package com.protalento.excepciones;

public class ExcepcionServicioGenerico extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionServicioGenerico(String message) {
		super(message);
	}
}
