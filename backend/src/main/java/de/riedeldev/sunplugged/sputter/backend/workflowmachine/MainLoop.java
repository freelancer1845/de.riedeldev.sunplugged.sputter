package de.riedeldev.sunplugged.sputter.backend.workflowmachine;

import org.springframework.beans.factory.annotation.Autowired;

import de.riedeldev.sunplugged.sputter.backend.configurations.MainLoopConfiguration;
import de.riedeldev.sunplugged.sputter.backend.devices.Devices;
import de.riedeldev.sunplugged.sputter.backend.utils.AbstractStateLoop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainLoop extends AbstractStateLoop<MainLoopStates> {

	private final Devices devices;

	private boolean autoStart = false;

	@Autowired
	public MainLoop(MainLoopConfiguration config, Devices devices) {
		setTickRate(config.getTickrate());

		setState(MainLoopStates.IDLE);

		getStates().put(MainLoopStates.IDLE, () -> idleState());
		getStates().put(MainLoopStates.STARTING, () -> startingState());
		getStates().put(MainLoopStates.RUNNING, () -> runngingState());

		setThreadName("MainLoop");

		this.devices = devices;

		this.autoStart = config.isAutoStart();

		start();
	}

	private MainLoopStates idleState() throws InterruptedException {
		if (autoStart == true) {
			autoStart = false;
			return MainLoopStates.STARTING;
		}
		log.debug("MainLoop waiting for wakeup.");
		wait();
		log.debug("MainLoop wakeing up.");

		return MainLoopStates.RUNNING;
	}

	private MainLoopStates startingState() {
		return MainLoopStates.STARTING;
	}

	private MainLoopStates runngingState() {

		return MainLoopStates.RUNNING;
	}

	public void start() {
		if (getState() == MainLoopStates.IDLE) {
			notify();
		} else {
			log.error("MainLoop is not in a state that allows starting! " + getState());
			throw new IllegalStateException("MainLoop not in IDLE state!");
		}
	}

}
