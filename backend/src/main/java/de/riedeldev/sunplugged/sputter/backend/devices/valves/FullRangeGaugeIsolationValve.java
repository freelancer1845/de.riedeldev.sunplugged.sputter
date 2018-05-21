package de.riedeldev.sunplugged.sputter.backend.devices.valves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoValve;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@Component
public class FullRangeGaugeIsolationValve extends AbstractDiscreteWagoValve {

  private static final String ID = "56bf97f4";

  @Autowired
  public FullRangeGaugeIsolationValve(WagoIOService wago) {
    super(wago.getCoilById(ID));
  }

}
