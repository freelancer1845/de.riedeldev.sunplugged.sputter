package de.riedeldev.sunplugged.sputter.backend.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.riedeldev.sunplugged.sputter.backend.devices.util.AbstractDiscreteWagoControl;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@Component
public class PurgeHeater1 extends AbstractDiscreteWagoControl {

  private static final String ON_OFF_ID = "6e6bb066";

  @Autowired
  public PurgeHeater1(WagoIOService wago) {
    super(wago.getCoilById(ON_OFF_ID));
  }

}
