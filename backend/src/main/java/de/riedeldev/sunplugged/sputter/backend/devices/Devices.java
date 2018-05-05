package de.riedeldev.sunplugged.sputter.backend.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.sensors.Cryo1TempSensor;
import de.riedeldev.sunplugged.sputter.backend.devices.sensors.Cryo2TempSensor;
import de.riedeldev.sunplugged.sputter.backend.devices.valves.Cryo1PurgingValve;
import de.riedeldev.sunplugged.sputter.backend.devices.valves.Cryo1RoughingValve;
import de.riedeldev.sunplugged.sputter.backend.devices.valves.Cryo2PurgingValve;
import de.riedeldev.sunplugged.sputter.backend.devices.valves.Cryo2RoughingValve;
import de.riedeldev.sunplugged.sputter.backend.evra.EvraPump;
import de.riedeldev.sunplugged.sputter.backend.pinnacle.PinnacleOne;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import lombok.Getter;

@Component
@Getter
public class Devices {

	@Autowired
	private Compressor compressor;

	@Autowired
	private Cryo1TempSensor cryo1TempSensor;

	@Autowired
	private Cryo2TempSensor cryo2TempSensor;

	@Autowired
	private Cryo1PurgingValve cryo1PurgingValve;

	@Autowired
	private Cryo2PurgingValve cryo2PurgingValve;

	@Autowired
	private Cryo1RoughingValve cryo1RoughingValve;

	@Autowired
	private Cryo2RoughingValve cryo2RoughingValve;

	@Autowired
	private PurgeHeater1 purgeHeater1;

	@Autowired
	private PurgeHeater2 purgeHeater2;

	@Autowired
	private WagoIOService wago;

	@Autowired
	private EvraPump evraPump;

	@Autowired
	private PinnacleOne pinnacleOne;

}
