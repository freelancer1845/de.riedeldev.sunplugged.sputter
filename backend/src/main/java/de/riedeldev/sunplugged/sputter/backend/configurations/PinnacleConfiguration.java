package de.riedeldev.sunplugged.sputter.backend.configurations;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "pinnacle")
@Getter
@Setter
public class PinnacleConfiguration {

	@NotNull
	public int baudrate;

	@NotNull
	long tickrate;

	public Pinnacle one = new Pinnacle();

	public Pinnacle two = new Pinnacle();

	@Getter
	@Setter
	public static class Pinnacle {
		@NotNull
		private String port = "None";

		private boolean start = false;

	}

}
