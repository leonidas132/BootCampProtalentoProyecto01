package com.protalento.entidades;

public final class Error {
	private int codigo;
	private String mensaje;

	public Error() {
		super();
	}

	public Error(int codigo, String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "Error [codigo=" + codigo + ", mensaje=" + mensaje + "]";
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
