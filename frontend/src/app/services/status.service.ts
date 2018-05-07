import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { FooterErrorService } from '../footer/footer-error.service';
import { FooterErrorMessage } from '../model/footErrorMessage';
import { NGXLogger } from 'ngx-logger';
import { Observable, Observer, Subscription } from 'rxjs';
import { StompService, StompState } from '@stomp/ng2-stompjs';

@Injectable({
  providedIn: 'root'
})
export class StatusService {

  httpErrorMessage: FooterErrorMessage;
  socketErrorMessage: FooterErrorMessage;

  private httpStatusObservable: Observable<boolean>;

  api_url: string = environment.server_api + 'status';

  constructor(private http: HttpClient,
    private footerErrorService: FooterErrorService,
    private log: NGXLogger,
    private stomp: StompService
  ) {
    //this.checkMachine();
    this.addWebsocketSubscription();

    this.httpStatusObservable =  Observable.create((obs: Observer<boolean>) => {
      this.reachesMachine().subscribe(() => obs.next(true), (error) => obs.next(false));
      setInterval(() => {
       this.reachesMachine().subscribe(()=> obs.next(true), (error) => obs.next(false));
      }, 5000)
    })
  }



  private addWebsocketSubscription() {



    this.stomp.state.subscribe((state: StompState) => {
      if (state != StompState.CONNECTED) {
        this.socketErrorMessage = new FooterErrorMessage();
        this.socketErrorMessage.text = 'Failed to connect to Websocket. Automatic UI updates will not work!';
        this.footerErrorService.submitError(this.socketErrorMessage);
      }
      else {
        this.footerErrorService.clearError(this.socketErrorMessage);
      }
    });
  }

  subscribeToStatusChecker(func: (state: boolean) => any): Subscription {
    return this.httpStatusObservable.subscribe(func);
  }


  reachesMachine(): Observable<Boolean> {
    return this.http.get<Boolean>(this.api_url);
  }
}
