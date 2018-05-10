import { StandardStates } from "./standardStates";

export class EvaraState {
    state: StandardStates;
    mpRunning: boolean;
    bpRunning: boolean;
    normalMode: boolean;
    powerSavingMode: boolean;
    warnings: EvaraMessage[];
    alarms: EvaraMessage[];

}

export class EvaraMessage {
    no: number;
    message: string;
}