package de.riedeldev.sunplugged.sputter.backend.devices.util;

import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;

public abstract class AbstractDiscreteWagoControl implements DiscreteControl {


  private final Coil coil;

  public AbstractDiscreteWagoControl(Coil coil) {
    this.coil = coil;
  }

  @Override
  public void on() {
    coil.setState(true);

  }

  @Override
  public void off() {
    coil.setState(false);
  }

  @Override
  public boolean isOn() {
    return coil.getState();
  }

  @Override
  public String getId() {
    return coil.getId();
  }
}
