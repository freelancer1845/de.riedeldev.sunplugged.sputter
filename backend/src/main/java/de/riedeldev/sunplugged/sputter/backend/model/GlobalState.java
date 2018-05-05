package de.riedeldev.sunplugged.sputter.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalState {

	private Boolean WagoConnectedState = false;

	private Boolean evraConnectedState = false;

	private Boolean pinnacleOneConnected = false;

	private Boolean pinnacleTwoConnected = false;

}
