<%@page import="com.protalento.jdbc.implementaciones.CategoriaImp"%>
<%@page import="com.protalento.entidades.Categoria"%>
<%@page import="java.util.List"%>
<%@page import="com.protalento.enumerados.Alertas"%>
<%@page import="com.protalento.entidades.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/botones.css">
<meta charset="ISO-8859-1">
<title>Categorias (Protalento - EducacionIT)</title>
</head>
<body>

	<%
	Alertas alerta = (Alertas) request.getAttribute("alerta");

	String id = request.getParameter("id");
	Categoria categoria = null;
	CategoriaImp categoriaImp = null;

	if (id != null) {
		categoriaImp = new CategoriaImp();
		categoria = categoriaImp.buscarPorID(Long.valueOf(id));
	}

	Usuario usuario = (Usuario) session.getAttribute("usuario");
	if (null == usuario) {
		response.sendRedirect("login.jsp");
	} else {
	%>

	<div>
		<%=(null == alerta ? "" : alerta.getMensaje())%>
	</div>

	<div>
		<header>
			<h1>CRUD Protalento</h1>
			<h2>
				Bienvenido
				<%=usuario.getCorreo()%></h2>
			<!-- Imagen -->
		</header>
	</div>
	<div>
		<a href="index.jsp">Inicio</a> <br> <a onclick="cerrarSesion()">Cerrar
			Sesion</a>
	</div>

	<div>

		<section>

			<h2>Categorias</h2>


			<div>
				<form action="categorias" method="post">

					<input type="hidden" id="id" name="id"
						value="<%=categoria != null ? categoria.getId() : 0%>"> <label
						for="descripcion">Descripcion</label> <input type="text"
						id="descripcion" name="descripcion"
						placeholder="Descripcion Categoria" required
						<%=categoria != null ? "value=\"" + categoria.getDescripcion() + "\"" : 0%>>
					<button class="success" type="submit"><%=categoria != null ? "Modificar" : "Agregar"%></button>
					<button class="warning" type="reset">Limpiar</button>

				</form>
			</div>
		</section>

	</div>

	<div>
		<footer>

			<p>Creado por Bootcamp Protalento - Educacion IT</p>
		</footer>

	</div>


	<%
	}
	%>
	<script src="scripts/confirmar.js" type="text/javascript"></script>
</body>
</html>