package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@Component
public class Cryo2PurgingValve extends AbstractDiscreteWagoValve {

  private static final String ID = "45dcff1e";

  @Autowired
  public Cryo2PurgingValve(WagoIOService wago) {
    super(wago.getCoilById(ID));
  }

}
