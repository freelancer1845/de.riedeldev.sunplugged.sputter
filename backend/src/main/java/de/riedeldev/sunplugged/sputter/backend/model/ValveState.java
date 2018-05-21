package de.riedeldev.sunplugged.sputter.backend.model;

import java.util.ArrayList;
import java.util.List;
import de.riedeldev.sunplugged.sputter.backend.devices.Devices;
import de.riedeldev.sunplugged.sputter.backend.devices.util.DiscreteValve;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ValveState {

  private List<SimpleCoilValve> simpleCoilValves = new ArrayList<>();


  public ValveState(Devices devices) {
    simpleCoilValves.add(fromDiscreteValve(devices.getFastVent()));
    simpleCoilValves.add(fromDiscreteValve(devices.getSlowVent()));
    simpleCoilValves.add(fromDiscreteValve(devices.getCryo1PurgingValve()));
    simpleCoilValves.add(fromDiscreteValve(devices.getCryo1RoughingValve()));
    simpleCoilValves.add(fromDiscreteValve(devices.getCryo2PurgingValve()));
    simpleCoilValves.add(fromDiscreteValve(devices.getCryo2RoughingValve()));
    simpleCoilValves.add(fromDiscreteValve(devices.getSlowRoughingValve()));
    simpleCoilValves.add(fromDiscreteValve(devices.getFastRoughingValve()));
    simpleCoilValves.add(fromDiscreteValve(devices.getHighVacuumValve1()));
    simpleCoilValves.add(fromDiscreteValve(devices.getHighVacuumValve2()));
    simpleCoilValves.add(fromDiscreteValve(devices.getBaratonGaugeIsolationValve()));
    simpleCoilValves.add(fromDiscreteValve(devices.getFullRangeGaugeIsolationValve()));
  }


  private SimpleCoilValve fromDiscreteValve(DiscreteValve valve) {
    return new SimpleCoilValve(valve.getId(), valve.isOpen());
  }

  @Getter
  @RequiredArgsConstructor
  public class SimpleCoilValve {

    private final String id;
    private final Boolean state;
  }

}
