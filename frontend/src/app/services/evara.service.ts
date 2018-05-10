import { Injectable } from '@angular/core';
import { StompService } from '@stomp/ng2-stompjs';
import { NGXLogger } from 'ngx-logger';
import { Observable, Observer } from 'rxjs';
import { Message } from 'stompjs';
import { EvaraState, EvaraMessage } from '../model/evaraState';
import { StandardStates } from '../model/standardStates';

@Injectable({
  providedIn: 'root'
})
export class EvaraService {

  private TOPIC_EXECUTE_ANSWER = '/topic/evara/execute/answer';

  private TOPIC_STATE = '/topic/evara';

  private answerObservable: Observable<string>;

  private stateObservable: Observable<EvaraState>;

  constructor(private socket: StompService, private log: NGXLogger) {
    this.answerObservable = socket.subscribe(this.TOPIC_EXECUTE_ANSWER).map((message: Message) => {
      log.debug("Recivied Evara execute command answer: " + message.body);
      return message.body;
    });

    this.stateObservable = socket.subscribe(this.TOPIC_STATE).map((message: Message) => <EvaraState> JSON.parse(message.body));
   }


  subscribeToCommandAnswer(next: (value: string) => void) {
    this.answerObservable.subscribe(next);
  }

  subscribeToState(next: (value: EvaraState) => void) {
    this.stateObservable.subscribe(next);


  }

  issueCommand(cmd: string){
    this.log.debug('Sending command to evara: ' + cmd);
    this.socket.publish('/app/evara/execute', 'Hello World');
  }


}
