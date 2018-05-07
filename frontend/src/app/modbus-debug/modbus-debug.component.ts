import { Component, OnInit, OnDestroy } from '@angular/core';
import { DigitalModbus } from '../model/digitalModbus';
import { ModbusApiService } from '../services/modbus-api.service';
import { WagoIOState } from '../model/wagoIOState';
import { ModbusRegister } from '../model/modbusRegister';
import { Inplace } from 'primeng/inplace';
import { Subscription } from 'rxjs';
import { StompService, StompState } from '@stomp/ng2-stompjs';
import { Message } from '@stomp/stompjs';
import { GlobalState } from '../model/globalState';
import { StatusService } from '../services/status.service';

@Component({
  selector: 'app-modbus-debug',
  templateUrl: './modbus-debug.component.html',
  styleUrls: ['./modbus-debug.component.css']
})
export class ModbusDebugComponent implements OnInit, OnDestroy {




  coils: DigitalModbus[] = [];
  discreteInputs: DigitalModbus[] = [];
  inputRegisters: ModbusRegister[] = [];

  holdingRegisters: ModbusRegister[] = [];
  private updateHolingRegisters: boolean = true;

  private globalStateSubscription: Subscription;
  private stompStateSubscription: Subscription;
  private httpStateSubscription: Subscription;
  private wagoSubscription: Subscription;

  displayWagoError: boolean;
  displayWebsocketError: boolean;

  constructor(
    private modbusService: ModbusApiService,
    private socket: StompService,
    private statusService: StatusService,
  ) { }

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

    this.globalStateSubscription = this.socket.subscribe('/topic/global')
      .map((message: Message) => <GlobalState>JSON.parse(message.body))
      .subscribe(this.newGlobalState);
    this.stompStateSubscription = this.socket.state.subscribe(state => this.displayWebsocketError = state !== StompState.CONNECTED);
    this.httpStateSubscription = this.statusService.subscribeToStatusChecker(state => this.displayWagoError = !state);
    this.wagoSubscription = this.socket.subscribe('/topic/wagoIO').map((message: Message) => <WagoIOState> JSON.parse(message.body)).subscribe(this.newWagoIOState);
  }

  ngOnDestroy(): void {
    this.globalStateSubscription.unsubscribe();
    this.stompStateSubscription.unsubscribe();
    this.httpStateSubscription.unsubscribe();
    this.wagoSubscription.unsubscribe();
  }


  newWagoIOState = (state: WagoIOState) => {
    this.coils.forEach(coil => coil.state = state.coils.find(newCoil => coil.id == newCoil.id).state)
    this.discreteInputs.forEach(input => input.state = state.discreteInputs.find(newInput => newInput.id == input.id).state);
    if (this.updateHolingRegisters == true) {
      this.holdingRegisters.forEach(reg => reg.value = state.holdingRegisters.find(reg2 => reg.id == reg2.id).value);
    }
    this.inputRegisters.forEach(reg => reg.value = state.inputRegisters.find(reg2 => reg.id == reg2.id).value);
  }

  private newGlobalState = (state: GlobalState) => {
    if (state.wagoConnectedState == false) {
      this.displayWagoError = true;
    } else {
      this.displayWagoError = false;
    }
  }


  stopHoldingRegisterUpdate() {
    this.updateHolingRegisters = false;
  }

  continueHOldingRegisterUpdate() {
    this.updateHolingRegisters = true;
  }
}
