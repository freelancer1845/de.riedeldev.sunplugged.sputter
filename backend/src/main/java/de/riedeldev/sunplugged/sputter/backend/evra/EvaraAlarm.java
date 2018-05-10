package de.riedeldev.sunplugged.sputter.backend.evra;

import lombok.Getter;

@Getter
public class EvaraAlarm {

  private static final String[] DESCRIPTIONS = {"Casing temp. HH", "BP motor temp. high",
      "MP motor temp. high", "Water leakage", "BP thermal", "MP thermal", "", "", "", "",
      "MP no current", "", "", "Back press. high", "Power failure", "MP driver protection active",
      "BP driver protection active", "BP overload 2", "MP overload 2", "BP step out", "MP step out",
      "Emergency off (EMO)", "Exh. N2 flow low", "Water flow low continued", "External interlock",
      "", "", "", "", "", "", "Other alarms"};

  private final int no;
  private final String description;

  public EvaraAlarm(int no) {
    this.no = no;
    this.description = DESCRIPTIONS[no];
  }

}
