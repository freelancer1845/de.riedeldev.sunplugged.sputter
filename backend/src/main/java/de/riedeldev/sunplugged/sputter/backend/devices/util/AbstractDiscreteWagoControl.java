package de.riedeldev.sunplugged.sputter.backend.devices.util;

import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

public abstract class AbstractDiscreteWagoControl implements DiscreteControl {

	private WagoIOService wago;

	private final DO digitalOutput;

	public AbstractDiscreteWagoControl(WagoIOService wago, DO digitalOutput) {
		this.digitalOutput = digitalOutput;
		this.wago = wago;
	}

	@Override
	public void on() {
		wago.setDO(digitalOutput, true);

	}

	@Override
	public void off() {
		wago.setDO(digitalOutput, false);
	}

	@Override
	public boolean isOn() {
		return wago.readDO(digitalOutput);
	}
}
