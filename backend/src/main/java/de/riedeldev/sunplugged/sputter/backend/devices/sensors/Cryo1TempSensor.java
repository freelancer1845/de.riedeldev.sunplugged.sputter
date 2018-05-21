package de.riedeldev.sunplugged.sputter.backend.devices.sensors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.Sensor;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.InputRegister;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Cryo1TempSensor implements Sensor {

  private static final String INPUT_ID = "34898810";

  private InputRegister register;

  @Autowired
  public Cryo1TempSensor(WagoIOService wago) {
    this.register = wago.getInputRegisterById(INPUT_ID);
  }

  @Override
  public double getValue() {
    int mesValue = this.register.getValue();

    // Not Implemented yet
    log.warn("Get Value is not implemented for this Sensor. Returning actual register value. "
        + mesValue);

    return mesValue;
  }

  @Override
  public String getId() {
    return INPUT_ID;
  }

}
