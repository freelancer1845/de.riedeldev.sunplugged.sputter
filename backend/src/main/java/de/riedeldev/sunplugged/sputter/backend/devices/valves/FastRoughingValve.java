package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@Component
public class FastRoughingValve extends AbstractDiscreteWagoValve {

  private static final String ID = "7521becd";

  @Autowired
  public FastRoughingValve(WagoIOService wago) {
    super(wago.getCoilById(ID));
  }

}
