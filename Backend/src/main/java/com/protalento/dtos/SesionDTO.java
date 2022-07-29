package com.protalento.dtos;

import java.time.LocalDateTime;

public class SesionDTO {
	private UsuarioDTO usuarioDTO;
	private LocalDateTime fechaSesion;

	public SesionDTO(UsuarioDTO usuarioDTO) {
		super();
		this.usuarioDTO = usuarioDTO;
		this.fechaSesion = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "SesionDTO [" + usuarioDTO + ", fechaSesion=" + fechaSesion + "]";
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public LocalDateTime getFechaSesion() {
		return fechaSesion;
	}

	public void setFechaSesion(LocalDateTime fechaSesion) {
		this.fechaSesion = fechaSesion;
	}

}
