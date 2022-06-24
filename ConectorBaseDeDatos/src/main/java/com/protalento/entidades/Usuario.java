package com.protalento.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario {
	private String correo;
	private String clave;
	private LocalDate fechaCreacion;
	private LocalDateTime fechaUltimoAcceso;

	public Usuario() {
		super();
	}

	public Usuario(String correo, String clave, LocalDate fechaCreacion, LocalDateTime fechaUltimoAcceso) {
		super();
		this.correo = correo;
		this.clave = clave;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	@Override
	public String toString() {
		return "Usuario [correo=" + correo + ", clave=" + clave + ", fechaCreacion=" + fechaCreacion
				+ ", fechaUltimoAcceso=" + fechaUltimoAcceso + "]";
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

}
