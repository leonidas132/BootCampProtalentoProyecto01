package com.protalento.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.protalento.entidades.Categoria;
import com.protalento.enumerados.Alertas;
import com.protalento.jdbc.implementaciones.CategoriaImp;

@WebServlet("/categorias")
public class CategoriaControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger();
	private CategoriaImp categoriaImp;

	public CategoriaControlador() {
		super();
		categoriaImp = new CategoriaImp();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.valueOf(request.getParameter("id"));

		Categoria categoria = new Categoria(id, null);
		request.setAttribute("alerta",
				categoriaImp.eliminar(categoria) ? Alertas.ELIMINAR : Alertas.REGISTRO_NO_ENCONTRADO);
		RequestDispatcher requestDispatcher;
		requestDispatcher = request.getRequestDispatcher("index.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.valueOf(request.getParameter("id"));
		String descripcion = request.getParameter("descripcion");

		RequestDispatcher requestDispatcher;
		Categoria categoria = new Categoria(id, descripcion);
		logger.debug(categoria);

		categoriaImp.guardar(categoria);
		request.setAttribute("alerta", Alertas.GUARDAR);

		requestDispatcher = request.getRequestDispatcher("index.jsp");
		requestDispatcher.forward(request, response);
	}

}
