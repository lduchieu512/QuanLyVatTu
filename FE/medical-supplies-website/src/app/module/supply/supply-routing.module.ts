import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SupplyListComponent} from './components/supply-list/supply-list.component';
import {AuthGuard} from "../../auth.guard";
import {RoleGuard} from "../../role.guard";


const routes: Routes = [{
  path: '',
  canActivate: [AuthGuard, RoleGuard],
  data: {roles: ['ROLE_ADMIN']},
  component: SupplyListComponent
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupplyRoutingModule { }
