import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EmployeeRoutingModule} from './employee-routing.module';
import {ListEmployeeComponent} from './component/list-employee/list-employee.component';
import {HttpClientModule} from '@angular/common/http';
import {EmployeeUserDetailComponent} from './component/employee-user-detail/employee-user-detail.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CreateComponent} from './component/create/create.component';
import {EditComponent} from './component/edit/edit.component';
import {ModalChangePasswordComponent} from './component/modal-change-password/modal-change-password.component';
import {EmployeeUserUpdateComponent} from './component/employee-user-update/employee-user-update.component';


@NgModule({
  declarations: [
    EmployeeUserDetailComponent,
    ListEmployeeComponent,
    CreateComponent,
    EditComponent,
    ModalChangePasswordComponent,
    EmployeeUserUpdateComponent
  ],
  exports: [],
  imports: [
    CommonModule,
    EmployeeRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
})

export class EmployeeModule {
}
