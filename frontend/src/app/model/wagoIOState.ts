import { DigitalModbus } from "./digitalModbus";
import { ModbusRegister } from "./modbusRegister";

export class WagoIOState {
    coils: DigitalModbus[];
    discreteInputs: DigitalModbus[];
    holdingRegisters: ModbusRegister[];
    inputRegisters: ModbusRegister[];
};