package com.protalento.entidades;

public class Categoria {
	private Long id;
	private String descripcion;

	public Categoria() {
		super();
	}

	public Categoria(Long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", descripcion=" + descripcion + "]";
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

}
