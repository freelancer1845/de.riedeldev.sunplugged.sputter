package de.riedeldev.sunplugged.sputter.backend.configurations;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "machine")
public class WorkflowMachineConfiguration {

	@NotNull
	boolean start;

	@NotNull
	long tickrate;

}
