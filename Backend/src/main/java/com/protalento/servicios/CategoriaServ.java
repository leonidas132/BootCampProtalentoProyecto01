package com.protalento.servicios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.protalento.entidades.Categoria;
import com.protalento.jdbc.implementaciones.CategoriaImp;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/categorias")
public class CategoriaServ {
	private CategoriaImp categoriaImp = new CategoriaImp();
	private static Logger logger = LogManager.getLogger();

	@GET
	@Path("/buscarURL")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response buscarURL(@QueryParam(value = "id") Long id) {
		Categoria categoria = categoriaImp.buscarPorID(id);
		logger.info(id + " | " + categoria);
		return Response.accepted().entity(categoria).build();
	}

}
