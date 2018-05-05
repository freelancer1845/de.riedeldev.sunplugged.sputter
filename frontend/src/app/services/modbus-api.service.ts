import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';
import { Observable } from 'rxjs';
import { DigitalModbus } from '../model/digitalModbus';
import { ModbusRegister } from '../model/modbusRegister';

@Injectable()
export class ModbusApiService {

  private api_url = environment.server_api + 'modbus/';

  constructor(private http: HttpClient) { }

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
