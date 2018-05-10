package de.riedeldev.sunplugged.sputter.backend.evra;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fazecast.jSerialComm.SerialPort;
import de.riedeldev.sunplugged.sputter.backend.configurations.EvraPumpConfiguration;
import de.riedeldev.sunplugged.sputter.backend.utils.AbstractStateLoop;
import de.riedeldev.sunplugged.sputter.backend.utils.StandardStates;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EvraPump extends AbstractStateLoop<StandardStates> {

  private static final long TIME_BETWEEN_SEND = 1000;

  private static final long MAX_RESEND = 3;

  private long lastResponse = 0;

  private long lastSend = 0;

  private int sendCounter = 0;

  private SerialPort port;

  private BlockingQueue<EvraCommand> commands = new ArrayBlockingQueue<>(50);

  private EvraPumpConfiguration config;

  private EvraState evraState = new EvraState();

  @Autowired
  public EvraPump(EvraPumpConfiguration config) {

    setState(StandardStates.INIT);

    getStates().put(StandardStates.INIT, () -> init());
    getStates().put(StandardStates.RUNNING, () -> mainLoop());
    getStates().put(StandardStates.ERROR, () -> error());

    this.config = config;
    if (config.isStart()) {
      start();
    }
  }

  @Scheduled(fixedDelay = 5000)
  public void issueStatusUpdate() {
    queueCommand(new EvraCommand("M21", answer -> handleStatusAnswer(answer)));
  }

  private void handleStatusAnswer(EvraCommand cmd) {
    String answer = cmd.getPackageAnswers().get(0).getMessage();

    byte[] bytes = answer.getBytes(Charset.forName("ASCII"));

    evraState.setNormalMode(answer.substring(0, 1).equals("N"));
    evraState.setPowerSavingMode(answer.substring(0, 1).equals("S"));
    evraState.setMpRunning(answer.substring(1, 2).equals("R"));
    evraState.setBpRunning(answer.substring(2, 3).equals("R"));
    byte[] warningBytes = Arrays.copyOfRange(bytes, 3, 11);

    boolean[] warnings = convertWarningOrAlarmBytes(warningBytes);

    evraState.getWarnings()
             .addAll((IntStream.range(0, warnings.length)
                               .filter(idx -> warnings[idx])
                               .mapToObj(idx -> new EvaraWarning(idx))
                               .collect(Collectors.toList())));
    byte[] alarmBytes = Arrays.copyOfRange(bytes, 11, 19);

    boolean[] alarms = convertWarningOrAlarmBytes(alarmBytes);
    evraState.getAlarms()
             .addAll((IntStream.range(0, alarms.length)
                               .filter(idx -> alarms[idx])
                               .mapToObj(idx -> new EvaraAlarm(idx))
                               .collect(Collectors.toList())));

  }

  private StandardStates error() {
    log.error("Error in Evra Pump Loop");

    if (config.isAutoRestart()) {
      log.debug("Autorestarting loop");
      return StandardStates.INIT;
    } else {
      log.debug("Loop finished");
      finish();
      return StandardStates.ERROR;
    }

  }

  @Override
  public void setState(StandardStates state) {
    this.evraState.setState(state);
    super.setState(state);
  }

  private StandardStates init() {

    port = SerialPort.getCommPort(config.getPort());

    port.setBaudRate(config.getBaudrate());
    port.setNumDataBits(config.getDatalength());
    port.setParity(config.getParity());

    port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 500);

    if (port.openPort()) {
      log.debug("Evra Port openend.");
      return StandardStates.RUNNING;
    } else {
      log.error("Failed to open Evra port.");
      return StandardStates.ERROR;
    }
  }

  /**
   * 
   * 
   * @return boolean If the command is queued.
   */
  public boolean startPumpMP() {
    EvraCommand cmd = new EvraCommand("S20M", cmdA -> {
      EvraPackage data = cmdA.getPackageAnswers().get(0);
      if (data.getMessage() == "OK") {
        log.debug("Started M Pump.");
      } else if (data.getMessage() == "NG") {
        log.error("Starting M Pump unsuccesfully");
      }

    });
    return queueCommand(cmd);
  }

  /**
   * 
   * 
   * @return
   * @return boolean If the command is queued.
   */
  public boolean stopPumpMP() {
    EvraCommand cmd = new EvraCommand("S21M", cmdA -> {
      EvraPackage data = cmdA.getPackageAnswers().get(0);
      if (data.getMessage() == "OK") {
        log.debug("Stopped M Pump.");
      } else if (data.getMessage() == "NG") {
        log.error("Stopping M Pump unsuccesfully");
      }

    });
    return queueCommand(cmd);
  }

  public boolean queueCommand(EvraCommand e) {
    if (getState() != StandardStates.RUNNING) {
      log.error("EvraPump not in RUNNING state. Command will be ignored: " + e.getMessage());
      return false;
    }

    return commands.offer(e);
  }

  private StandardStates mainLoop() throws InterruptedException, IOException {

    sendCommand.andThen(recieveAnswer).andThen(handleCommand).accept(commands.take());

    return StandardStates.RUNNING;
  }

  private Consumer<EvraCommand> handleCommand = cmd -> {

    try {
      if (cmd.isCancelled()) {
        return;
      }
      if (cmd.getPackageAnswers().size() == 0) {
        log.warn("No Answers for command: " + cmd.getMessage());
        return;
      }
      if (cmd.getCallBack() != null) {
        cmd.getCallBack().accept(cmd);
      }
    } finally {
      cmd.setDone(true);
    }
  };

  private Consumer<EvraCommand> sendCommand = command -> {

    try {
      waitSendTime();
    } catch (InterruptedException e) {
      command.setException(e);
      command.cancel(true);
      log.error("EvraPump Interrupted while wait for command send.");
      return;
    }

    byte[] buffer = EvraPackage.toBytes(command.getMessage());

    port.writeBytes(buffer, buffer.length);

    lastSend = System.currentTimeMillis();
    sendCounter++;
  };

  private Consumer<EvraCommand> recieveAnswer = command -> {
    while (true) {
      if (command.isCancelled() == true) {
        return;
      }
      EvraPackage recievedPackage;
      try {
        recievedPackage = readPackage();
        sendCounter = 0;
      } catch (EvraCRCException | IOException e) {
        log.warn("Error in evra connection.", e);
        if (sendCounter < MAX_RESEND) {
          sendCommand.accept(command);
        } else {
          log.error("Failed to resend command. Check Connection.");
          command.setException(e);
        }
        continue;
      }
      command.getPackageAnswers().add(recievedPackage);
      if (recievedPackage.isAnalogResponse() == false) {
        break;
      }
    }
  };

  private EvraPackage readPackage() throws IOException, EvraCRCException {
    byte[] recieveBuffer = new byte[1024];

    int position = 0;
    while (true) {
      int bytesRead = port.readBytes(recieveBuffer, 1);
      if (bytesRead == -1) {
        throw new IOException("Error reading bytes.");
      }
      position++;

      if (recieveBuffer[position - 1] == EvraPackage.CR) {
        break;
      } else if (position == 1024) {
        throw new BufferOverflowException();
      }
    }
    lastResponse = System.currentTimeMillis();
    return EvraPackage.parseFromData(Arrays.copyOf(recieveBuffer, position));

  }

  private void waitSendTime() throws InterruptedException {
    long currentTime = System.currentTimeMillis();
    long diff = currentTime - lastSend;
    if (diff < TIME_BETWEEN_SEND) {
      if (diff > 0) {
        Thread.sleep(diff);
      }
    }
  }

  private boolean[] convertWarningOrAlarmBytes(byte[] data) {
    boolean[] result = new boolean[data.length * 4];
    for (int j = 0; j < data.length; j++) {
      byte d = data[j];
      for (int i = 0; i < 4; i++) {
        boolean set = ((d >> i) & 1) == 1;
        result[result.length - 1 - (j * 4) + i] = set;
      }
    }
    return result;
  }

  public EvraState getEvraState() {
    return evraState;
  }
}
