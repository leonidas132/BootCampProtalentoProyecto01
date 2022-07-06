<%@page import="com.protalento.enumerados.Alertas"%>
<%@page import="com.protalento.entidades.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/formulario.css">
<link rel="stylesheet" href="css/botones.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<title>Login (Protalento - EducacionIT)</title>
</head>
<body>


	<%
	Usuario usuario = (Usuario) session.getAttribute("usuario");
	if (null != usuario)
		response.sendRedirect("index.jsp");

	Alertas alerta = (Alertas) request.getAttribute("alerta");
	%>
	<div>
		<%=(null == alerta ? "" : alerta.getMensaje())%>

	</div>
	<div>
		<form action="sesion" method="post">
			<label for="correo">Correo:</label> <input type="email" id="correo"
				name="correo" placeholder="usuario@dominio.ext" required /> <label
				for="clave">Clave:</label> <input type="password" id="clave"
				name="clave" placeholder="clave..." required /> <i
				class="bi-eye-slash" id="verClave"></i>

			<button type="submit" class="success">Iniciar Sesion</button>
			<button type="reset" class="warning">Limpiar Formulario</button>
		</form>
	</div>

	<script type="text/javascript" src="scripts/clave.js"></script>
</body>
</html>