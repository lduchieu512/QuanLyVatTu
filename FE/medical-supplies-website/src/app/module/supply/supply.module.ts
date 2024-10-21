import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SupplyRoutingModule } from './supply-routing.module';
import { SupplyListComponent } from './components/supply-list/supply-list.component';
import { SupplySearchComponent } from './components/supply-search/supply-search.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [SupplyListComponent, SupplySearchComponent],
  imports: [
    CommonModule,
    SupplyRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ]
})
export class SupplyModule { }
