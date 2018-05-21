import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { AfterViewInit } from '@angular/core';
import { AfterContentInit } from '@angular/core';
import { ModbusApiService } from '../services/modbus-api.service';
import { WagoIOState } from '../model/wagoIOState';
import { ViewEncapsulation } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ValveState } from '../model/valveState';
import { ValveService } from '../services/valve.service';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'app-vacuum-view',
  templateUrl: './vacuum-view.component.html',
  styleUrls: ['./vacuum-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class VacuumViewComponent implements OnInit {


 
  constructor(private valveService: ValveService, private http: HttpClient, private log: NGXLogger) { }

  private svgLoaded = false;
  private vacuumFlowSVG: VacuumFlowSVG;

  private fastVent: HTMLElement;

  onSVGLoaded(svg: SVGElement, parent: Element): SVGElement{
    return svg;
  }

  onSVGInserted(e: SVGElement) {
    this.vacuumFlowSVG = new VacuumFlowSVG(this.http, this.log);
    this.svgLoaded = true;
  }

  ngOnInit() {
    this.valveService.subscribe(state => this.newValveState(state));
    
  }

  newValveState(state: ValveState): void {
    console.log('New State');
    if (this.svgLoaded === false) {
      return;
    }
    this.vacuumFlowSVG.newValveState(state);
  }

}

class VacuumFlowSVG {


  private simpleCoilOutlets: SimpleCoilOutlet[] = [];

  constructor(private http: HttpClient, private log: NGXLogger) {
    this.http.get('assets/vacuumview/mapping.json').subscribe(data => this.setMapping(data));
  }

  public newValveState(state: ValveState) {
    this.simpleCoilOutlets.forEach(outlet => {
      const matchingValve = state.simpleCoilValves.find(valve => valve.id == outlet.id)
      if (matchingValve == undefined) {
        this.log.error('No matching valve found for id: ' + outlet.id)
      } else {
        outlet.setState(matchingValve.state);
      }
    })
  }

  private setMapping(data: any) {
    this.simpleCoilOutlets = [];
    data['SimpleCoilOutlets'].forEach(element => {
      this.simpleCoilOutlets.push(new SimpleCoilOutlet(element.id, element.svgId));
    });
  }
}

class SimpleCoilOutlet {
  id: string;
  private element: HTMLElement;
  private state: Boolean;

  constructor(id: string, svgId: string) {
    this.id = id;
    this.element = document.getElementById(svgId);
  }

  public setState(state: Boolean) {
    if (this.state == state) {
      return;
    }
    if (state == undefined) {
      this.element.setAttribute('class', 'undefinedoutlet')
    } else if (state === true) {
      this.element.setAttribute('class', 'openoutlet');
    } else if (state === false) {
      this.element.setAttribute('class', 'closedoutlet');
    }
  }

}


