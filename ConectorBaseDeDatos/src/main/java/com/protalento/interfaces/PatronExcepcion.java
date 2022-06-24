package com.protalento.interfaces;

import com.protalento.enumerados.ErroresPatrones;

public class PatronExcepcion extends Exception {
	private static final long serialVersionUID = 1L;

	private ErroresPatrones erroresPatrones;

	public PatronExcepcion(ErroresPatrones erroresPatrones) {
		super();
		this.erroresPatrones = erroresPatrones;
	}

	public PatronExcepcion(String mensaje) {
		super(mensaje);
	}

	@Override
	public String getMessage() {
		switch (erroresPatrones) {
		case CORREO:
			return erroresPatrones.getMensaje();

		case CLAVE:
			return erroresPatrones.getMensaje();
		}
		return super.getMessage();
	}

}
