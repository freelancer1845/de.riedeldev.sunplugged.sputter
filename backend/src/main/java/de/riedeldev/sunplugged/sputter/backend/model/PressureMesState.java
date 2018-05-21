package de.riedeldev.sunplugged.sputter.backend.model;

import de.riedeldev.sunplugged.sputter.backend.devices.Devices;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressureMesState {

  private Double fullRangeGauge;

  public PressureMesState(Devices devices) {
    this.fullRangeGauge = devices.getFullRangeGaugeSensor().getValue();
  }

}
