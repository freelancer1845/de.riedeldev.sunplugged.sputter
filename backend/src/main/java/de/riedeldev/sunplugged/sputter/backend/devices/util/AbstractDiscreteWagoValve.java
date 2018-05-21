package de.riedeldev.sunplugged.sputter.backend.devices.util;

import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;

public abstract class AbstractDiscreteWagoValve implements DiscreteValve {

  private final Coil coil;

  public AbstractDiscreteWagoValve(Coil coil) {
    this.coil = coil;
  }

  @Override
  public void open() {
    coil.setState(true);
  }

  @Override
  public void close() {
    coil.setState(false);
  }

  @Override
  public boolean isOpen() {
    return coil.getState();
  }

  @Override
  public String getId() {
    return coil.getId();
  }



}
