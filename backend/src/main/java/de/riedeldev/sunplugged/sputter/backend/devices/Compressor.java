package de.riedeldev.sunplugged.sputter.backend.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.core.UniqueDevice;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.DiscreteInput;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@Component
public class Compressor implements UniqueDevice {

  private static final String COMPRESSOR_STARTED_ID = "a9345007";

  private static final String CRYO_1_COIL = "eb7809ac";

  private static final String CRYO_2_COIL = "2c6b0987";


  private DiscreteInput compressorStartedInput;

  private Coil cryo1Coil;

  private Coil cryo2Coil;

  @Autowired
  public Compressor(WagoIOService wago) {
    this.compressorStartedInput = wago.getDiscreteInputById(COMPRESSOR_STARTED_ID);
    this.cryo1Coil = wago.getCoilById(CRYO_1_COIL);
    this.cryo2Coil = wago.getCoilById(CRYO_2_COIL);
  }

  public void startCryo1() {
    cryo1Coil.setState(true);
  }

  public void stopCryo1() {
    cryo1Coil.setState(false);

  }

  public void startCryo2() {
    cryo2Coil.setState(true);
  }

  public void stopCryo2() {
    cryo2Coil.setState(false);
  }

  public boolean isCryo1() {
    return cryo1Coil.getState();
  }

  public boolean isCryo2() {
    return cryo2Coil.getState();
  }

  public boolean isCompressorStarted() {
    return compressorStartedInput.getState();
  }

  @Override
  public String getId() {
    return COMPRESSOR_STARTED_ID;
  }

}
