import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';
import { Observable } from 'rxjs';
import { DigitalModbus } from '../model/digitalModbus';
import { ModbusRegister } from '../model/modbusRegister';
import { WagoIOState } from '../model/wagoIOState';
import { StompService } from '@stomp/ng2-stompjs';
import { Message } from 'stompjs';

@Injectable()
export class ModbusApiService {

  private api_url = environment.server_api + 'modbus/';

  private stateObservable: Observable<WagoIOState>;

  constructor(private http: HttpClient, private socket: StompService) { 
    this.stateObservable = this.socket.subscribe('/topic/wagoIO').map((message: Message) => <WagoIOState> JSON.parse(message.body));
  }

  subscribe(next: (state: WagoIOState) => void) {
    console.log('Subscribed');
    this.stateObservable.subscribe(next);
  }

  setCoil(coil: DigitalModbus): Observable<any> {

    return this.http.post(this.api_url + 'submitcoils', [coil]);
  }

  getCoils(): Observable<DigitalModbus[]> {

    return this.http.get<DigitalModbus[]>(this.api_url + 'coils');
  }

  getDiscreteInputs(): Observable<DigitalModbus[]> {
    return this.http.get<DigitalModbus[]>(this.api_url + 'discreteinputs');
  }

  getHoldingRegisters(): Observable<ModbusRegister[]> {
    return this.http.get<ModbusRegister[]>(this.api_url + 'holdingRegisters');
  }

  getInputRegisters(): Observable<ModbusRegister[]> {
    return this.http.get<ModbusRegister[]>(this.api_url + 'inputRegisters');
  }

  setHoldingRegister(register: ModbusRegister): Observable<any> {
    return this.http.post(this.api_url + 'submitholdingregisters', [register]);
  }

}
