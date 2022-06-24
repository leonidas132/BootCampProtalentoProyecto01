package com.protalento.jdbc.interfaces;

import java.util.List;

public interface DAOGenerico<E, K> {
	E buscarPorID(K k);

	boolean insertar(E e);

	boolean modificar(E e);

	boolean eliminar(E e);

	List<E> listar();
}
