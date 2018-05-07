import { Injectable } from '@angular/core';
import { FooterErrorMessage } from '../model/footErrorMessage';
import { FooterComponent } from './footer.component';

@Injectable()
export class FooterErrorService {


  footer: FooterComponent;
  errors: FooterErrorMessage[] = [];


  constructor() { }

  submitError(error: FooterErrorMessage) {
    if (error.time == undefined) {
      error.time = new Date();
    } 
    let possibleDuplicate = this.errors.find(obj => obj.text == error.text);
    if (possibleDuplicate != undefined) {
      possibleDuplicate.time = error.time;
    } else {
      this.errors.push(error);
    }
    this.notifyFooterComponent();
  }

  getErrors(): FooterErrorMessage[] {
    return this.errors;
  }

  clearError(error: FooterErrorMessage) {
    this.errors = this.errors.filter(obj => obj.text != error.text);
    this.notifyFooterComponent();
  }

  registerFooter(footer: FooterComponent) {
    this.footer = footer;
  }

  notifyFooterComponent() {
    if (this.footer != undefined) {
      this.footer.notify();
    }
  }

}
