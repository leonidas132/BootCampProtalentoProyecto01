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
<title>Inicio (Protalento - EducacionIT)</title>
</head>
<body>

	<%
	Alertas alerta = (Alertas) request.getAttribute("alerta");

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
		<a href="categoria.jsp">Agregar Categoria</a> <br> <a
			onclick="cerrarSesion()">Cerrar Sesion</a>
	</div>

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
							<button class="warning" onclick="editar('categoria.jsp',<%=categoria.getId()%>)">Editar</button>
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