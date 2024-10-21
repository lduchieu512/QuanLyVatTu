import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CustomerCreateComponent} from './component/customer-create/customer-create.component';
import {CustomerEditComponent} from './component/customer-edit/customer-edit.component';
import {CustomerUserDetailComponent} from './component/customer-user-detail/customer-user-detail.component';
import {CustomerListComponent} from './component/customer-list/customer-list.component';
import {AuthGuard} from '../../auth.guard';
import {RoleGuard} from '../../role.guard';


const routes: Routes = [
  {
    path: 'create',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_ACCOUNTANT']},
    component: CustomerCreateComponent
  },
  {
    path: 'edit/:id',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_ACCOUNTANT']},
    component: CustomerEditComponent
  },
  {
    path: 'detail',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_USER']},
    component: CustomerUserDetailComponent
  },
  {
    path: '',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_ACCOUNTANT']},
    component: CustomerListComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule {
}
