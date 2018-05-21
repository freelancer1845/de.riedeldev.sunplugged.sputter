package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@Component
public class Cryo2RoughingValve extends AbstractDiscreteWagoValve {

  private static final String ID = "3a231d4d";

  @Autowired
  public Cryo2RoughingValve(WagoIOService wago) {
    super(wago.getCoilById(ID));
  }

}
