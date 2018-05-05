package de.riedeldev.sunplugged.sputter.backend.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "mainloop")
@Getter
@Setter
public class MainLoopConfiguration {

	private boolean autoStart = false;

	private long tickrate = 100;

}
