package com.protalento.dtos;

public class CategoriaDTO {
	private Long id;
	private String descripcion;
	private String mensaje;

	public CategoriaDTO() {
		super();
	}

	public CategoriaDTO(Long id, String descripcion, String mensaje) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "CategoriaDTO [id=" + id + ", descripcion=" + descripcion + ", mensaje=" + mensaje + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
