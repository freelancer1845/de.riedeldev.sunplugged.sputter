package de.riedeldev.sunplugged.sputter.backend.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService.DO;

@Component
public class Compressor {

	private final WagoIOService wago;

	@Autowired
	public Compressor(WagoIOService wago) {
		this.wago = wago;
	}

	public void startCryo1() {
		wago.setDO(DO.COMPRESSOR1_CRYO1_START, true);
	}

	public void stopCryo1() {
		wago.setDO(DO.COMPRESSOR1_CRYO1_START, false);
	}

	public void startCryo2() {
		wago.setDO(DO.COMPRESSOR1_CRYO2_START, true);
	}

	public void stopCryo2() {
		wago.setDO(DO.COMPRESSOR1_CRYO2_START, false);
	}

	public boolean isCryo1() {
		return wago.readDO(DO.COMPRESSOR1_CRYO1_START);
	}

	public boolean isCryo2() {
		return wago.readDO(DO.COMPRESSOR1_CRYO2_START);
	}

}
