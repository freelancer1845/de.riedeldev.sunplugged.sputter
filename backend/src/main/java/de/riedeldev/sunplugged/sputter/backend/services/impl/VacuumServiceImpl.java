package de.riedeldev.sunplugged.sputter.backend.services.impl;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import de.riedeldev.sunplugged.sputter.backend.services.VacuumService;
import de.riedeldev.sunplugged.sputter.backend.services.VacuumService.State;
import de.riedeldev.sunplugged.sputter.backend.utils.AbstractStateLoop;
import lombok.extern.slf4j.Slf4j;

@Service
@Scope("application")
@Slf4j
public class VacuumServiceImpl extends AbstractStateLoop<State> implements VacuumService {

	private boolean start;

	public VacuumServiceImpl(@Value("$(loops.vaccum.start:false") boolean start) {
		super();
		setThreadName("Vacuum");

		setState(State.INIT);
		getStates().put(State.INIT, () -> State.IDLE);
		getStates().put(State.IDLE, new Idleling());

		if (start == true) {
			start();
		} else {
			log.warn("VaccumService was not started due to Properties. Try set 'loops.vaccum.start=true'");
		}

	}

	@Override
	public State getState() {
		return super.getState();
	}

	private final class Idleling implements Callable<State> {
		@Override
		public State call() throws Exception {
			Thread.sleep(100);
			return State.IDLE;
		}
	}

}
