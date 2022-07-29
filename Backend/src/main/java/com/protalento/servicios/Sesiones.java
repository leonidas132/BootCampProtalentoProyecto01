package com.protalento.servicios;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.protalento.dtos.SesionDTO;
import com.protalento.dtos.UsuarioDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Sesiones {
	public static ConcurrentMap<String, SesionDTO> sesiones = new ConcurrentHashMap<>();
	private static Logger logger = LogManager.getLogger();
	public static final byte TIEMPO_SESION = 90;

	private Sesiones() {

	}

	public static boolean crearSesion(UsuarioDTO dto) {
		String correo = dto.getCorreo();

		if (!sesiones.containsKey(correo)) {
			sesiones.put(dto.getCorreo(), new SesionDTO(dto));
			logger.debug("Se creo la sesion " + dto);
			return true;
		}
		return actualizarSesion(correo);
	}

	public static boolean verificarSesion(String correo, String uuid) {
		logger.debug(sesiones);
		SesionDTO sesionDTO = sesiones.get(correo);

		logger.debug(correo + " | " + uuid);
		if (sesionDTO != null && sesionDTO.getUsuarioDTO().getUuid().toString().equalsIgnoreCase(uuid)) {
			return actualizarSesion(correo);
		}
		invalidarSesion(correo);
		return false;
	}

	public static boolean invalidarSesion(String correo) {
		return sesiones.remove(correo) == null ? false : true;
	}

	public static boolean actualizarSesion(String correo) {
		LocalDateTime fechaActual = LocalDateTime.now();
		SesionDTO sesionDTO = sesiones.get(correo);
		long minutos = ChronoUnit.SECONDS.between(sesionDTO.getFechaSesion(), fechaActual);
		logger.debug("Tiempo de sesion: " + minutos);
		if (minutos > TIEMPO_SESION) {
			invalidarSesion(correo);
			logger.debug("Expiro la sesion " + sesionDTO);
			return false;
		} else {
			sesionDTO.setFechaSesion(fechaActual);
			logger.debug("Actualizo la sesion " + sesionDTO);
			return true;
		}
	}

}
