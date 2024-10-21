import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

const routes: Routes = [
  {path: '', loadChildren: () => import('./module/home/home.module').then(module => module.HomeModule)},
  {path: 'products', loadChildren: () => import('./module/product/product.module').then(module => module.ProductModule)},
  {path: 'customers', loadChildren: () => import('./module/customer/customer.module').then(module => module.CustomerModule)},
  {path: 'carts', loadChildren: () => import('./module/cart/cart.module').then(module => module.CartModule)},
  {path: 'employees', loadChildren: () => import('./module/employee/employee.module').then(module => module.EmployeeModule)},
  {path: 'accounts', loadChildren: () => import('./module/account/account.module').then(module => module.AccountModule)},
  {path: 'shipments',
    loadChildren: () => import('./module/shipment/shipment.module').then(module => module.ShipmentModule)},
  {path: 'receipts', loadChildren: () => import('./module/receipt/receipt.module').then(module => module.ReceiptModule)},
  {path: 'login', loadChildren: () => import('./module/security/security.module').then(module => module.SecurityModule)},
  {path: 'supplies', loadChildren: () => import('./module/supply/supply.module').then(module => module.SupplyModule)},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    scrollPositionRestoration: 'top'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
