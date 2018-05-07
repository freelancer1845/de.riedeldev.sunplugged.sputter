import { TestBed, inject } from '@angular/core/testing';

import { FooterErrorService } from './footer-error.service';

describe('FooterErrorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FooterErrorService]
    });
  });

  it('should be created', inject([FooterErrorService], (service: FooterErrorService) => {
    expect(service).toBeTruthy();
  }));
});
