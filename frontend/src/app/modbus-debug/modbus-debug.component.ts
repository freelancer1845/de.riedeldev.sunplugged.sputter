import { Component, OnInit } from '@angular/core';
import { DigitalModbus } from '../model/digitalModbus';
import { ModbusApiService } from '../services/modbus-api.service';
import { SocketServiceService } from '../services/socket-service.service';
import { WagoIOState } from '../model/wagoIOState';
import { ModbusRegister } from '../model/modbusRegister';
import { Inplace } from 'primeng/inplace';

@Component({
  selector: 'app-modbus-debug',
  templateUrl: './modbus-debug.component.html',
  styleUrls: ['./modbus-debug.component.css']
})
export class ModbusDebugComponent implements OnInit {


  coils: DigitalModbus[] = [];
  discreteInputs: DigitalModbus[] = [];
  inputRegisters: ModbusRegister[] = [];

  holdingRegisters: ModbusRegister[] = [];
  private updateHolingRegisters: boolean = true;

  constructor(private modbusService: ModbusApiService, private socket: SocketServiceService) { }

  requestCoilToggle(coil: DigitalModbus, value: boolean) {

    const newCoil = new DigitalModbus();
    newCoil.id = coil.id;
    newCoil.name = coil.name;
    newCoil.address = coil.address;
    newCoil.state = value;
    this.modbusService.setCoil(newCoil).subscribe();
  }

  submitHoldingRegister(register: ModbusRegister, inplace: Inplace) {
    inplace.deactivate(undefined);


    this.modbusService.setHoldingRegister(register).subscribe();
  }

  cancelHoldingRegister(inplace: Inplace) {
    inplace.deactivate(undefined);
    this.modbusService.getHoldingRegisters().subscribe(holdingRegister => this.holdingRegisters = holdingRegister);
  }

  ngOnInit() {
    this.modbusService.getCoils().subscribe(coils => this.coils = coils);
    this.modbusService.getDiscreteInputs().subscribe(discreteInputs => this.discreteInputs = discreteInputs);
    this.modbusService.getHoldingRegisters().subscribe(holdingRegisters => this.holdingRegisters = holdingRegisters);
    this.modbusService.getInputRegisters().subscribe(inputRegisters => this.inputRegisters = inputRegisters);

    setTimeout(() => {
      this.socket.getObservable<WagoIOState>("/topic/wagoIO").subscribe(state => this.newWagoIOState(state));
    }, 2000)



  }

  newWagoIOState(state: WagoIOState) {
    
    this.coils.forEach(coil => coil.state =  state.coils.find(newCoil => coil.id == newCoil.id).state)
    this.discreteInputs.forEach(input => input.state = state.discreteInputs.find(newInput => newInput.id == input.id).state);
    if (this.updateHolingRegisters == true) {
      this.holdingRegisters.forEach(reg => reg.value = state.holdingRegisters.find(reg2 => reg.id == reg2.id).value);
    }
    this.inputRegisters.forEach(reg => reg.value = state.inputRegisters.find(reg2 => reg.id == reg2.id).value);
  }


  stopHoldingRegisterUpdate() {
    this.updateHolingRegisters = false;
  }

  continueHOldingRegisterUpdate() {
    this.updateHolingRegisters = true;
  }
}
