package de.riedeldev.sunplugged.sputter.backend.utils;

public class Conversions {

	public static double convertWagoIOAnalog(Integer mesValue) {
		return mesValue / 65535.0 * 10.0;
	}

}
