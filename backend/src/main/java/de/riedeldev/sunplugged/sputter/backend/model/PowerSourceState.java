package de.riedeldev.sunplugged.sputter.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PowerSourceState {

	private boolean DC_POWER_MASTER;

	private boolean RF_POWER_MASTER;

	private boolean DC_POWER_SLAVE1;

	private boolean DC_POWER_SLAVE2;

	private boolean RF_POWER_SLAVE1;

	private boolean RF_POWER_SLAVE2;

	private Guns DC_selectedGun;

	private Guns RF_selectedGun;

}
