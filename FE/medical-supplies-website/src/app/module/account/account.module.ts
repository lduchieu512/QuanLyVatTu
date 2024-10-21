import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountRoutingModule } from './account-routing.module';
import { AddAccountComponent } from './component/add-account/add-account.component';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [AddAccountComponent],
  imports: [
    CommonModule,
    AccountRoutingModule,
    ReactiveFormsModule,
  ]
})
export class AccountModule { }
