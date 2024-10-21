import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ShipmentRoutingModule } from './shipment-routing.module';
import { ShipmentListComponent } from './component/shipment-list/shipment-list.component';
import { ShipmentCreateComponent } from './component/shipment-create/shipment-create.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { ReturnCanceComponent } from './component/return-cance/return-cance.component';
import { ReturnCanceCreateComponent } from './component/return-cance-create/return-cance-create.component';
import {NgxPaginationModule} from 'ngx-pagination';




@NgModule({
  imports: [
    CommonModule,
    ShipmentRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    NgxPaginationModule
  ],
  declarations: [ShipmentListComponent, ShipmentCreateComponent, ReturnCanceComponent, ReturnCanceCreateComponent]
})
export class ShipmentModule { }
