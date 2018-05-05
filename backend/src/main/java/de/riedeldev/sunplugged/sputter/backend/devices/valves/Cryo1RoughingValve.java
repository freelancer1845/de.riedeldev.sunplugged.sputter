package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

@Component
public class Cryo1RoughingValve extends AbstractDiscreteWagoValve {

	@Autowired
	public Cryo1RoughingValve(WagoIOService wago) {
		super(wago, DO.CRYO1_ROUGH_VALVE);
	}

}
