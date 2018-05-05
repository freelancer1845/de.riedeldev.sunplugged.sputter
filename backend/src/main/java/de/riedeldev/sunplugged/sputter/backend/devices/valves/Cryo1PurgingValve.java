package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

@Component
public class Cryo1PurgingValve extends AbstractDiscreteWagoValve {

	@Autowired
	public Cryo1PurgingValve(WagoIOService wago) {
		super(wago, DO.CRYO1_PURGE_VALVE);
	}

}
