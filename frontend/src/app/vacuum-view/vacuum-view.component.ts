import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { AfterViewInit } from '@angular/core';
import { AfterContentInit } from '@angular/core';
import { ModbusApiService } from '../services/modbus-api.service';
import { WagoIOState } from '../model/wagoIOState';
import { ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-vacuum-view',
  templateUrl: './vacuum-view.component.html',
  styleUrls: ['./vacuum-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class VacuumViewComponent implements OnInit {


  constructor(private modbus: ModbusApiService) { }

  private svgLoaded = false;
  private fastVent: HTMLElement;

  onSVGLoaded(svg: SVGElement, parent: Element): SVGElement{
    return svg;
  }

  onSVGInserted(e: SVGElement) {
    this.fastVent = document.getElementById('cell-3f5fc188c4f296b7-5');

    this.svgLoaded = true;
  }

  ngOnInit() {
    this.modbus.subscribe((state) => this.newWagoIOState(state));
  }

  private newWagoIOState(state: WagoIOState) {
    console.log('New State');
    if (this.svgLoaded === false) {
      return;
    }

    if (state.discreteInputs[0].state) {
      console.log('should be green');
      this.fastVent.setAttribute('class', 'openoutlet');
    } else {
      console.log('should be green');
      this.fastVent.setAttribute('class', 'closedoutlet');

    }
  }


}


