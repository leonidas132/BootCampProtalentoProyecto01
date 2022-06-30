package com.protalento.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.protalento.entidades.Usuario;
import com.protalento.enumerados.Alertas;
import com.protalento.interfaces.Constantes;
import com.protalento.jdbc.implementaciones.UsuarioImp;

/**
 * Servlet implementation class ValidarSesion
 */
@WebServlet("/sesion")
public class ValidarSesion extends HttpServlet {
	private static Logger logger = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	private UsuarioImp usuarioImp;

	public ValidarSesion() {
		super();
		usuarioImp = new UsuarioImp();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("get");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String correo = request.getParameter("correo");
		String clave = request.getParameter("clave");
		String pagina = "login.jsp";
		HttpSession sesion = null;
		Usuario usuario = usuarioImp.buscarPorCorreoClave(correo, clave);
		RequestDispatcher requestDispatcher;

		if (usuario != null) {
			if (usuario.getIntentosFallidos() >= Constantes.MAXIMO_INTENTOS_FALLIDOS_USUARIO) {
				request.setAttribute("alerta", Alertas.USUARIO_BLOQUEADO);
				logger.warn(usuario + " " + Alertas.USUARIO_BLOQUEADO);
			} else {
				sesion = request.getSession();
				sesion.setAttribute("usuario", usuario);
				pagina = "index.jsp";
			}
		} else {
			request.setAttribute("alerta", Alertas.CREDENCIALES_INCORRECTAS);
			logger.warn(correo + " " + Alertas.CREDENCIALES_INCORRECTAS);
		}

		requestDispatcher = request.getRequestDispatcher(pagina);
		requestDispatcher.forward(request, response);

	}

}
