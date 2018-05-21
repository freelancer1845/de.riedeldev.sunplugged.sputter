package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.DiscreteValve;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.DiscreteInput;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HighVacuumValve1 implements DiscreteValve {

  private static final String COIL_ID = "fb5b1f61";

  private static final String OPEN_ID = "553d91e5";
  private static final String CLOSED_ID = "bc6c97c6";


  private Coil coil;

  private DiscreteInput closedInput;

  private DiscreteInput openInput;

  @Autowired
  public HighVacuumValve1(WagoIOService wago) {
    this.coil = wago.getCoilById(COIL_ID);
    this.closedInput = wago.getDiscreteInputById(CLOSED_ID);
    this.openInput = wago.getDiscreteInputById(OPEN_ID);
  }

  public void open() {
    coil.setState(true);
  }

  public void close() {
    coil.setState(false);
  }

  public boolean isOpen() {
    boolean is = openInput.getState();
    boolean isNot = closedInput.getState();
    if (is == isNot) {
      log.error("Inconsitent state for HiVacVale1. Returning isOpen=true!!!");
      return true;
    }
    return is ? true : false;
  }

  @Override
  public String getId() {
    return COIL_ID;
  }

}
