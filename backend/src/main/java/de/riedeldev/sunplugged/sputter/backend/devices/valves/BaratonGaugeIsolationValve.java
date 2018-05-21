package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@Component
public class BaratonGaugeIsolationValve extends AbstractDiscreteWagoValve {

  private static final String ID = "f82c4692";

  @Autowired
  public BaratonGaugeIsolationValve(WagoIOService wago) {
    super(wago.getCoilById(ID));
  }

}
