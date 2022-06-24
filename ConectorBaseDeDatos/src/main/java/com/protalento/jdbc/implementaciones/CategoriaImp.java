package com.protalento.jdbc.implementaciones;

import java.util.List;

import com.protalento.entidades.Categoria;
import com.protalento.jdbc.interfaces.ICategoria;

public class CategoriaImp implements ICategoria {

	@Override
	public Categoria buscarPorID(Long id) {

		return null;
	}

	@Override
	public boolean insertar(Categoria categoria) {

		return false;
	}

	@Override
	public boolean modificar(Categoria categoria) {

		return false;
	}

	@Override
	public boolean eliminar(Categoria categoria) {

		return false;
	}

	@Override
	public List<Categoria> listar() {

		return null;
	}

}
