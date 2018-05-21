package de.riedeldev.sunplugged.sputter.backend.model;

import de.riedeldev.sunplugged.sputter.backend.evra.EvraState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StateData {

  private GlobalState globalState = new GlobalState();

  private ShutterState shutterState = new ShutterState();

  private PowerSourceState powerSourceState = new PowerSourceState();

  private PressureMesState pressureMesState;

  public void setPressureMesState(PressureMesState pressureMesState) {
    this.pressureMesState = pressureMesState;
  }

  private CryoState cryoState = new CryoState();

  private WagoIOData wagoIOData = new WagoIOData();

  private EvraState evraState = new EvraState();

  private ValveState valveState;

  public void setValveState(ValveState valveState) {
    this.valveState = valveState;
  }

}
