import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModbusDebugComponent } from './modbus-debug.component';

describe('ModbusDebugComponent', () => {
  let component: ModbusDebugComponent;
  let fixture: ComponentFixture<ModbusDebugComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModbusDebugComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModbusDebugComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
