package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.devices.util.DiscreteValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DI;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HighVacuumValve1 implements DiscreteValve {

	private WagoIOService wago;

	@Autowired
	public HighVacuumValve1(WagoIOService wago) {
		this.wago = wago;
	}

	public void open() {
		wago.setDO(DO.HI_VAC_VALVE1_OPEN_CLOSE, true);
	}

	public void close() {
		wago.setDO(DO.HI_VAC_VALVE1_OPEN_CLOSE, false);
	}

	public boolean isOpen() {
		boolean is = wago.readDI(DI.HI_VAC_VALVE1_OPEN);
		boolean isNot = wago.readDI(DI.HI_VAC_VALVE1_CLOSED);
		if (is == isNot) {
			log.error("Inconsitent state for HiVacVale1. Returning isOpen=true!!!");
			return true;
		}
		return is ? true : false;
	}

}
