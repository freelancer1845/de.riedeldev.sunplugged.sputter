package de.riedeldev.sunplugged.sputter.backend.pinnacle;

import org.springframework.stereotype.Service;

import de.riedeldev.sunplugged.sputter.backend.configurations.PinnacleConfiguration;
import de.riedeldev.sunplugged.sputter.backend.utils.StandardStates;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PinnacleOne extends AbstractPinnacleControl {

	public PinnacleOne(PinnacleConfiguration config) {
		super("Pinnacle One", config.getOne().getPort(), config);
		if (config.getOne().isStart()) {
			start();
		} else {
			log.warn("PinnacleOne loop was not started. Try set propertie 'pinnacleone.start=true'");
		}
	}

	@Override
	protected StandardStates error() {
		finish();
		log.error("PinnacleOne stopped abornamly");
		return StandardStates.ERROR;
	}

}
