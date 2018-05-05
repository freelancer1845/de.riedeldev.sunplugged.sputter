package de.riedeldev.sunplugged.sputter.backend.services.impl;

import java.util.List;

import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.DiscreteInput;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.HoldingRegister;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.InputRegister;
import lombok.Data;

@Data
public class WagoConfigurationJSON {

	private List<Coil> coils;

	private List<DiscreteInput> discreteInputs;

	private List<HoldingRegister> holdingRegisters;

	private List<InputRegister> inputRegisters;

}
