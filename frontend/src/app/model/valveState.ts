export interface ValveState {
    simpleCoilValves: SimpleCoilValve[];
}

export interface SimpleCoilValve {
    id: string;
    state: Boolean;
}