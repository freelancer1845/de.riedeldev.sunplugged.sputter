import { Component } from '@angular/core';

import {TabMenuModule} from 'primeng/tabmenu';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  items: MenuItem[];

    ngOnInit() {
        this.items = [
            {label: 'Status', icon: 'fa-bar-chart', routerLink: ['statusview']},
            {label: 'Modbus Debug', icon: 'fa-calendar', routerLink: ['modbusdebug']},
            {label: 'Evara Debug', icon: 'fa-caret-square-right', routerLink: ['evaradebug']},
            {label: 'Vacuum View', icon: 'fa-support', routerLink: ['vacuumview']},
            {label: 'Social', icon: 'fa-twitter'}
        ];
    }
}
