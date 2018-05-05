package de.riedeldev.sunplugged.sputter.backend.configurations;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "wago")
@Getter
@Setter
public class WagoConfiguration {

	@NotNull
	String ip = "localhost";

	@NotNull
	int port = 502;

	@NotNull
	boolean start = false;

	@NotNull
	long tickrate = 10;

}
