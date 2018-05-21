package de.riedeldev.sunplugged.sputter.backend.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoControl;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@Component
public class PurgeHeater2 extends AbstractDiscreteWagoControl {

  private static final String ON_ON_OFF_ID = "ea1161c8";

  @Autowired
  public PurgeHeater2(WagoIOService wago) {
    super(wago.getCoilById(ON_ON_OFF_ID));
  }

}
