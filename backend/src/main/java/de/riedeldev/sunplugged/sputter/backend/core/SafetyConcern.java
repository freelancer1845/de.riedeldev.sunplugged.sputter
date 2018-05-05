package de.riedeldev.sunplugged.sputter.backend.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SafetyConcern {

	public static final int MINOR = 0;

	public static final int WARNING = 1;

	public static final int CRITICAL = 2;

	private final int severity;

	private final String message;

}
