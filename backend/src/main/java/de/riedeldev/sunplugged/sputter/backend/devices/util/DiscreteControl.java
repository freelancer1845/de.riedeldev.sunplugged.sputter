package de.riedeldev.sunplugged.sputter.backend.devices.util;

import de.riedeldev.sunplugged.sputter.backend.core.UniqueDevice;

public interface DiscreteControl extends UniqueDevice {

  public void on();

  public void off();

  public boolean isOn();
}
