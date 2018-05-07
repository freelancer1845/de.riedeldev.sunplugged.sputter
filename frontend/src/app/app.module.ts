import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';

import { TabMenuModule } from 'primeng/tabmenu';
import { MenuItem } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InplaceModule } from 'primeng/inplace';
import { InputTextModule } from 'primeng/inputtext';
import { AccordionModule } from 'primeng/accordion';


import { AppComponent } from './app.component';
import { StatusViewComponent } from './status-view/status-view.component';
import { ModbusDebugComponent } from './modbus-debug/modbus-debug.component';
import { ModbusApiService } from './services/modbus-api.service';
import { HttpClientModule } from '@angular/common/http';
import { FooterComponent } from './footer/footer.component';
import { FooterErrorService } from './footer/footer-error.service';
import { StatusService } from './services/status.service';
import { environment } from '../environments/environment.prod';
import { StompService, StompConfig } from '@stomp/ng2-stompjs';


const appRoutes: Routes = [
  { path: 'statusview', component: StatusViewComponent },
  { path: 'modbusdebug', component: ModbusDebugComponent }
  // { path: 'hero/:id',      component: HeroDetailComponent },
  // {
  //   path: 'heroes',
  //   component: HeroListComponent,
  //   data: { title: 'Heroes List' }
  // },
  // { path: '',
  //   redirectTo: '/heroes',
  //   pathMatch: 'full'
  // },
  // { path: '**', component: PageNotFoundComponent }
];


const stompConfig: StompConfig = {
  // Which server?
  url: environment.server_ws,

  // Headers
  // Typical keys: login, passcode, host
  headers: {
    login: '',
    passcode: ''
  },

  // How often to heartbeat?
  // Interval in milliseconds, set to 0 to disable
  heartbeat_in: 0, // Typical value 0 - disabled
  heartbeat_out: 20000, // Typical value 20000 - every 20 seconds
  // Wait in milliseconds before attempting auto reconnect
  // Set to 0 to disable
  // Typical value 5000 (5 seconds)
  reconnect_delay: 5000,

  // Will log diagnostics on console
  debug: false
};



@NgModule({
  declarations: [
    AppComponent,
    StatusViewComponent,
    ModbusDebugComponent,
    FooterComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false } // <-- debugging purposes only
    ),
    LoggerModule.forRoot({ level: NgxLoggerLevel.DEBUG }),
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    TabMenuModule,
    TableModule,
    ButtonModule,
    InplaceModule,
    InputTextModule,
    AccordionModule,
  ],
  providers: [
    ModbusApiService,
    FooterErrorService,
    StatusService,
    StompService,
    {
      provide: StompConfig,
      useValue: stompConfig
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
