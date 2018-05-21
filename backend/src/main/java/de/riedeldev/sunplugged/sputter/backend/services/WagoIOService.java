package de.riedeldev.sunplugged.sputter.backend.services;

import java.util.List;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.DiscreteInput;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.HoldingRegister;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.InputRegister;

/**
 * 
 * Service for accessing all hardware related controls.
 * 
 * @author Jascha Riedel
 *
 */
public interface WagoIOService {

  public boolean isOk();

  public List<Coil> getCoils();

  public List<DiscreteInput> getDiscreteInputs();

  public List<HoldingRegister> getHoldingRegisters();

  public List<InputRegister> getInputRegisters();

  public void setCoil(Coil coil);

  public void setHoldindRegister(HoldingRegister reg);

  public Coil getCoilById(String id);

  public DiscreteInput getDiscreteInputById(String id);

  public HoldingRegister getHoldingRegisterById(String id);

  public InputRegister getInputRegisterById(String id);


}
