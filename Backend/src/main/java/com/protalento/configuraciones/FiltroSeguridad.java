package com.protalento.configuraciones;

import java.io.IOException;

import com.octaviorobleto.commons.utilities.text.StringUtils;
import com.protalento.servicios.ErrorServicio;
import com.protalento.servicios.ErrorServicio.TIPO_ERROR;
import com.protalento.servicios.Sesiones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

@Provider
@PreMatching
public class FiltroSeguridad implements ContainerRequestFilter {
	private static Logger logger = LogManager.getLogger();

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String url = requestContext.getUriInfo().getPath();
		String authorization = requestContext.getHeaderString("authorization");
		String userID = requestContext.getHeaderString("USER-ID");

		logger.debug(url);
		logger.debug(authorization);
		logger.debug(userID);
		logger.debug(requestContext.getCookies());

		// if (!url.equalsIgnoreCase("credenciales/validar") &&
		// !requestContext.getMethod().equalsIgnoreCase("GET")) {
		if (!url.equalsIgnoreCase("credenciales/validar")) {
			if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(userID)) {
				requestContext
						.abortWith(Response
								.status(Status.UNAUTHORIZED).entity(ErrorServicio
										.getError(TIPO_ERROR.CREDENCIALES_VACIAS, "USER-ID o authorization faltantes"))
								.type(MediaType.APPLICATION_JSON).build());
			} else if (!Sesiones.verificarSesion(userID, authorization.replace("Bearer ", ""))) {
				requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
						.entity(ErrorServicio.getError(TIPO_ERROR.CREDENCIALES_INCORRECTAS, "USER-ID o authorization"))
						.type(MediaType.APPLICATION_JSON).build());
			}
		}
	}

}
