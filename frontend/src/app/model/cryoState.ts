export interface CryoState {
    
    compressorStarted: boolean;

    cryo1CompressorOn: boolean;

    cryo2CompressorOn: boolean;

    cryo1Temp: number;

    cryo2Temp: number;

    roughValveOne: boolean;

    roughValveTwo: boolean;

    purgeValveOne: boolean;

    purgeValveTwo: boolean;

    purgeHeaterOne: boolean;

    purgeHeaterTwo: boolean;
}