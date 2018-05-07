import { Component, OnInit, OnDestroy } from '@angular/core';
import { FooterErrorMessage } from '../model/footErrorMessage';
import { NGXLogger } from 'ngx-logger';
import { FooterErrorService } from './footer-error.service';
import { StatusService } from '../services/status.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit, OnDestroy {



  messages: FooterErrorMessage[] = [];

  httpErrorMessage: FooterErrorMessage;
  httpSubscripton: Subscription;

  constructor(
    private logger: NGXLogger,
    private footerErrorService: FooterErrorService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.footerErrorService.registerFooter(this);
    this.loadMessages();
    this.httpSubscripton = this.statusService.subscribeToStatusChecker(status=> {
      if (!status) {
        this.httpErrorMessage = new FooterErrorMessage();
        this.httpErrorMessage.text = 'Connection to machine lost!';
        this.footerErrorService.submitError(this.httpErrorMessage);
      } else{
        if (this.httpErrorMessage != undefined) {
          this.footerErrorService.clearError(this.httpErrorMessage);
          this.httpErrorMessage = undefined;
        }
    
      }
    });
  }

  ngOnDestroy() {
    this.httpSubscripton.unsubscribe();
  }

  private loadMessages() {
    this.messages = this.footerErrorService.getErrors();
  }

  acknowledgeMessage(message: FooterErrorMessage) {
    this.logger.debug('Message Acknowledged by user.');
    this.footerErrorService.clearError(message);
  }

  notify(): any {
    this.loadMessages();
  }

}
