import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJs from 'sockjs-client';
import { Observable } from 'rxjs';
import { SocketServiceService } from '../services/socket-service.service';
import { GlobalState } from '../model/globalState';
import { WagoIOState } from '../model/wagoIOState';

@Component({
  selector: 'app-status-view',
  templateUrl: './status-view.component.html',
  styleUrls: ['./status-view.component.css']
})
export class StatusViewComponent implements OnInit {

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
  constructor(private socket: SocketServiceService) { }

  ngOnInit() {
    setTimeout(() => {
      this.socket.getObservable<GlobalState>('/topic/global').subscribe((state) => this.newGlobalState(state));
    }, 2000);
  }

}
