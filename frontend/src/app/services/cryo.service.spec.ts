import { TestBed, inject } from '@angular/core/testing';

import { CryoService } from './cryo.service';

describe('CryoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CryoService]
    });
  });

  it('should be created', inject([CryoService], (service: CryoService) => {
    expect(service).toBeTruthy();
  }));
});
