package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

@Component
public class Cryo2RoughingValve extends AbstractDiscreteWagoValve {

	private WagoIOService wago;

	@Autowired
	public Cryo2RoughingValve(WagoIOService wago) {
		super(wago, DO.CRYO2_ROUGH_VALVE);
	}

}
