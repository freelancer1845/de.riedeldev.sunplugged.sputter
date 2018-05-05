package de.riedeldev.sunplugged.sputter.backend.model;

import java.util.List;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.DiscreteInput;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.HoldingRegister;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.InputRegister;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WagoIOData {

  private List<Coil> coils;

  private List<DiscreteInput> discreteInputs;

  private List<HoldingRegister> holdingRegisters;

  private List<InputRegister> inputRegisters;



}
