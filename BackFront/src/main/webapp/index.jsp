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
<link rel="icon" href="imagenes/protalento.ico" />
<link rel="stylesheet" href="css/botones.css">
<link rel="stylesheet" href="css/alert.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/tabla.css">

<meta charset="ISO-8859-1">
<title>Inicio (Protalento - EducacionIT)</title>
</head>
<body>

	<%
	Usuario usuario = (Usuario) session.getAttribute("usuario");
	if (null == usuario) {
		response.sendRedirect("login.jsp");
	} else {

		Alertas alerta = (Alertas) request.getAttribute("alerta");
		if (!(null == alerta)) {
	%>
	<div id="alert">
		<a class="alert <%=alerta.getClaseCSS()%>" href="#alert"><%=alerta.getMensaje()%></a>
	</div>

	<%
	}
	%>

	<div>
		<header>
			<h1>CRUD Protalento - <%=session.getAttribute("VERSION") %></h1>
			<h2>
				Bienvenido
				<%=usuario.getCorreo()%></h2>
			<!-- Imagen -->
		</header>
	</div>
	<nav>
		<a href="categoria.jsp">Agregar Categoria</a> <a
			style="float: right;" onclick="cerrarSesion()">Cerrar Sesion</a>
	</nav>

	<div>

		<section>

			<h2>Categorias</h2>

			<%
			CategoriaImp categoriaImp = new CategoriaImp();
			List<Categoria> categorias = categoriaImp.listar();
			%>

			<div>
				<table>
					<tr>
						<th>ID</th>
						<th>Descripcion</th>
						<th>Accion</th>
					</tr>

					<%
					for (Categoria categoria : categorias) {
					%>
					<tr>
						<td><%=categoria.getId()%></td>
						<td><%=categoria.getDescripcion()%></td>
						<td>
							<button class="warning"
								onclick="editar('categoria.jsp',<%=categoria.getId()%>)">Editar</button>
							<button class="danger"
								onclick="eliminar('categorias',<%=("'" + categoria.getId() + "','" + categoria + "'")%>)">Eliminar</button>
						</td>
					</tr>

					<%
					}
					%>

				</table>

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