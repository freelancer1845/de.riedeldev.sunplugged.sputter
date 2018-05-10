import { TestBed, inject } from '@angular/core/testing';

import { EvaraService } from './evara.service';

describe('EvaraService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EvaraService]
    });
  });

  it('should be created', inject([EvaraService], (service: EvaraService) => {
    expect(service).toBeTruthy();
  }));
});
