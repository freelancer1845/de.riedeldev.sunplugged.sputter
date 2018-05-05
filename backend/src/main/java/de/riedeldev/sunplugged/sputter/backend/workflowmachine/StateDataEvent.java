package de.riedeldev.sunplugged.sputter.backend.workflowmachine;

import org.springframework.context.ApplicationEvent;

import de.riedeldev.sunplugged.sputter.backend.model.StateData;

public class StateDataEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8854390879793613515L;

	public StateDataEvent(StateData source) {
		super(source);
	}

	@Override
	public StateData getSource() {
		return (StateData) super.getSource();
	}

}
