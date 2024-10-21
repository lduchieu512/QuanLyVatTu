import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ProductCreateFormDTO} from '../../model/ProductCreateFormDTO';
import {Category} from '../../model/Category';
import {Customer} from '../../../customer/model/Customer';
import {ProductInfo} from '../../model/ProductInfo';
import {CustomerService} from '../../../customer/service/customer.service';
import {ProductService} from '../../service/product.service';
import {AngularFireStorage} from '@angular/fire/storage';
import {ActivatedRoute, Router} from '@angular/router';
import {finalize} from 'rxjs/operators';
import {CategoryService} from '../../service/category.service';
import {ProductInfoService} from '../../service/product-info.service';
import {FileValidator} from '../../utils/CustomerValidator';
import {ToastrService} from 'ngx-toastr';
import Swal from "sweetalert2";

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {

  formGroup: FormGroup = new FormGroup({
    productCode: new FormControl(),
    productName: new FormControl(),
    productPrice: new FormControl(),
    productQuantity: new FormControl(),
    productImg: new FormControl(),
    expireDate: new FormControl(),
    customer: new FormControl(),
    category: new FormControl(),
    productInfo: new FormControl()
  });
  product: ProductCreateFormDTO;
  category: Category[] = [];
  customer: Customer[] = [];
  productInfo: ProductInfo[] = [];
  uploadedAvatar: File = null;
  id: string;
  loading = false;
  downloadURL: string;
  errorMessage: string;
  oldAvatarLink = 'https://cdn.pixabay.com/photo/2020/03/17/20/52/covid-4941846_640.png';

  price: number ;

  constructor( private categoryService: CategoryService,
               private customerService: CustomerService,
               private productService: ProductService,
               private productInfoService: ProductInfoService,
               private fireStorage: AngularFireStorage,
               private toast: ToastrService,
               private formBuilder: FormBuilder,
               private router: Router,
               private activatedRoute: ActivatedRoute

  ) {
    this.categoryService.getAll().subscribe(data => {
      this.category = data;
    });
    this.customerService.getAllSuppliers().subscribe( data => {
      this.customer = data;
    });
    this.productInfoService.getAll().subscribe( data => {
      this.productInfo = data;
    });
    this.activatedRoute.paramMap.subscribe(next => {
      this.id = next.get('id');
      console.log('id:', this.id);
    });
  }

  ngOnInit(): void {
    this.productService.findById(this.id.trim()).subscribe(data => {
      this.price = data.productPrice
      console.log(data);
      this.product = data;
      this.oldAvatarLink = data.productImg;
      console.log(data.productImg);
      this.formGroup =  new FormGroup({
        productId: new FormControl(data.productId),
        productCode: new FormControl( data.productCode, [Validators.required, Validators.minLength(3)]),
        productName: new FormControl(data.productName, [Validators.required, Validators.minLength(3), Validators.maxLength(45)]),
        productPrice: new FormControl(data.productPrice, [Validators.required, Validators.min(1), Validators.max(1000000000)]),
        productQuantity: new FormControl(data.productQuantity, [Validators.required, Validators.min(1), Validators.max(10000)]),
        productImg: new FormControl((data.productImg), [Validators.required, FileValidator.validate]),
        expireDate: new FormControl( (data.expireDate), [Validators.required]),
        customer: new FormControl(data.customer, [Validators.required]),
        category: new FormControl(data.category, [Validators.required]),
        productInfo: new FormControl(data.productInfo, [Validators.required])
      });
      console.log(this.formGroup);
    });

  }

  reset() {
    this.formGroup.reset();
    // this.oldAvatarLink;
  }

  changeFile(event: any) {
    this.uploadedAvatar = event.target.files[0];
    if (this.uploadedAvatar) {
      const reader = new FileReader();
      reader.readAsDataURL(this.uploadedAvatar);
      reader.onload = (e: any) => {
        this.oldAvatarLink = e.target.result;
      };
    }
    console.log('file0', this.uploadedAvatar);
  }

  submitProduct() {
    // Upload img & download url
    console.log('bắt đầu');
    console.log(this.formGroup);
    if (this.uploadedAvatar !== null && this.formGroup.valid) {
      const avatarName = this.getCurrentDateTime() + this.uploadedAvatar.name;
      const fileRef = this.fireStorage.ref(avatarName);
      this.fireStorage.upload(avatarName, this.uploadedAvatar).snapshotChanges().pipe(
        finalize(() => {
          fileRef.getDownloadURL().subscribe(url => {
            this.formGroup.value.productImg = url;
            console.log('url', url);
            this.productService.updateProductId(this.formGroup.value).subscribe(() => {
              console.log('bắt');
              this.router.navigateByUrl('/supplies').then(() => {
                Swal.fire({
                  icon: 'success',
                  title: 'THÔNG BÁO',
                  text: 'chỉnh sửa vật tư thành công!',
                  showConfirmButton: false,
                  timer: 3000
                });
                this.reset();
              });
              console.log('hêt');
            }, error => {
              console.log(error);
              console.log(error.error);
              if (error.error.productName !== undefined) {
                Swal.fire({
                  title: "Lỗi",
                  icon : "error",
                  text : error.error.productName,
                  showConfirmButton: false,
                  timer: 1000
                })
              } else if (error.error.name !== undefined) {
                Swal.fire({
                  title: "Lỗi",
                  icon : "error",
                  text : error.error.name,
                  showConfirmButton: false,
                  timer: 1000
                })
              }
            });
          });
        })
      ).subscribe();
    } else {
      console.log('OK');
      this.formGroup.value.productImg = this.oldAvatarLink;
      this.productService.updateProductId(this.formGroup.value).subscribe(
        () => {
          this.router.navigateByUrl('/supplies').then(() => {
            Swal.fire({
              icon: 'success',
              title: 'THÔNG BÁO',
              text: 'chỉnh sửa vật tư thành công!',
              showConfirmButton: false,
              timer: 3000
            });
            this.reset();
          });
        }, error => {
          console.log(error);
          console.log(error.error.expireDate);
          if (error.error.productName !== undefined) {
            Swal.fire({
              title: "Lỗi",
              icon : "error",
              text : error.error.productName,
              showConfirmButton: false,
              timer: 1000
            })
          } else if (error.error.name !== undefined) {
            Swal.fire({
              title: "Lỗi",
              icon : "error",
              text : error.error.name,
              showConfirmButton: false,
              timer: 1000
            })
          }
        }
      );
    }
  }

  private getCurrentDateTime() {
    return new Date().getTime();
  }

}
