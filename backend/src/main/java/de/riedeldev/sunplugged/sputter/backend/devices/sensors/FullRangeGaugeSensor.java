package de.riedeldev.sunplugged.sputter.backend.devices.sensors;

import org.springframework.beans.factory.annotation.Autowired;

import de.riedeldev.sunplugged.sputter.backend.devices.util.Sensor;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.AI;
import de.riedeldev.sunplugged.sputter.backend.utils.Conversions;

public class FullRangeGaugeSensor implements Sensor {

	private static final double Full_Range_Gauge_Voltage_Pressure_Relation = 1.286;

	private static final double Full_Range_Gauge_Nomralization = Math.pow(10.0,
			-10.0 / Full_Range_Gauge_Voltage_Pressure_Relation);

	private WagoIOService wago;

	@Autowired
	public FullRangeGaugeSensor(WagoIOService wago) {
		this.wago = wago;
	}

	@Override
	public double getValue() {
		int mesValue = wago.readAI(AI.FULL_RANGE_GAUGE);
		double voltageSignal = Conversions.convertWagoIOAnalog(mesValue);
		double pressure = Full_Range_Gauge_Nomralization
				* Math.pow(10, voltageSignal / Full_Range_Gauge_Voltage_Pressure_Relation);

		return pressure;
	}

}
