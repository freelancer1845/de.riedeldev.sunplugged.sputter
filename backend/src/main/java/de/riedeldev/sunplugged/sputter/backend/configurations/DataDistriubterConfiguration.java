package de.riedeldev.sunplugged.sputter.backend.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "data-distributer")
@Getter
@Setter
public class DataDistriubterConfiguration {

	private long tickrate = 1;

	private boolean autoStart = false;

}
