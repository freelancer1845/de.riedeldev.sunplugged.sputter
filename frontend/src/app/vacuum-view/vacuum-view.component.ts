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
import { PressureMesState } from '../model/pressureMesState';
import { PressureService } from '../services/pressure.service';
import { CryoState } from '../model/cryoState';
import { UV_UDP_REUSEADDR, ENGINE_METHOD_DIGESTS } from 'constants';
import { CryoService } from '../services/cryo.service';

@Component({
  selector: 'app-vacuum-view',
  templateUrl: './vacuum-view.component.html',
  styleUrls: ['./vacuum-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class VacuumViewComponent implements OnInit {




  constructor(private valveService: ValveService, private pressureService: PressureService, private cryoService: CryoService, private http: HttpClient, private log: NGXLogger) { }

  private svgLoaded = false;
  private vacuumFlowSVG: VacuumFlowSVG;

  private fastVent: HTMLElement;

  onSVGLoaded(svg: SVGElement, parent: Element): SVGElement {
    svg.setAttribute('viewBox', '0 0 742 578');
    svg.setAttribute('style', 'width:100%;height:100%');
    return svg;
  }

  onSVGInserted(e: SVGElement) {
    this.vacuumFlowSVG = new VacuumFlowSVG(this.http, this.log);
    this.svgLoaded = true;
  }

  ngOnInit() {
    this.valveService.subscribe(state => this.newValveState(state));
    this.pressureService.subscribe(state => this.newPressureState(state));
    this.cryoService.subscribe(state => this.newCryoState(state));
  }

  newValveState(state: ValveState): void {
    if (this.svgLoaded === false) {
      return;
    }
    this.vacuumFlowSVG.newValveState(state);
  }

  newPressureState(state: PressureMesState): void {
    if (this.svgLoaded === false) {
      return;
    }
    this.vacuumFlowSVG.newPressureMesState(state);
  }

  newCryoState(state: CryoState): any {
    if (this.svgLoaded === false) {
      return;
    }
    this.vacuumFlowSVG.newCryoMesState(state);
  }

}

class VacuumFlowSVG {



  private simpleCoilOutlets: SimpleCoilOutlet[] = [];
  private pressureSites: NumberSite[] = [];
  private cryoTempSites: NumberSite[] = [];
  private compressorSites: SimpleCoilOutlet[] = [];
  private purgeHeater: SimpleCoilOutlet[] = [];

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

  newPressureMesState(state: PressureMesState): any {
    this.pressureSites.forEach(site => {
      const value = state[site.name];
      if (value != undefined) {
        site.setValue(value);
      } else {
        this.log.debug('No Pressure site with name: ' + site.name);
      }
    });
  }

  newCryoMesState(state: CryoState) {
    this.cryoTempSites.forEach(site => {
      const value = state[site.name];
      if (value != undefined) {
        site.setValue(value);
      } else {
        this.log.debug('No Cryo Temperature site with name: ' + site.name);
      }
    });
    this.compressorSites.forEach(site => {
      const value = state[site.id];
      if (value != undefined) {
        site.setState(value);
      } else {
        this.log.debug('No Compressor site with name: ' + site.id);
      }
    });
    this.purgeHeater.forEach(site => {
      const value = state[site.id];
      if (value != undefined) {
        site.setState(value);
      } else {
        this.log.debug('No heater found with name: ' + site.id);
      }
    })
  }

  private setMapping(data: any) {
    data['SimpleCoilOutlets'].forEach(element => {
      this.simpleCoilOutlets.push(new SimpleCoilOutlet(element.id, element.svgId));
    });
    data['PressureSites'].forEach(element => {
      this.pressureSites.push(new NumberSite(element.name, element.svgId));
    });
    data['CryoStateSites']['temp'].forEach(element => {
      this.cryoTempSites.push(new NumberSite(element.name, element.svgId));
    });
    data['CryoStateSites']['compressor'].forEach(element => {
      this.compressorSites.push(new SimpleCoilOutlet(element.name, element.svgId, 'cryoon', 'cryooff'));
    })
    data['CryoStateSites']['heater'].forEach(element => {
      this.purgeHeater.push(new SimpleCoilOutlet(element.name, element.svgId));
    });
  }
}

class SimpleCoilOutlet {

  classOn: string;
  classOff: string;
  id: string;
  private element: HTMLElement;
  private state: Boolean;

  constructor(id: string, svgId: string, classOn?: string, classOff?: string) {
    this.id = id;
    this.element = document.getElementById(svgId);
    this.classOn = classOn ? classOn : 'openoutlet';
    this.classOff = classOff ? classOff : 'closedoutlet';
  }

  public setState(state: Boolean) {
    if (this.state == state) {
      return;
    }
    if (state == undefined) {
      this.element.setAttribute('class', 'undefinedoutlet')
    } else if (state === true) {
      this.element.setAttribute('class', this.classOn);
    } else if (state === false) {
      this.element.setAttribute('class', this.classOff);
    }
  }

}

class NumberSite {
  name: string;
  private element: HTMLElement;
  private value: number;

  constructor(name: string, svgId: string) {
    this.name = name;
    this.element = document.getElementById(svgId);
  }

  public setValue(value: number) {
    if (value == this.value) {
      return;
    }
    const text = this.element.getElementsByTagName('text')[0];
    text.innerHTML = value.toPrecision(3);
  }

}




