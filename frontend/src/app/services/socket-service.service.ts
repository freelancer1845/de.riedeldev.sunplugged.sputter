import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import * as Stomp from 'stompjs';
import { GlobalState } from '../model/globalState';
import * as webstompobs from 'webstomp-obs';

@Injectable()
export class SocketServiceService {

  private socketUrl = 'ws://localhost:8080/socket';
  private client: Stomp.Client;

  constructor() {
    this.createClient();
  }

  private createClient() {
    this.client = Stomp.client(this.socketUrl);
    this.client.debug = undefined;
    this.client.connect("","",(connectFrame) => {}, (errorFrame) => console.log(errorFrame));
  }

  public getObservable<T>(subject: string): Observable<T> {
    if (this.client == undefined) {
      this.createClient();
    }
    let that = this;
    return Observable.create(function (observer) {
      console.log('subscribing to: ' + subject);
      that.client.subscribe(subject, function (message) {
        observer.next(message);
        return function () {
          that.client.disconnect(function () {
            console.log('Disconnected.');
          });
        };
      }, function (error) {
        observer.error(new Error(error));
      })
    })
      .map((msg) => <T>JSON.parse(msg.body));
  }




}
