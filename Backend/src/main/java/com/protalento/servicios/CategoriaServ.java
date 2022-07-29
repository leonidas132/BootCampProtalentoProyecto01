package com.protalento.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.protalento.dtos.CategoriaDTO;
import com.protalento.entidades.Categoria;
import com.protalento.excepciones.ExcepcionServicioGenerico;
import com.protalento.jdbc.implementaciones.CategoriaImp;
import com.protalento.servicios.ErrorServicio.TIPO_ERROR;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/categorias")
@Produces(value = MediaType.APPLICATION_JSON)
public class CategoriaServ {
	private CategoriaImp categoriaImp = new CategoriaImp();
	private static Logger logger = LogManager.getLogger();

	@GET
	@Path("/buscarURL")
	public Response buscarURL(@QueryParam(value = "id") Long id) {
		return buscar(id);
	}

	@GET
	@Path("/buscar/{categoriaID}")
	public Response buscarPath(@PathParam(value = "categoriaID") Long id) {
		return buscar(id);
	}

	@GET
	@Path("/listar")
	public Response listar() {

		List<CategoriaDTO> categoriasDTO = categoriaImp.listar().stream()
				.map(categoria -> getCategoriaDTO(categoria, "Registro recuperado exitosamente"))
				.collect(Collectors.toList());

		return Response.ok()
				.entity(categoriasDTO.size() > 0 ? categoriasDTO : ErrorServicio.getError(TIPO_ERROR.LISTA_VACIA, null))
				.build();
	}

	@POST
	@Path("/guardarForm")
	public Response guardarForm(@FormParam("id") Long id, @FormParam("descripcion") String descripcion) {
		return guardar(new CategoriaDTO(id, descripcion, null));
	}

	@POST
	@Path("/guardarJSON")
	@Consumes(value = MediaType.APPLICATION_JSON)
	public Response guardarJSON(CategoriaDTO categoriaDTO) {
		return guardar(categoriaDTO);
	}

	@DELETE
	@Path("/eliminar")
	@Consumes(value = MediaType.APPLICATION_JSON)
	public Response eliminar(CategoriaDTO categoriaDTO) {
		Categoria categoria = null;
		if (categoriaDTO.getId() != null) {
			categoria = categoriaImp.buscarPorID(categoriaDTO.getId());
		}

		boolean elimino = categoriaImp.eliminar(getCategoria(categoriaDTO));

		if (elimino) {
			return Response.ok().entity(getCategoriaDTO(categoria, "Registro Elimnado correctamente")).build();
		}

		return Response.ok().entity(ErrorServicio.getError(TIPO_ERROR.NO_ENCONTRADO, categoriaDTO.toString())).build();
	}

	private Response buscar(Long id) {
		Categoria categoria = categoriaImp.buscarPorID(id);
		logger.info(id + " | " + categoria);
		if (categoria == null) {
			return Response.ok().entity(ErrorServicio.getError(TIPO_ERROR.NO_ENCONTRADO, "id categoria = " + id))
					.build();
		}
		return Response.accepted().entity(getCategoriaDTO(categoria, "Registro recuperado exitosamente")).build();
	}

	private Response guardar(CategoriaDTO categoriaDTO) {
		Categoria categoria = null;
		if (!(categoriaDTO.getId() == null)) {
			categoria = categoriaImp.buscarPorID(categoriaDTO.getId());
		}

		String mensaje = "";
		if (categoria == null) {
			categoria = getCategoria(categoriaDTO);
			categoriaImp.insertar(categoria);
			mensaje = "Registro insertado correctamente";
		} else {
			categoriaImp.modificar(getCategoria(categoriaDTO));
			mensaje = "Registro modificado correctamente";
			categoria = categoriaImp.buscarPorID(categoriaDTO.getId());
		}

		return Response.status(Status.CREATED).entity(getCategoriaDTO(categoria, mensaje)).build();
	}

	private Categoria getCategoria(CategoriaDTO categoriaDTO) {
		if (categoriaDTO == null) {
			throw new ExcepcionServicioGenerico("No debe estar en null CategoriaDTO");
		}
		Categoria categoria = new Categoria();
		categoria.setId(categoriaDTO.getId());
		categoria.setDescripcion(categoriaDTO.getDescripcion());
		return categoria;
	}

	private CategoriaDTO getCategoriaDTO(Categoria categoria, String mensaje) {
		if (categoria == null) {
			throw new ExcepcionServicioGenerico("No debe estar en null categoria");
		}
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setId(categoria.getId());
		categoriaDTO.setDescripcion(categoria.getDescripcion());
		categoriaDTO.setMensaje(mensaje);
		return categoriaDTO;
	}

}
