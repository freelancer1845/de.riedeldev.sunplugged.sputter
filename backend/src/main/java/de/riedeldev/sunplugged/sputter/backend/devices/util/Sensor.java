package de.riedeldev.sunplugged.sputter.backend.devices.util;

import de.riedeldev.sunplugged.sputter.backend.core.UniqueDevice;

public interface Sensor extends UniqueDevice {

  public double getValue();

}
