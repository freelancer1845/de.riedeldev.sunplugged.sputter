package de.riedeldev.sunplugged.sputter.backend.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoControl;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

@Component
public class PurgeHeater1 extends AbstractDiscreteWagoControl {

	@Autowired
	public PurgeHeater1(WagoIOService wago) {
		super(wago, DO.CRYO1_PURGE_HEATER_ON_OFF);
	}

}
