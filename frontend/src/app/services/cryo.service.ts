import { Injectable } from '@angular/core';
import { HttpClient } from 'selenium-webdriver/http';
import { NGXLogger } from 'ngx-logger';
import { CryoState } from '../model/cryoState';
import { Observable } from 'rxjs';
import { StompService } from '@stomp/ng2-stompjs';
import { Message } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root'
})
export class CryoService {

  private cryoStateObservable: Observable<CryoState>;

  constructor(private socket: StompService, private log: NGXLogger) { 

    this.cryoStateObservable = socket.subscribe('/topic/cryo').map((message: Message) => <CryoState> JSON.parse(message.body));
  }

  public subscribe(next: (state: CryoState)=> void) {
    this.log.debug('New subscription to cryo state.');
    this.cryoStateObservable.subscribe(next);
  }
}
