package de.riedeldev.sunplugged.sputter.backend.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoControl;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

@Component
public class PurgeHeater2 extends AbstractDiscreteWagoControl {

	@Autowired
	public PurgeHeater2(WagoIOService wago) {
		super(wago, DO.CRYO2_PURGE_HEATER_ON_OFF);
	}

}
