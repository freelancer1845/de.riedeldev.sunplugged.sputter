package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

@Component
public class FullRangeGaugeIsolationValve extends AbstractDiscreteWagoValve {

	@Autowired
	public FullRangeGaugeIsolationValve(WagoIOService wago) {
		super(wago, DO.FULL_RANGE_GAUGE_ISOLATION_VALVE);
	}

}
