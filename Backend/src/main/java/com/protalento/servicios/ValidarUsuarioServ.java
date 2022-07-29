package com.protalento.servicios;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.octaviorobleto.commons.utilities.text.CodeUtils;
import com.octaviorobleto.commons.utilities.text.StringUtils;
import com.protalento.dtos.SesionDTO;
import com.protalento.dtos.UsuarioDTO;
import com.protalento.entidades.Usuario;
import com.protalento.excepciones.ExcepcionServicioGenerico;
import com.protalento.excepciones.PatronExcepcion;
import com.protalento.jdbc.implementaciones.UsuarioImp;
import com.protalento.servicios.ErrorServicio.TIPO_ERROR;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/sesion")
@Produces(MediaType.APPLICATION_JSON)
public class ValidarUsuarioServ {

	private UsuarioImp usuarioImp = new UsuarioImp();
	private static Logger logger = LogManager.getLogger();

	@POST
	@Path("/iniciar")
	public Response validarUsuario(Usuario usuario) {
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

			Map<Object, Object> map = new HashMap<>();
			map.put("url", "http://localhost:8080/Backend/");
			map.put("credenciales", usuarioDTO);
			return Response.ok(map).build();
		}
	}

	@POST
	@Path("/redireccion")
	public Response pruebaDatos(@HeaderParam("User-ID") String userID, @HeaderParam("Session-ID") String sessionID) {

		try {
			return Response.temporaryRedirect(new URI("http://localhost:8080/Backend/")).build();
		} catch (URISyntaxException e) {
			logger.error(e);
			return null;
		}
	}

	@GET
	@Path("/headers")
	public Response getHeader(@Context HttpHeaders httpHeaders) {

		for (Entry<String, List<String>> entry : httpHeaders.getRequestHeaders().entrySet()) {
			logger.debug(entry.getKey() + " " + entry.getValue());
		}

		List<String> autenticacionBasica = httpHeaders.getRequestHeader("authorization");
		String valores = CodeUtils
				.BASE64_Decode(autenticacionBasica.stream().reduce((a, b) -> a.concat(b)).get().replace("Basic ", ""));

		String[] usuarioClave = valores.split(":");
		String usuario = usuarioClave[0];
		String clave = usuarioClave[1];

		logger.debug(usuario + " " + clave);

		return Response.ok().build();
	}

	@POST
	@Path("/redireccion2")
	public Response redireccion2() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", "redireccion2");
		return Response.ok(map).build();
	}

}
