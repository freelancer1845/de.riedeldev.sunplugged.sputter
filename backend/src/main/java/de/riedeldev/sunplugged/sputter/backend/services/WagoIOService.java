package de.riedeldev.sunplugged.sputter.backend.services;

import java.util.List;
import java.util.stream.Stream;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.Coil;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.DiscreteInput;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.HoldingRegister;
import de.riedeldev.sunplugged.sputter.backend.model.modbus.InputRegister;

/**
 * 
 * Service for accessing all hardware related controls.
 * 
 * @author Jascha Riedel
 *
 */
public interface WagoIOService {

  public boolean isOk();

  public List<Coil> getCoils();

  // public List<Boolean> getCoils();

  // public List<Boolean> getDiscreteInputs();

  public List<DiscreteInput> getDiscreteInputs();

  // public List<Integer> getHoldingRegisters();

  public List<HoldingRegister> getHoldingRegisters();

  // public List<Integer> getInputRegisters();

  public List<InputRegister> getInputRegisters();

  public void setCoil(Coil coil);

  public void setHoldindRegister(HoldingRegister reg);


  public boolean readDO(DO digitalOutput);

  public boolean readDI(DI di);

  public void setDO(DO digitalOutput, boolean value);

  public Integer readAI(AI ai);

  public Integer readAO(AO ao);

  public void setAO(AO ao, Integer value);

  public enum DI {
    DOOR_SWITCH(0), ATM_SWITCH(1), GUN4_SHUTTLE_CENTERING(2), WATER_LEAK_SENSOR(3),
    HI_VAC_VALVE1_OPEN(4), HI_VAC_VALVE1_CLOSED(5), HI_VAC_VALVE2_OPEN(6), HI_VAC_VALVE2_CLOSED(7),
    GLASS_LOADER_SHUTTLE_POS(8), GLASS_LOADER_LOADING_POS(9), GLASS_LOADER_STANDBY_POS(10),
    GUN2_SHUTTER_OPEN(11), GUN2_SHUTTER_CLOSE(12), GUN2_SHUTTER_ALARM(13), GUN3_SHUTTER_OPEN(14),
    GUN3_SHUTTER_CLOSE(15), GUN3_SHUTTER_ALARM(16), TRAY_MOVING_POSITION_SENSOR1(17),
    TRAY_MOVING_POSITION_SENSOR2(18), TRAY_MOVING_POSITION_SENSOR3(19),
    TRAY_MOVING_POSITION_SENSOR4(20), TRAY_MOVING_POSITION_SENSOR5(21),
    TRAY_MOVING_POSITION_SENSOR6(22), TRAY_MOVING_POSITION_SENSOR7(23),
    TRAY_MOVING_POSITION_SENSOR8(24), TRAY_MOVING_POSITION_SENSOR9(25),
    TRAY_MOVING_POSITION_SENSOR10(26), WATER_FLOW_SWITCH_GUN1_1(27), WATER_FLOW_SWITCH_GUN1_2(28),
    WATER_FLOW_SWITCH_GUN1_3(29), WATER_FLOW_SWITCH_GUN1_4(30), WATER_FLOW_SWITCH_GUN2_1(31),
    WATER_FLOW_SWITCH_GUN2_2(32), WATER_FLOW_SWITCH_GUN3_1(33), WATER_FLOW_SWITCH_GUN3_2(34),
    WATER_FLOW_SWITCH_GUN4_1(35), WATER_FLOW_SWITCH_GUN4_2(36), SHUTTLE_CENTERING_90D_GUN4(37),
    SHUTTLE_CENTERING_UP_GUN4(38), SHUTTLE_CENTERING_DOWN_GUN4(39),
    // Missing TM Inline No Idea - next 40 - 47
    COMPRESSOR1_STARTED(48),
    /**
     * NC = GUN1,2 NO = GUN3,4
     */
    DC_POWER_SELECT_MASTER(49),
    /**
     * NC = GUN1,2 NO = GUN3,4
     */
    RF_POWER_SELECT_MASTER(50),
    /**
     * NC = GUN1 NO = GUN2
     */
    RF_POWER_SELECT_SLAVE1(51),
    /**
     * NC = GUN3 NO = GUN4
     */
    RF_POWER_SELECT_SLAVE2(52),
    /**
     * NC = GUN1 NO = GUN2
     */
    DC_POWER_SELECT_SLAVE1(53),
    /**
     * NC = GUN3 NO = GUN4
     */
    DC_POWER_SELECT_SLAVE2(54), ROTRY_ACTUATOR_LOADING_POS(56), ROTRY_ACTUATOR_UNLOADING_POS(57),
    SHUTTLE_UP(58), SHUTTL_DOWN(59), GLASS_BACK_PLATE_UP(60), GLASS_BACK_PLATE_DOWN(61),
    MASK_UP(62), MASK_DOWN(63), GUN1_STOPPERUP(64), GUN1_STOOPERDOWN(65), GUN1_COOLING_PLATE_0D(66),
    GUN1_COOLING_PLATE_90D(67), GUN1_COOLING_PLATE_1STEP_UP(68), GUN1_COOLING_PLATE_1STEP_DOWN(69),
    GUN1_COOLING_PLATE_2STEP_UP(70), GUN1_COOLING_PLATE_2STEP_DOWN(71),
    GUN1_COOLING_PLATE_SHUTTER_OPEN(72), GUN1_COOLING_PLATE_SHUTTER_CLOSED(73), GUN4_STOPPER_UP(74),
    GUN4_STOPPER_DOWN(75), GUN4_MAGNECT_PLATE_UP(76), GUN4_MAGNECT_PLATE_DOWN(77),
    GUN1_SHUTTLE_CENTERING_UP(78), GUN1_SHUTTLE_CENTERING_DOWN(79);

