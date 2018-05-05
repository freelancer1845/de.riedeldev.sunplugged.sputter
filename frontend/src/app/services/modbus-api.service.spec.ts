import { TestBed, inject } from '@angular/core/testing';

import { ModbusApiService } from './modbus-api.service';

describe('ModbusApiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ModbusApiService]
    });
  });

  it('should be created', inject([ModbusApiService], (service: ModbusApiService) => {
    expect(service).toBeTruthy();
  }));
});
