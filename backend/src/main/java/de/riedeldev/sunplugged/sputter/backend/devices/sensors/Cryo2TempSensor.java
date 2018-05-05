package de.riedeldev.sunplugged.sputter.backend.devices.sensors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.util.Sensor;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.AI;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Cryo2TempSensor implements Sensor {

	private WagoIOService wago;

	@Autowired
	public Cryo2TempSensor(WagoIOService wago) {
		this.wago = wago;
	}

	@Override
	public double getValue() {
		int mesValue = wago.readAI(AI.CRYO2_TEMP);
		// Not Implemented yet
		log.warn("Get Value is not implemented for this Sensor. Returning actual register value.");

		return mesValue;
	}

}
