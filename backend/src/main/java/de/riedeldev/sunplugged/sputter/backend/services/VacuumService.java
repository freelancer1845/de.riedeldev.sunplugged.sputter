package de.riedeldev.sunplugged.sputter.backend.services;

public interface VacuumService {

	public State getState();

	public enum State {
		INIT, IDLE, ERROR, EVACUATING, VACUUM_READY;
	}

}
