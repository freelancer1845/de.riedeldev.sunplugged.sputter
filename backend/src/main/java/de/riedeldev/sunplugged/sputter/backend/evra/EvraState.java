package de.riedeldev.sunplugged.sputter.backend.evra;

import java.util.ArrayList;
import java.util.List;
import de.riedeldev.sunplugged.sputter.backend.utils.StandardStates;
import lombok.Data;

@Data
public class EvraState {

  private StandardStates state;

  private boolean mpRunning;

  private boolean bpRunning;

  private boolean normalMode;

  private boolean powerSavingMode;

  private final List<EvaraAlarm> alarms = new ArrayList<>();

  private final List<EvaraWarning> warnings = new ArrayList<>();


}
