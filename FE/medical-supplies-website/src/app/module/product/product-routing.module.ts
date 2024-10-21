import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ProductDetailComponent} from './component/product-detail/product-detail.component';
import {ProductCreateEditComponent} from './component/product-create-edit/product-create-edit.component';
import {ProductEditComponent} from './component/product-edit/product-edit.component';
import {AuthGuard} from '../../auth.guard';
import {RoleGuard} from '../../role.guard';


const routes: Routes = [
  {path: 'detail/:id', component: ProductDetailComponent},
  {
    path: '',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN']},
    component: ProductCreateEditComponent
  },
  {
    path: 'update/:id',
    canActivate: [AuthGuard, RoleGuard],
    data: {roles: ['ROLE_ADMIN']},
    component: ProductEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule {
}
