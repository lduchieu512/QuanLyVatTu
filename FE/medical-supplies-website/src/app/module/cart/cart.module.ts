import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CartRoutingModule } from './cart-routing.module';
import { CartListComponent } from './component/cart-list/cart-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { PaymentSuccessComponent } from './component/payment-success/payment-success.component';

@NgModule({
  declarations: [CartListComponent, PaymentSuccessComponent],
  imports: [
    CommonModule,
    CartRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class CartModule { }
