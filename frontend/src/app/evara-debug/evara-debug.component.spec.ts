import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaraDebugComponent } from './evara-debug.component';

describe('EvaraDebugComponent', () => {
  let component: EvaraDebugComponent;
  let fixture: ComponentFixture<EvaraDebugComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvaraDebugComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaraDebugComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
