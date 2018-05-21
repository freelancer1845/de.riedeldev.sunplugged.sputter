import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VacuumViewComponent } from './vacuum-view.component';

describe('VacuumViewComponent', () => {
  let component: VacuumViewComponent;
  let fixture: ComponentFixture<VacuumViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VacuumViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VacuumViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
