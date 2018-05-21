package de.riedeldev.sunplugged.sputter.backend.services.impl;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.re.easymodbus.exceptions.ModbusException;
import de.re.easymodbus.modbusclient.ModbusClient;
import de.riedeldev.sunplugged.sputter.backend.configurations.WagoConfiguration;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.DiscreteInput;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.HoldingRegister;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.InputRegister;
import de.riedeldev.sunplugged.sputter.backend.services.WagoIOService;
import de.riedeldev.sunplugged.sputter.backend.utils.AbstractStateLoop;
import de.riedeldev.sunplugged.sputter.backend.utils.StandardStates;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WagoIOServiceImpl extends AbstractStateLoop<StandardStates> implements WagoIOService {

  private String ip;

  private int port;

  private boolean start = false;

  public WagoIOServiceImpl(WagoConfiguration config) {
    this.ip = config.getIp();
    this.port = config.getPort();

    this.start = config.isStart();
    setTickRate(config.getTickrate());
  }

  private boolean[] coils;

  private boolean[] discreteCoils;

  private int[] holdingRegisters;

  private int[] inputRegisters;

  private ModbusClient client;

  private ReentrantLock lock = new ReentrantLock(true);

  @Value(value = "classpath:wago.json")
  private Resource wagoJSON;

  private WagoConfigurationJSON wagoConfig;

  @PostConstruct
  public void init() throws IOException {

    readWagoConfiguration();
    initDataArrays();
    setThreadName("Wago Modbus");
    setState(StandardStates.INIT);
    Map<StandardStates, Callable<StandardStates>> states = getStates();

    states.put(StandardStates.INIT, () -> initCycle());
    states.put(StandardStates.RUNNING, () -> runningCycle());
    states.put(StandardStates.ERROR, () -> errorCycle());
    if (start) {
      start();
    } else {
      log.warn("WagoIOService was not started due to properties. Try set 'wago.start=true'");
    }

  }

  private void readWagoConfiguration() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    wagoConfig = mapper.readValue(wagoJSON.getInputStream(), WagoConfigurationJSON.class);

  }

  private StandardStates initCycle() {
    lock.lock();
    client = new ModbusClient(ip, Integer.valueOf(port));
    try {
      client.Connect();
    } catch (IOException ie) {
      log.error("Failed to connect to modbus.", ie);
      return StandardStates.ERROR;
    } finally {
      lock.unlock();
    }
    lock.lock();
    try {
      readData();
    } catch (ModbusException | IOException e) {
      log.error("Failed to connect to modbus.");
      log.debug("Failed to connect to modbus.", e);
      return StandardStates.ERROR;
    } finally {
      lock.unlock();
    }
    return StandardStates.RUNNING;

  }

  private StandardStates runningCycle() throws InterruptedException {

    try {
      lock.lock();
      writeData();
      readData();
    } catch (ModbusException | IOException e) {
      log.error("Error in Wago Modbus Connection.", e);
      return StandardStates.ERROR;
    } finally {
      lock.unlock();
    }

    Thread.sleep(5);

    return StandardStates.RUNNING;
  }

  private StandardStates errorCycle() throws InterruptedException {
    log.debug("Trying to reconnect to client in 5s");
    Thread.sleep(5000);
    return StandardStates.INIT;
  }

  private void readData()
      throws UnknownHostException, SocketException, ModbusException, IOException {
    coils = client.ReadCoils(0, coils.length);
    wagoConfig.getCoils().forEach(coil -> coil.setState(coils[coil.getAddress()]));

    discreteCoils = client.ReadDiscreteInputs(0, discreteCoils.length);
    wagoConfig.getDiscreteInputs()
              .forEach(input -> input.setState(discreteCoils[input.getAddress()]));


    holdingRegisters = client.ReadHoldingRegisters(0, holdingRegisters.length);
    wagoConfig.getHoldingRegisters()
              .forEach(reg -> reg.setValue(holdingRegisters[reg.getAddress()]));

    inputRegisters = client.ReadInputRegisters(0, inputRegisters.length);
    wagoConfig.getInputRegisters().forEach(reg -> reg.setValue(inputRegisters[reg.getAddress()]));

  }

  private void writeData()
      throws UnknownHostException, SocketException, ModbusException, IOException {
    client.WriteMultipleCoils(0, coils);
    client.WriteMultipleRegisters(0, holdingRegisters);

  }


  private void initDataArrays() {
    coils =
        new boolean[wagoConfig.getCoils().stream().mapToInt(Coil::getAddress).max().getAsInt() + 1];
    discreteCoils = new boolean[wagoConfig.getDiscreteInputs()
                                          .stream()
                                          .mapToInt(DiscreteInput::getAddress)
                                          .max()
                                          .getAsInt()
        + 1];
    holdingRegisters = new int[wagoConfig.getHoldingRegisters()
                                         .stream()
                                         .mapToInt(HoldingRegister::getAddress)
                                         .max()
                                         .getAsInt()
        + 1];
    inputRegisters = new int[wagoConfig.getInputRegisters()
                                       .stream()
                                       .mapToInt(InputRegister::getAddress)
                                       .max()
                                       .getAsInt()
        + 1];
  }


  @Override
  public boolean isOk() {
    return getState() == StandardStates.RUNNING;
  }

  @Override
  public List<Coil> getCoils() {
    return wagoConfig.getCoils();
  }

  @Override
  public List<DiscreteInput> getDiscreteInputs() {
    return wagoConfig.getDiscreteInputs();
  }


  @Override
  public List<HoldingRegister> getHoldingRegisters() {
    return wagoConfig.getHoldingRegisters();
  }

  @Override
  public List<InputRegister> getInputRegisters() {
    return wagoConfig.getInputRegisters();
  }

  @Override
  public void setCoil(Coil coil) {
    try {
      lock.lock();
      coils[coil.getAddress()] = coil.getState();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void setHoldindRegister(HoldingRegister reg) {
    try {
      lock.lock();
      holdingRegisters[reg.getAddress()] = reg.getValue();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public Coil getCoilById(String id) {
    return wagoConfig.getCoils().stream().filter(coil -> coil.getId().equals(id)).findFirst().get();
  }

  @Override
  public DiscreteInput getDiscreteInputById(String id) {
    return wagoConfig.getDiscreteInputs()
                     .stream()
                     .filter(input -> input.getId().equals(id))
                     .findFirst()
                     .get();
  }

  @Override
  public HoldingRegister getHoldingRegisterById(String id) {
    return wagoConfig.getHoldingRegisters()
                     .stream()
                     .filter(reg -> reg.getId().equals(id))
                     .findFirst()
                     .get();
  }

  @Override
  public InputRegister getInputRegisterById(String id) {
    return wagoConfig.getInputRegisters()
                     .stream()
                     .filter(reg -> reg.getId().equals(id))
                     .findFirst()
                     .get();
  }

}
