import { TestBed, inject } from '@angular/core/testing';

import { PressureService } from './pressure.service';

describe('PressureService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PressureService]
    });
  });

  it('should be created', inject([PressureService], (service: PressureService) => {
    expect(service).toBeTruthy();
  }));
});
