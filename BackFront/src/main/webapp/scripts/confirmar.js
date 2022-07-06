

function cerrarSesion() {
	const cerrar = window.confirm("Desea Cerrar Sesion?");
	if (cerrar) {
		window.location.href = "sesion?cerrarSesion=true";// get
	}
}

function editar(url, id) {
	window.location.href = url + "?id=" + id;
}


function eliminar(url, id, texto) {
	const elimina = window.confirm("Desea eliminar " + texto + "?");
	if (elimina) {
		window.location.href = url + "?id=" + id;// get
	}
}