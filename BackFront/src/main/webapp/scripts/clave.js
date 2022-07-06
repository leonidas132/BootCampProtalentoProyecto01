

const botonVerClave = document.getElementById("verClave");
const inputClave = document.getElementById("clave");

botonVerClave.addEventListener('click', function(evento) {
	const tipo = inputClave.getAttribute("type") === "text" ? "password" : "text";
	inputClave.setAttribute('type', tipo);
	botonVerClave.classList.toggle("bi-eye");
});

