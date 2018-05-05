import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';


import {TabMenuModule} from 'primeng/tabmenu';
import {MenuItem} from 'primeng/api';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import {InplaceModule} from 'primeng/inplace';
import {InputTextModule} from 'primeng/inputtext';
import {AccordionModule} from 'primeng/accordion';

import { AppComponent } from './app.component';
import { StatusViewComponent } from './status-view/status-view.component';
import { SocketServiceService } from './services/socket-service.service';
import { ModbusDebugComponent } from './modbus-debug/modbus-debug.component';
import { ModbusApiService } from './services/modbus-api.service';
import { HttpClientModule } from '@angular/common/http';


const appRoutes: Routes = [
    { path: 'statusview', component: StatusViewComponent},
    { path: 'modbusdebug', component: ModbusDebugComponent}
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

@NgModule({
  declarations: [
    AppComponent,
    StatusViewComponent,
    ModbusDebugComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
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
  providers: [SocketServiceService, ModbusApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
