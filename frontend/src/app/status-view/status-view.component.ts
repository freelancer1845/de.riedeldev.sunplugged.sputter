import { Component, OnInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { GlobalState } from '../model/globalState';
import { WagoIOState } from '../model/wagoIOState';
import { StatusService } from '../services/status.service';
import { StompService } from '@stomp/ng2-stompjs';
import { FooterErrorService } from '../footer/footer-error.service';
import { Message } from '@stomp/stompjs';
import 'rxjs/add/operator/map';
import {Observable, Subscription} from 'rxjs';


@Component({
  selector: 'app-status-view',
  templateUrl: './status-view.component.html',
  styleUrls: ['./status-view.component.css']
})
export class StatusViewComponent implements OnInit,OnDestroy {

  
  @ViewChild("wagoOn") wagoWorking: ElementRef;
  @ViewChild("evraConnected") evraConnected: ElementRef;
  @ViewChild('pinnacleOneConnected') pinnacleOneConnected: ElementRef;
  @ViewChild('pinnacleTwoConnected') pinnacleTwoConnected: ElementRef;

  private newGlobalState(state: GlobalState) {

    if (state.wagoConnectedState == false) {
      this.changeElementState(this.wagoWorking, 'Modbus Connection is not working!', false);
    } else {
      this.changeElementState(this.wagoWorking, 'Modbus Connection is working!', true);
    }
    if (state.evraConnectedState == false) {
      this.changeElementState(this.evraConnected, 'Evara Pump is not connected!', false);
    } else {
      this.changeElementState(this.evraConnected, 'Evara Pump is connected!', true);
    }
    if (state.pinnacleOneConnected == false) {
      this.changeElementState(this.pinnacleOneConnected, 'Pinnacle One not connected!', false);
    } else {
      this.changeElementState(this.pinnacleOneConnected, 'Pinnacle One connected!', true);
    }
    if (state.pinnacleTwoConnected == false) {
      this.changeElementState(this.pinnacleTwoConnected, 'Pinnacle Two not connected!', false);
    } else {
      this.changeElementState(this.pinnacleTwoConnected, 'Pinnacle Two connected!', true);
    }

  }


  private changeElementState(element: ElementRef, text: string, state: boolean) {
    element.nativeElement.innerText = text;
    if (state == true) {
      element.nativeElement.setAttribute('style', 'color:green');
    } else {
      element.nativeElement.setAttribute('style', 'color:red');
    }
  }
  constructor(
    private stomp: StompService,
    private errorService: FooterErrorService,
    private statusService: StatusService
  ) { }


  private globalSubscription: Subscription;

  ngOnInit() {
    this.globalSubscription = this.stomp.subscribe('/topic/global').map((message: Message) => <GlobalState>JSON.parse(message.body)).subscribe(state => this.newGlobalState(state));

  }

  ngOnDestroy(): void {
    this.globalSubscription.unsubscribe();
  }

}
