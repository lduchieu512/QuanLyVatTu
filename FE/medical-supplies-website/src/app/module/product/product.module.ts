import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ProductRoutingModule} from './product-routing.module';
import {ProductDetailComponent} from './component/product-detail/product-detail.component';
import {ReactiveFormsModule} from '@angular/forms';
import {ProductEditComponent} from './component/product-edit/product-edit.component';
import {ProductCreateEditComponent} from './component/product-create-edit/product-create-edit.component';
import {FileValidator} from './utils/CustomerValidator';


@NgModule({
  declarations: [
    ProductDetailComponent,
    ProductCreateEditComponent,
    ProductEditComponent,
    FileValidator
  ],
    imports: [
        CommonModule,
        ProductRoutingModule,
        ReactiveFormsModule
    ]
})
export class ProductModule {
}
