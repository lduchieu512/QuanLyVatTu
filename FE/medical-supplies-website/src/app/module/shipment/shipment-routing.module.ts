import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ShipmentListComponent} from './component/shipment-list/shipment-list.component';
import {ShipmentCreateComponent} from './component/shipment-create/shipment-create.component';
import {ReturnCanceComponent} from './component/return-cance/return-cance.component';
import {ReturnCanceCreateComponent} from './component/return-cance-create/return-cance-create.component';
import {AuthGuard} from "../../auth.guard";
import {RoleGuard} from "../../role.guard";



const routes: Routes = [
  {path: 'shipment',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_SALE']},
    component: ShipmentListComponent},
  {path: 'shipment/create',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_SALE']},
    component: ShipmentCreateComponent},
  {path: 'return',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_ACCOUNTANT']},
    component: ReturnCanceComponent},
  {path: 'return/create',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_ACCOUNTANT']},
    component: ReturnCanceCreateComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShipmentRoutingModule { }
