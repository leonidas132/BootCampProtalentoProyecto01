package com.protalento.configuraciones;

import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class ConfiguracionRest extends ResourceConfig {
	public ConfiguracionRest() {
		packages("com.protalento");
	}
}