    private final int address;

    private DI(int address) {
      this.address = address;
    }

    public int getAddress() {
      return address;
    }

    public static int maxAddress() {
      return Stream.of(values()).mapToInt(o -> o.getAddress()).max().getAsInt();
    }
  }


  public enum DO {
    HI_VAC_VALVE1_OPEN_CLOSE(0), HI_VAC_VALVE2_OPEN_CLOSE(1), FAST_ROUGHING_VALVE(2),
    SLOW_ROUGHING_VALVE(3), GUN3_AR_100SCCM_OUTLET(4), FAST_VENT_VALVE(5), SLOW_VENT_VALVE(6),
    CRYO1_ROUGH_VALVE(7), CRYO1_PURGE_VALVE(8), FULL_RANGE_GAUGE_ISOLATION_VALVE(9),
    BARATRON_GAUGE_ISOLATION_VALVE(10), ROTARY_ACTUATOR_LOADING_POS(11),
    ROTARY_ACTUATOR_UNLOADING_POS(12), SHUTTLE_UP(13), SHUTTLE_DOWN(14), GLASS_BACK_PLATE_UP(15),
    GLASS_BACK_PLATE_DOWN(16), MASK_UP(17), MASK_DOWN(18), CRYO2_ROUGH_VALVE(19),
    CRYO2_PURGE_VALVE(20), FULLRANGE_GAUGE_DEGAS_ON(21), CRYO1_PURGE_HEATER_ON_OFF(22),
    CRYO2_PURGE_HEATER_ON_OFF(23),
    /**
     * NC = GUN1,2 NO = GUN3,4
     */
    DC_POWER_SELECT_MASTER(24),
    /**
     * NC = GUN1,2 NO = GUN3,4
     */
    RF_POWER_SELECT_MASTER(25),
    /**
     * NC = GUN1 NO = GUN2
     */
    RF_POWER_SELECT_SLAVE1(26),
    /**
     * NC = GUN3 NO = GUN4
     */
    RF_POWER_SELECT_SLAVE2(27), GUN2_SHUTTER_RUN_STOP(28), GUN2_SHUTTER_CW_CCW(29),
    GUN2_SHUTTER_INT_EXT(30), GUN2_SHUTTER_ALARM_RESET(31), GUN3_SHUTTER_RUN_STOP(32),
    GUN3_SHUTTER_CW_CCW(33), GUN3_SHUTTER_INT_EXT(34), GUN3_SHUTTER_ALARM_RESET(35),
    COMPRESSOR1_CRYO1_START(36), COMPRESSOR1_CRYO2_START(37),
    /**
     * NC = GUN1 NO = GUN2
     */
    DC_POWER_SELECT_SLAVE1(38),
    /**
     * NC = GUN3 NO = GUN4
     */
    DC_POWER_SELECT_SLAVE2(39),
    // Missing TM Line, again no idea
    GEN1_EPC_INLET(48), GEN2_O2_OUTLET(49), GEN1_N2_OUTLET(50), GEN3_O2_OUTLET_(51),
    GEN3_N2_OUTLET(52), GUN1_AR_OUTLET(53), GUN1_COOLING_PLATE_PURGE(54),
    GUN4_SHUTTLE_CENTERING_0_90D(56), GUN4_SHUTTLE_CENTERING_UP_DOWN(58), N2_MFC_SELECT(60),
    GUN1_STOPPER_UP_DOWN(64), GUN4_STOPPER_UP_DOWN(65), GUN1_COOLING_PLATE_0D(66),
    GUN1_COOLING_PLATE_90D(67), GUN1_COOLING_PLATE_1STEP_UP(68), GUN1_COOLING_PLATE_1STEP_DOWN(69),
    GUN1_COOLING_PLATE_2STEP_UP(70), GUN1_COOLING_PLATE_2STEP_DOWN(71),
    GUN1_COOLING_PLATE_SHUTTER_OPEN(72), GUN1_COOLING_PLATE_SHUTTER_CLOSE(73),
    GUN4_MAGNECT_PLATE_UP(74), GUN4_MAGNECT_PLATE_DOWN(75), GUN4_STOPPER_UP_DOWN2(76),
    GUN1_SHUTTLE_CENTERING_UP_DOWN(77);

    private final int address;

    private DO(int address) {
      this.address = address;
    }

    public int getAddress() {
      return address;
    }

    public static int maxAddress() {
      return Stream.of(values()).mapToInt(o -> o.getAddress()).max().getAsInt();
    }
  }

  public enum AI {
    FULL_RANGE_GAUGE(0), CRYO1_TEMP(1), CRYO2_TEMP(2), MFC_1_AR_100SCCM(3), EPC_HE_PRESSURE(4),
    EPC_HE_FLOW(5);

    private final int address;

    private AI(int address) {
      this.address = address;
    }

    public int getAddress() {
      return address;
    }

    public static int maxAddress() {
      return Stream.of(values()).mapToInt(o -> o.getAddress()).max().getAsInt();
    }
  }

  public enum AO {
    MFC_1_AR_50SCCM(0), EPC_HE_OUTPUT(1);

    private final int address;

    private AO(int address) {
      this.address = address;
    }

    public int getAddress() {
      return address;
    }

    public static int maxAddress() {
      return Stream.of(values()).mapToInt(o -> o.getAddress()).max().getAsInt();
    }
  }


}
