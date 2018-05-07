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
            {label: 'Documentation', icon: 'fa-book'},
            {label: 'Support', icon: 'fa-support'},
            {label: 'Social', icon: 'fa-twitter'}
        ];
    }
}
