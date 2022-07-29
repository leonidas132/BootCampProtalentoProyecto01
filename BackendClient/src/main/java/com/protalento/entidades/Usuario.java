package com.protalento.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Usuario {
	private String correo;
	private String clave;
	private LocalDate fechaCreacion;
	private LocalDateTime fechaUltimoAcceso;
	private Byte intentosFallidos;
	private UUID uuid;

	public Usuario() {
		super();
	}

	public Usuario(String correo, String clave, LocalDate fechaCreacion, LocalDateTime fechaUltimoAcceso,
			Byte intentosFallidos, UUID uuid) {
		super();
		this.correo = correo;
		this.clave = clave;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimoAcceso = fechaUltimoAcceso;
		this.intentosFallidos = intentosFallidos;
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [correo=" + correo + ", clave=" + clave + ", fechaCreacion=" + fechaCreacion
				+ ", fechaUltimoAcceso=" + fechaUltimoAcceso + ", intentosFallidos=" + intentosFallidos + ", uuid="
				+ uuid + "]";
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

	public Byte getIntentosFallidos() {
		return intentosFallidos;
	}

	public void setIntentosFallidos(Byte intentosFallidos) {
		this.intentosFallidos = intentosFallidos;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
