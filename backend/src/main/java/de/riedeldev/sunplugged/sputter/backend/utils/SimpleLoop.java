package de.riedeldev.sunplugged.sputter.backend.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SimpleLoop extends AbstractStateLoop<StandardStates> {

	public SimpleLoop() {
		super();
		getStates().put(StandardStates.INIT, () -> {
			return StandardStates.RUNNING;
		});

		getStates().put(StandardStates.RUNNING, () -> {
			try {
				tick();
			} catch (Exception e) {
				log.error("Unhandled Exception in simple loop. The loop will stop.", e);
				return StandardStates.ERROR;
			}
			return StandardStates.RUNNING;

		});

		getStates().put(StandardStates.ERROR, () -> {
			finish();
			return StandardStates.ERROR;
		});

		setState(StandardStates.INIT);
	}

	public abstract void tick() throws Exception;

}
