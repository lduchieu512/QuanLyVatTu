import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {finalize} from 'rxjs/operators';
import {ProductInfo} from '../../model/ProductInfo';
import {ProductCreateFormDTO} from '../../model/ProductCreateFormDTO';
import {Category} from '../../model/Category';
import {Customer} from '../../../customer/model/Customer';
import {CustomerService} from '../../../customer/service/customer.service';
import {ProductService} from '../../service/product.service';
import {AngularFireStorage} from '@angular/fire/storage';
import {Router} from '@angular/router';
import {CategoryService} from '../../service/category.service';
import {ProductInfoService} from '../../service/product-info.service';
import {checkFile} from '../../utils/CustomerValidator';
import {ToastrService} from 'ngx-toastr';
import Swal from "sweetalert2";

@Component({
  selector: 'app-product-create-edit',
  templateUrl: './product-create-edit.component.html',
  styleUrls: ['./product-create-edit.component.css']
})
export class ProductCreateEditComponent implements OnInit {
  constructor(private categoryService: CategoryService,
              private customerService: CustomerService,
              private productService: ProductService,
              private productInfoService: ProductInfoService,
              private fireStorage: AngularFireStorage,
              private toast: ToastrService,
              private formBuilder: FormBuilder,
              private router: Router
  ) {
    this.categoryService.getAll().subscribe(data => {
      this.category = data;
    });

    this.customerService.getAllSuppliers().subscribe(data => {
      console.log(data)
      this.customer = data;
    });

    this.productInfoService.getAll().subscribe(data => {
      this.productInfo = data;
    });
  }

  formGroup: FormGroup;
  product: ProductCreateFormDTO[] = [];
  category: Category[] = [];
  customer: Customer[] = [];
  productInfo: ProductInfo[] = [];
  uploadedAvatar: any = null;
  loading = false;
  downloadURL: string;
  errorMessage: string;
  oldAvatarLink = 'https://cdn.pixabay.com/photo/2020/03/17/20/52/covid-4941846_640.png';
  formArrayImg: FormArray;

  price: number ;

  ngOnInit(): void {
    this.buildForm();
  }


  buildForm() {
    this.formGroup = new FormGroup({
      productCode: new FormControl('', [Validators.minLength(3)]),
      productName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(45)]),
      productPrice: new FormControl('', [Validators.required, Validators.min(1), Validators.max(1000000000)]),
      productQuantity: new FormControl('', [Validators.required, Validators.min(1), Validators.max(10000)]),
      productImg: new FormControl('', [Validators.required, checkFile]),
      expireDate: new FormControl('', [Validators.required]),
      customer: new FormControl('', [Validators.required]),
      category: new FormControl('', [Validators.required]),
      productInfo: new FormControl('1', [Validators.required])
    });
  }

  reset() {
    this.formGroup.reset();
    this.oldAvatarLink;
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
    if (this.uploadedAvatar !== null && this.formGroup.valid) {
      const avatarName = this.getCurrentDateTime() + this.uploadedAvatar.name;
      const fileRef = this.fireStorage.ref(avatarName);
      this.fireStorage.upload(avatarName, this.uploadedAvatar).snapshotChanges().pipe(
        finalize(() => {
          fileRef.getDownloadURL().subscribe(url => {
            console.log('url', url);
            this.formGroup.value.productImg = url;
            console.log(this.formGroup);
            this.productService.saveProduct(this.formGroup.value).subscribe(
              (resp) => {
                this.router.navigateByUrl('/supplies').then(() => {
                  Swal.fire({
                    icon: 'success',
                    title: 'THÔNG BÁO',
                    text: 'Thêm mới vật tư thành công!',
                    showConfirmButton: false,
                    timer: 3000
                  });
                  this.reset();
                });
              }
              ,
              (error) => {
                console.log(error);
                console.log(typeof error.error.productName);
                console.log(typeof error.error.name);
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
                  })}
              }
            );
          });
        })
      ).subscribe();
    } else {
      console.log('OK');
      this.formGroup.value.productImg = '';
      this.productService.saveProduct(this.formGroup.value).subscribe(
        () => {
          console.log('successful:');
          this.toast.success('Thêm mới vật tư thành công!', 'Success:');
          this.router.navigateByUrl('/supplies');
          this.reset();
        }, error => {
          console.log(error.error)
          Swal.fire({
            title: "Lỗi",
            icon : "error",
            text : "Vui lòng nhập dữ liệu đầy đủ",
            showConfirmButton: false,
            timer: 1000
          })
        }
      );
    }
  }

  private getCurrentDateTime() {
    return new Date().getTime();
  }

}
