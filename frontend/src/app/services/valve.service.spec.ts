import { TestBed, inject } from '@angular/core/testing';

import { ValveService } from './valve.service';

describe('ValveService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ValveService]
    });
  });

  it('should be created', inject([ValveService], (service: ValveService) => {
    expect(service).toBeTruthy();
  }));
});
