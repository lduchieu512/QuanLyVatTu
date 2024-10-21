import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReceiptRoutingModule } from './receipt-routing.module';
import { ReceiptListComponent } from './component/receipt-list/receipt-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [ReceiptListComponent, ReceiptListComponent],
    imports: [
        CommonModule,
        ReceiptRoutingModule,
        FormsModule,
        ReactiveFormsModule
    ]
})
export class ReceiptModule { }
