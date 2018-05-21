import { Injectable } from '@angular/core';
import { StompService } from '@stomp/ng2-stompjs';
import { ValveState } from '../model/valveState';
import { Observable } from 'rxjs'
import { Message } from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class ValveService {

  private valveState: Observable<ValveState>;

  constructor(private socket: StompService) { 
    this.valveState = socket.subscribe('/topic/valves').map((message: Message) => <ValveState> JSON.parse(message.body));
  }
  public subscribe(next : (state: ValveState) => void) {
    this.valveState.subscribe(next);
  }
}
