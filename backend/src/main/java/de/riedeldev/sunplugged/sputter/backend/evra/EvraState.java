package de.riedeldev.sunplugged.sputter.backend.evra;

import lombok.Data;

@Data
public class EvraState {

	private boolean mpRunning;

	private boolean bpRunning;

	private boolean normalMode;

	private boolean powerSavingMode;

	private boolean[] warnings;

	private boolean[] alarms;

}
