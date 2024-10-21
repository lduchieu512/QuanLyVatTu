import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerCreateComponent } from './component/customer-create/customer-create.component';
import { CustomerEditComponent } from './component/customer-edit/customer-edit.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { CustomerUserDetailComponent } from './component/customer-user-detail/customer-user-detail.component';
import { CustomerListComponent } from './component/customer-list/customer-list.component';


@NgModule({
  declarations: [
    CustomerCreateComponent,
    CustomerEditComponent,
    CustomerUserDetailComponent,
    CustomerListComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    ReactiveFormsModule,
    FormsModule,
  ]
})
export class CustomerModule { }
