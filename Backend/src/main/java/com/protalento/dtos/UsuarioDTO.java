package com.protalento.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.octaviorobleto.commons.utilities.time.DateUtils;
import com.protalento.entidades.Usuario;
import com.protalento.excepciones.ExcepcionServicioGenerico;
import com.protalento.excepciones.PatronExcepcion;

public class UsuarioDTO {
	private String correo;
	private String clave;
	@JsonIgnore
	private LocalDate fechaCreacion;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaUltimoAcceso;

	private Byte intentosFallidos;
	private UUID uuid;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(String correo, String clave, LocalDate fechaCreacion, LocalDateTime fechaUltimoAcceso,
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

	public static Usuario getUsuario(UsuarioDTO usuarioDTO) {
		if (usuarioDTO == null) {
			throw new ExcepcionServicioGenerico("No debe estar en null usuarioDTO");
		}
		Usuario usuario = new Usuario();
		try {
			usuario.setCorreo(usuarioDTO.getCorreo());
			usuario.setClave(usuarioDTO.getClave());
			usuario.setFechaCreacion(usuarioDTO.getFechaCreacion());
			usuario.setFechaUltimoAcceso(usuarioDTO.getFechaUltimoAcceso());
			usuario.setIntentosFallidos(usuarioDTO.getIntentosFallidos());
		} catch (PatronExcepcion e) {
			e.printStackTrace();
		}

		return usuario;
	}

	public static UsuarioDTO getUsuarioDTO(Usuario usuario) {
		if (usuario == null) {
			throw new ExcepcionServicioGenerico("No debe estar en null usuario");
		}
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setCorreo(usuario.getCorreo());
		usuarioDTO.setClave(usuario.getClave());
		usuarioDTO.setFechaCreacion(usuario.getFechaCreacion());
		usuarioDTO.setFechaUltimoAcceso(usuario.getFechaUltimoAcceso());
		usuarioDTO.setIntentosFallidos(usuario.getIntentosFallidos());
		return usuarioDTO;
	}

}
