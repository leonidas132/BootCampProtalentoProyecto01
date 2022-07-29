package com.protalento.servicios;

import java.util.UUID;

import com.octaviorobleto.commons.utilities.text.StringUtils;
import com.protalento.dtos.UsuarioDTO;
import com.protalento.entidades.Usuario;
import com.protalento.jdbc.implementaciones.UsuarioImp;
import com.protalento.servicios.ErrorServicio.TIPO_ERROR;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/credenciales")
@Produces(MediaType.APPLICATION_JSON)
public class ValidarSesionServicio {
	private UsuarioImp usuarioImp = new UsuarioImp();
	private static Logger logger = LogManager.getLogger();

	@Path("/validar")
	@POST
	public Response verificarUsuario(Usuario usuario) {
		logger.debug(usuario);

		if (StringUtils.isEmpty(usuario.getCorreo()) || StringUtils.isEmpty(usuario.getClave())) {
			return Response.ok().entity(ErrorServicio.getError(TIPO_ERROR.CREDENCIALES_VACIAS, null)).build();
		}
		Usuario usuarioAux = usuarioImp.buscarPorCorreoClave(usuario.getCorreo(), usuario.getClave());
		if (usuarioAux == null) {
			return Response.ok().entity(ErrorServicio.getError(TIPO_ERROR.CREDENCIALES_INCORRECTAS, null)).build();
		} else {
			UsuarioDTO usuarioDTO = UsuarioDTO.getUsuarioDTO(usuarioAux);
			usuarioDTO.setUuid(UUID.randomUUID());
			Sesiones.crearSesion(usuarioDTO);
			return Response.ok(usuarioDTO).build();
		}
	}

	@GET
	@Path("/ping")
	public Response ping() {
		return Response.ok("pong").build();
	}

}
