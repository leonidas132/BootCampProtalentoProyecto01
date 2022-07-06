package com.protalento.jdbc.interfaces;

import java.util.List;

public interface DAOGenerico<E, K> {
	E buscarPorID(K k);

	boolean insertar(E e);

	boolean modificar(E e);

	boolean eliminar(E e);

	/**
	 * Este metodo dtendra la logica de verificar si existe el objeto lo modifica
	 * sino lo inserta aprovechando los metodos ya existentes
	 * 
	 * @param e
	 * @return
	 */
	boolean guardar(E e);

	List<E> listar();
}
