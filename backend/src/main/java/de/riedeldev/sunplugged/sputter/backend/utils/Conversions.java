package de.riedeldev.sunplugged.sputter.backend.utils;

public class Conversions {

  public static double convertWagoIOAnalog(Integer mesValue) {
    return mesValue / 32767.0 * 10.0;
  }

}
