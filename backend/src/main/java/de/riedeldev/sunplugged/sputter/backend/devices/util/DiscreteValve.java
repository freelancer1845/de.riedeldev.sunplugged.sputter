package de.riedeldev.sunplugged.sputter.backend.devices.util;

import de.riedeldev.sunplugged.sputter.backend.core.UniqueDevice;

public interface DiscreteValve extends UniqueDevice {

  public void open();

  public void close();

  public boolean isOpen();

}
