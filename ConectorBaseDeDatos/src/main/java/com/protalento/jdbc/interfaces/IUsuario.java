package com.protalento.jdbc.interfaces;

import com.protalento.entidades.Usuario;

public interface IUsuario extends DAOGenerico<Usuario, String> {
	Usuario buscarPorCorreoClave(String correo, String clave);

	boolean actualizarFechaUltimoAcceso(Usuario usuario);

	boolean actualizarIntentoFallido(Usuario usuario);

}
