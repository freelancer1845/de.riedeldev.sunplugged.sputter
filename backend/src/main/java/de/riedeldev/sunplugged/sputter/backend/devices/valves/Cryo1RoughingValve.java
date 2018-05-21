package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@Component
public class Cryo1RoughingValve extends AbstractDiscreteWagoValve {
  private static final String ID = "ac013935";

  @Autowired
  public Cryo1RoughingValve(WagoIOService wago) {
    super(wago.getCoilById(ID));
  }

}
