import { Injectable } from '@angular/core';
import { StompService } from '@stomp/ng2-stompjs';
import { PressureMesState } from '../model/pressureMesState';
import { Observable } from 'rxjs';
import { Message } from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class PressureService {

  private stateObservable: Observable<PressureMesState>;

  constructor(private socket: StompService) {
    this.stateObservable = socket.subscribe('/topic/pressure').map((message: Message) => <PressureMesState> JSON.parse(message.body));
   }

   subscribe(next: (state: PressureMesState) => void) {
     this.stateObservable.subscribe(next);
   }
}
