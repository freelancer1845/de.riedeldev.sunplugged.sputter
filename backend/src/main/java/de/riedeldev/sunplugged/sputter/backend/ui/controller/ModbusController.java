package de.riedeldev.sunplugged.sputter.backend.ui.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.DiscreteInput;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.HoldingRegister;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.InputRegister;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;

@CrossOrigin(origins = CrossOriginConfig.ALLOWED_CROSS_ORIGIN, maxAge = CrossOriginConfig.MAX_AGE)
@RestController
@RequestMapping({"/api/modbus"})
public class ModbusController {

  private WagoIOService service;

  @Autowired
  public ModbusController(WagoIOService service) {
    this.service = service;
  }

  @GetMapping(path = "/coils")
  public List<Coil> getCoils() {
    return service.getCoils();
  }

  @GetMapping(path = "/discreteinputs")
  public List<DiscreteInput> getDiscreteInputs() {
    return service.getDiscreteInputs();
  }

  @GetMapping(path = "/holdingRegisters")
  public List<HoldingRegister> getHoldingRegisters() {
    return service.getHoldingRegisters();
  }

  @GetMapping(path = "/inputRegisters")
  public List<InputRegister> getInputRegisters() {
    return service.getInputRegisters();
  }


  @PostMapping(path = "/submitcoils")
  public void submitCoil(@RequestBody List<Coil> coils) {
    coils.forEach(coil -> service.setCoil(coil));
  }

  @PostMapping(path = "/submitholdingregisters")
  public void submitHoldingRegisters(@RequestBody List<HoldingRegister> holdingRegisters) {
    holdingRegisters.forEach(reg -> service.setHoldindRegister(reg));
  }

}
