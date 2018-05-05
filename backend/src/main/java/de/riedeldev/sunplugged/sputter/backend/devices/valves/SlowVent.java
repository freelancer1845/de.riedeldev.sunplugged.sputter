package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

@Component
public class SlowVent extends AbstractDiscreteWagoValve {

	private WagoIOService wago;

	@Autowired
	public SlowVent(WagoIOService wago) {
		super(wago, DO.SLOW_VENT_VALVE);
	}

}
