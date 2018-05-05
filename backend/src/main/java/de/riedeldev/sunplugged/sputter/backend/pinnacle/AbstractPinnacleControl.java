package de.riedeldev.sunplugged.sputter.backend.pinnacle;

import java.util.LinkedList;
import java.util.Queue;

import com.fazecast.jSerialComm.SerialPort;

import de.riedeldev.sunplugged.sputter.backend.configurations.PinnacleConfiguration;
import de.riedeldev.sunplugged.sputter.backend.core.hardware.CustomSerialPort;
import de.riedeldev.sunplugged.sputter.backend.core.hardware.SerialPortException;
import de.riedeldev.sunplugged.sputter.backend.utils.AbstractStateLoop;
import de.riedeldev.sunplugged.sputter.backend.utils.StandardStates;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractPinnacleControl extends AbstractStateLoop<StandardStates> {

	private CustomSerialPort port;

	private int baudrate = 0;

	protected Queue<AEBusCommand> commands = new LinkedList<>();

	public AbstractPinnacleControl(String name, String comPort, PinnacleConfiguration config) {
		baudrate = config.getBaudrate();
		setTickRate(config.getTickrate());
		setState(StandardStates.INIT);
		getStates().put(StandardStates.INIT, () -> {
			try {
				open(comPort);
				return StandardStates.RUNNING;
			} catch (Exception e) {
				log.error("Failed to open port for pinnacle communication.", e);
				return StandardStates.ERROR;
			}
		});

		getStates().put(StandardStates.RUNNING, () -> {
			try {
				tick();
			} catch (Exception e) {
				return StandardStates.ERROR;
			}
			return StandardStates.RUNNING;

		});
		getStates().put(StandardStates.ERROR, () -> {
			log.debug("Unhandled Error in Pinnacle Loop.");
			return error();
		});
	}

	public void queueCommand(AEBusCommand command) {
		if (commands.offer(command) == false) {
			log.error("Failed to add command to queue.");
		}
	}

	protected abstract StandardStates error();

	private void tick() {
		while (commands.peek() != null) {
			try {
				commands.poll().execute(port);
			} catch (AEException e) {
				log.error("AEBusCommand failed to execute.", e);
			}
		}
	}

	private void open(String comPort) throws SerialPortException {
		port = new CustomSerialPort(comPort);

		configurePortForPinnacle(port);

	}

	private void configurePortForPinnacle(CustomSerialPort port2) {
		SerialPort port = port2.getPort();

		if (baudrate != 0) {
			port.setBaudRate(baudrate);
		} else {
			log.warn("Setting default Baudrate for pinnacle to 9600. Try set propertie 'pinnacle.baudrate'");
			port.setBaudRate(9600);
		}
		port.setParity(SerialPort.ODD_PARITY);
		port.setNumStopBits(1);
		port.setNumDataBits(8);
		port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING | SerialPort.TIMEOUT_WRITE_SEMI_BLOCKING, 20, 20);
	}

}
