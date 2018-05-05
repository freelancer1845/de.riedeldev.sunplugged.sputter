package de.riedeldev.sunplugged.sputter.backend.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Assert {

	public static <T> void notNull(T object) {
		if (object == null) {
			log.error("Null Object found.");
			throw new AssertionError("Object was null.");
		}
	}

	public static <T> boolean isNull(T object, String message) {
		if (message == null) {
			message = "";
		}
		log.debug("Null object. " + message);
		return object == null;
	}

}
