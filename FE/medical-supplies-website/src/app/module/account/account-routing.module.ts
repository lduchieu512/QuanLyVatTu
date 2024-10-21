import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AddAccountComponent} from './component/add-account/add-account.component';
import {AuthGuard} from '../../auth.guard';
import {RoleGuard} from '../../role.guard';


const routes: Routes = [
  {path: 'create',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN']},
    component: AddAccountComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountRoutingModule { }
