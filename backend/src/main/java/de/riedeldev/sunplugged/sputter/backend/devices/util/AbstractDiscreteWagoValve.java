package de.riedeldev.sunplugged.sputter.backend.devices.util;

import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

public abstract class AbstractDiscreteWagoValve implements DiscreteValve {

	private WagoIOService wago;

	private final DO digitalOutput;

	public AbstractDiscreteWagoValve(WagoIOService wago, DO digitalOutput) {
		this.wago = wago;
		this.digitalOutput = digitalOutput;
	}

	@Override
	public void open() {
		wago.setDO(digitalOutput, true);
	}

	@Override
	public void close() {
		wago.setDO(digitalOutput, false);
	}

	@Override
	public boolean isOpen() {
		return wago.readDO(digitalOutput);
	}

}
