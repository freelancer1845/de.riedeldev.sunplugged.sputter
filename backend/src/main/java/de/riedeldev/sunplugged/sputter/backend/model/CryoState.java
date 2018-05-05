package de.riedeldev.sunplugged.sputter.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryoState {

	private Boolean compressorStarted;

	private Boolean cryo1CompressorOn;

	private Boolean cryo2CompressorOn;

	private Double cryo1Temp;

	private Double cryo2Temp;

	private Boolean roughValveOne;

	private Boolean roughValveTwo;

	private Boolean purgeValveOne;

	private Boolean purgeValveTwo;

	private Boolean purgeHeaterOne;

	private Boolean purgeHeaterTwo;

}
