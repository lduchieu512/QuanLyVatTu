import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ReceiptListComponent} from './component/receipt-list/receipt-list.component';
import {AuthGuard} from "../../auth.guard";
import {RoleGuard} from "../../role.guard";


const routes: Routes = [
  {path: '',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_ACCOUNTANT']},
    component: ReceiptListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReceiptRoutingModule { }
