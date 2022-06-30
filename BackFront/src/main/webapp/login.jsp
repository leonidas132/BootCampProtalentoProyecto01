<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div>ALERTAS</div>
	<div>
		<form action="sesion" method="post">
			<label for="correo">Correo:</label> 
			<input type="email" id="correo"	name="correo" placeholder="usuario@dominio.ext" required />
			<label	for="clave">Clave:</label>
			<input type="password" id="clave" name="clave" placeholder="clave..." required />
			
			<button type="submit">Iniciar Sesion</button>
			<button type="reset">Limpiar Formulario</button>
		</form>
	</div>
</body>
</html>