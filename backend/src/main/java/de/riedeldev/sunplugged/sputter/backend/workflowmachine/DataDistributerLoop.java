package de.riedeldev.sunplugged.sputter.backend.workflowmachine;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.riedeldev.sunplugged.sputter.backend.configurations.DataDistriubterConfiguration;
import de.riedeldev.sunplugged.sputter.backend.devices.Devices;
import de.riedeldev.sunplugged.sputter.backend.evra.EvraState;
import de.riedeldev.sunplugged.sputter.backend.model.CryoState;
import de.riedeldev.sunplugged.sputter.backend.model.GlobalState;
import de.riedeldev.sunplugged.sputter.backend.model.StateData;
import de.riedeldev.sunplugged.sputter.backend.model.WagoIOData;
import de.riedeldev.sunplugged.sputter.backend.utils.AbstractStateLoop;
import de.riedeldev.sunplugged.sputter.backend.utils.StandardStates;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataDistributerLoop extends AbstractStateLoop<StandardStates> {

  private static final String TOPIC_WAGO_IO = "/topic/wagoIO";

  private static final String TOPIC_CRYO = "/topic/cryo";

  private static final String TOPIC_GLOBAL = "/topic/global";

  private static final String TOPIC_EVARA = "/topic/evara";

  private final DataDistriubterConfiguration config;

  private final ApplicationEventPublisher publisher;

  private final Devices devices;

  @Autowired
  private SimpMessagingTemplate template;

  @Autowired
  public DataDistributerLoop(DataDistriubterConfiguration config,
      ApplicationEventPublisher publisher, Devices devices) {
    this.config = config;
    this.publisher = publisher;
    this.devices = devices;
    setState(StandardStates.INIT);
    setTickRate(config.getTickrate());

    getStates().put(StandardStates.INIT, () -> initState());
    getStates().put(StandardStates.RUNNING, () -> runningState());

    if (config.isAutoStart()) {
      start();
    }

  }

  private StandardStates runningState() {
    StateData stateData = new StateData();

    fillGlobalStateData(stateData.getGlobalState());
    fillWagoIOData(stateData.getWagoIOData());
    fillCryoState(stateData.getCryoState());
    fillEvraState(stateData.getEvraState());

    StateDataEvent event = new StateDataEvent(stateData);
    publisher.publishEvent(event);

    pushEvents(stateData);

    return StandardStates.RUNNING;
  }

  private void fillGlobalStateData(GlobalState globalState) {
    globalState.setWagoConnectedState(devices.getWago().isOk());
    globalState.setEvraConnectedState(devices.getEvraPump().getState() == StandardStates.RUNNING);
    globalState.setPinnacleOneConnected(
        devices.getPinnacleOne().getState() == StandardStates.RUNNING);
    globalState.setPinnacleTwoConnected(false);
  }

  private void pushEvents(StateData stateData) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      template.convertAndSend(TOPIC_GLOBAL,
          mapper.writeValueAsString(stateData.getGlobalState()));

      if (stateData.getEvraState() != null) {
        template.convertAndSend(TOPIC_EVARA,
            mapper.writeValueAsString(stateData.getEvraState()));
      }
      if (stateData.getCryoState() != null) {
        template.convertAndSend(TOPIC_CRYO, mapper.writeValueAsString(stateData.getCryoState()));
      }
      if (stateData.getWagoIOData() != null) {
        template.convertAndSend(TOPIC_WAGO_IO,
            mapper.writeValueAsString(stateData.getWagoIOData()));
      }
    } catch (MessagingException | JsonProcessingException e) {
      log.error("Error sending push data.", e);
    }

  }

  private void fillEvraState(EvraState evraState) {
    EvraState current = devices.getEvraPump().getEvraState();

    BeanUtils.copyProperties(current, evraState);
  }

  private void fillWagoIOData(WagoIOData wagoIOData) {
    wagoIOData.setCoils(devices.getWago().getCoils());
    wagoIOData.setDiscreteInputs(devices.getWago().getDiscreteInputs());
    wagoIOData.setHoldingRegisters(devices.getWago().getHoldingRegisters());
    wagoIOData.setInputRegisters(devices.getWago().getInputRegisters());
  }

  private void fillCryoState(CryoState cryoState) {
    cryoState.setCryo1CompressorOn(devices.getCompressor().isCryo1());
    cryoState.setCryo2CompressorOn(devices.getCompressor().isCryo2());

    cryoState.setCryo1Temp(devices.getCryo1TempSensor().getValue());
    cryoState.setCryo2Temp(devices.getCryo2TempSensor().getValue());

    cryoState.setPurgeValveOne(devices.getCryo1PurgingValve().isOpen());
    cryoState.setPurgeValveTwo(devices.getCryo2PurgingValve().isOpen());

    cryoState.setRoughValveOne(devices.getCryo1RoughingValve().isOpen());
    cryoState.setRoughValveTwo(devices.getCryo2RoughingValve().isOpen());
    cryoState.setPurgeHeaterOne(devices.getPurgeHeater1().isOn());
    cryoState.setPurgeHeaterTwo(devices.getPurgeHeater2().isOn());
  }

  private StandardStates initState() throws InterruptedException {
    if (config.isAutoStart()) {
      Thread.sleep(5000);
      return StandardStates.RUNNING;
    }
    synchronized (this) {
      wait();
    }

    return StandardStates.RUNNING;
  }

  public void startLoop() {
    if (getState() != StandardStates.INIT) {
      log.error("DataDistributerLoop is not in the coorect state to start it. " + getState());
      throw new IllegalStateException("Not in INIT state!");
    }
    synchronized (this) {
      notify();
    }
  }

}
