package de.riedeldev.sunplugged.sputter.backend.evra;

import lombok.Getter;

@Getter
public class EvaraWarning {


  private static final String[] DESCRIPTIONS = {"Water flow low", "", "", "", "",
      "Casing temp. high", "BP-G oil level low", "BP-M oil level low", "MP-G oil level low",
      "MP-M oil level low", "Drv grg temp. high", "Drvn brg temp. high", "Oil level low",
      "BOX temp. high", "N2 valve open", "Cooler 1 temp. high", "Cooler 2 temp. high",
      "Cooler 3 temp. high", "Pump N2 flow low", "Exh. N2 flow low", "Back press. hgih",
      "Heater error", "BP motor temp. high", "MP motor temp. high", "Driver temp. high",
      "Communication error", "Valve error", "", "", "", "Other warning..."};


  private final String description;

  private final int no;


  public EvaraWarning(int no) {
    this.no = no;
    this.description = DESCRIPTIONS[no];
  }

}
