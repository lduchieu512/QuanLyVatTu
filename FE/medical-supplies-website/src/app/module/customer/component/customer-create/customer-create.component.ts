import {Component, OnInit} from '@angular/core';
import {CustomerTypeService} from '../../service/customer-type.service';
import {CustomerService} from '../../service/customer.service';
import {Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomerType} from '../../model/CustomerType';
import {formatDate} from '@angular/common';
import {AngularFireStorage} from '@angular/fire/storage';
import {finalize} from 'rxjs/operators';
import Swal from 'sweetalert2';
import {Customer} from '../../model/Customer';

@Component({
  selector: 'app-customer-create',
  templateUrl: './customer-create.component.html',
  styleUrls: ['./customer-create.component.css']
})
export class CustomerCreateComponent implements OnInit {
  customerFormCreate: FormGroup;
  customerTypes: CustomerType [] = [];
  customer: Customer [] = [];
  inputImage: any;
  imgSrc: any = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfqGSpRSWM2LH7fa_Vvrr4V0IGlvG_QWXpJofT1-E&s';
  // imgSrc: any = 'https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png';
  maxSize: any;

  constructor(private customerTypeService: CustomerTypeService,
              private customerService: CustomerService,
              private storage: AngularFireStorage,
              private router: Router) {
  }

  ngOnInit(): void {
    this.customerTypeService.getAllCustomerType().subscribe((data) => {
      this.customerTypes = data;
    });
    this.customerFormCreate = new FormGroup({
      customerId: new FormControl(),
      customerCode : new FormControl('', Validators.required),
      name: new FormControl('', [Validators.required, Validators.maxLength(50),
        Validators.minLength(3), Validators.pattern('^(?:[A-Z][a-zÀ-ỹ]*(?: [A-Z][a-zÀ-ỹ]*)+)$')]),
      phone: new FormControl('', [Validators.required,
        Validators.pattern('^(086|096|097|098|038|037|036|035|034|033|032|091|094|088|081|082|083|084|085|070|076|077|078|079|089|090|093|092|052|056|058|099|059|087)\\d{7}$')]),
      gender: new FormControl('', [Validators.required]),
      dateOfBirth: new FormControl('', Validators.required),
      // idCard: new FormControl('', [Validators.required, Validators.pattern('^\\d{12}$')]),
      idCard: new FormControl('', [Validators.required,
        Validators.pattern('^(001|002|004|006|008|010|011|012|014|015|017|019|020|022|024|025|026|027|030|031|033|034|035|036|037|038|040|042|044|045|046|048|049|051|052|054|056|058|060|062|064|066|067|068|070|072|074|075|077|079|080|082|083|084|086|087|089|091|092|093|094|095|096)([0-9])(00|0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]|6[0-9]|7[0-9]|8[0-9]|9[0-9])([0-9]{6})$')]),
      // email: new FormControl('', [Validators.required, Validators.minLength(6),
      //   Validators.maxLength(30),  Validators.pattern('^[a-zA-Z0-9_.+-]+@gmail.com+$')]),
      email: new FormControl('', [Validators.required, Validators.minLength(6),
        Validators.maxLength(30),  Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')]),
      customerAddress: new FormControl('', [Validators.required, Validators.minLength(10),
        Validators.maxLength(100), Validators.pattern('^[^!@#$%^&*()_+<>?\'\"{}\\`~|/\\\\]+$')]),
      customerImg: new FormControl('', Validators.required),
      customerType: new FormControl('', Validators.required),
      account: new FormControl(),
      cart: new FormControl(),
      enable: new FormControl(),
    });
    console.log(this.customerFormCreate.value);
  }

  createCustomer() {
    const nameImg = formatDate(new Date(), 'dd-MM-yyyy_hh:mm:ss:a_', 'en-US') + this.inputImage.name;
    const fileRef = this.storage.ref(nameImg);
    this.storage.upload(nameImg, this.inputImage).snapshotChanges().pipe(
      finalize(() => {
        fileRef.getDownloadURL().subscribe(url => {
          this.customerFormCreate.value.customerImg = url;
          this.customerService.createCustomer(this.customerFormCreate.value).subscribe(next => {
              this.router.navigateByUrl('customers').then(() => {
                Swal.fire('Thành công',
                  'Đã thêm khách hàng thành công',
                  'success');
              });
            },
            (error) => {
              Swal.fire('Lỗi',
                'Không thêm khách hàng thành công',
                'error');
              console.log(error);
            },
            () => {
            }
          );
        });
      })
    ).subscribe(
      () => {
      },
      (error) => {
        Swal.fire('Lỗi',
          'Không thêm khách hàng thành công',
          'error');
        console.log(error);
      },
      () => {
      }
    );
    console.log(this.customerFormCreate.value);

  }

  selectImg(event: any) {
    this.inputImage = event.target.files[0];
    if (this.inputImage.size > 1048576 && this.inputImage != null) {
      this.maxSize = true;
      event.target.value = null;
      this.inputImage = null;
    } else if (this.inputImage) {
      this.maxSize = false;
      const reader = new FileReader();
      reader.readAsDataURL(this.inputImage);
      reader.onload = (e: any) => {
        this.imgSrc = e.target.result;
      };
    }
  }


}
