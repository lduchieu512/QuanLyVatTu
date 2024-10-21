import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomerType} from '../../model/CustomerType';
import {CustomerTypeService} from '../../service/customer-type.service';
import {CustomerService} from '../../service/customer.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {finalize} from 'rxjs/operators';
import {formatDate} from '@angular/common';
import {AngularFireStorage} from '@angular/fire/storage';
import Swal from 'sweetalert2';
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-customer-edit',
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.css']
})
export class CustomerEditComponent implements OnInit {
  customerFormEdit: FormGroup;
  id: string;
  customerTypes: CustomerType [] = [];
  inputImage: any = null;
  maxSize = false;
  imgSrc: string;


  constructor(private customerTypeService: CustomerTypeService,
              private customerService: CustomerService,
              private activatedRoute: ActivatedRoute,
              private storage: AngularFireStorage,
              private router: Router) {
  }

  ngOnInit(): void {
    this.customerTypeService.getAllCustomerType().subscribe((data) => {
      this.customerTypes = data;
    });
    this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {
      this.id = paramMap.get('id');
      console.log(this.id);
      this.customerService.findByIdCustomer(this.id).subscribe((customerEdit) => {
        console.log(customerEdit);
        this.imgSrc = customerEdit.customerImg;
        this.customerFormEdit = new FormGroup({
          customerId: new FormControl(customerEdit.customerId),
          customerCode: new FormControl(customerEdit.customerCode, Validators.required),
          name: new FormControl(customerEdit.name, [Validators.required, Validators.maxLength(50),
            Validators.minLength(3), Validators.pattern('^(?:[A-Z][a-zÀ-ỹ]*(?: [A-Z][a-zÀ-ỹ]*)+)$')]),
          // phone: new FormControl(customerEdit.phone, [Validators.required, Validators.pattern('^(09|08)\\d{8}$')]),
          phone: new FormControl(customerEdit.phone, [Validators.required,
            Validators.pattern('^(086|096|097|098|038|037|036|035|034|033|032|091|094|088|081|082|083|084|085|070|076|077|078|079|089|090|093|092|052|056|058|099|059|087)\\d{7}$')]),
          gender: new FormControl(customerEdit.gender, [Validators.required]),
          dateOfBirth: new FormControl(customerEdit.dateOfBirth, Validators.required),
          idCard: new FormControl(customerEdit.idCard, [Validators.required,
            Validators.pattern('^(001|002|004|006|008|010|011|012|014|015|017|019|020|022|024|025|026|027|030|031|033|034|035|036|037|038|040|042|044|045|046|048|049|051|052|054|056|058|060|062|064|066|067|068|070|072|074|075|077|079|080|082|083|084|086|087|089|091|092|093|094|095|096)([0-9])(00|0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]|6[0-9]|7[0-9]|8[0-9]|9[0-9])([0-9]{6})$')]),
          email: new FormControl(customerEdit.email, [Validators.required, Validators.minLength(6),
            Validators.maxLength(30), Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')]),
          customerAddress: new FormControl(customerEdit.customerAddress,
            [Validators.required, Validators.maxLength(100), Validators.minLength(10)
              , Validators.pattern('^[^!@#$%^&*()_+<>?\'\"{}\\`~|/\\\\]+$')]),
          customerImg: new FormControl(customerEdit.customerImg, Validators.required),
          customerType: new FormControl(customerEdit.customerType.customerTypeId, Validators.required),
          account: new FormControl(customerEdit.account),
          cart: new FormControl(customerEdit.cart),
          enable: new FormControl(customerEdit.enable),
        });
      }, (error) => {
        console.log(error);
      }, () => {
        console.log(this.customerFormEdit);
      });
    });
  }

  editCustomer(id: string): void {
    if (this.inputImage != null && this.maxSize !== true) {
      const nameImg = formatDate(new Date(), 'dd-MM-yyyy_hh:mm:ss:a_', 'en-US') + this.inputImage.name;
      const fileRef = this.storage.ref(nameImg);
      this.storage.upload(nameImg, this.inputImage).snapshotChanges().pipe(
        finalize(() => {
          fileRef.getDownloadURL().subscribe(url => {
            this.customerFormEdit.patchValue({employeeImg: url});
            console.log('Change');
            this.customerTypeService.findByIdCustomerType(this.customerFormEdit.get('customerType').value).subscribe(
              (data) => {
                this.customerFormEdit.patchValue({
                  customerType: data
                });
              },
              () => {
              },
              () => {
                this.customerService.updateCustomer(id, this.customerFormEdit.value).subscribe(
                  () => {
                    this.router.navigateByUrl('/customers').then(() => {
                      Swal.fire('Thành công',
                        'Đã chỉnh sửa khách hàng thành công',
                        'success');
                    });
                    /*console.log(this.customerFormEdit);*/
                  },
                  (error) => {
                    Swal.fire('Lỗi',
                      'Không chỉnh sửa khách hàng thành công',
                      'success');
                    console.log(error);

                  },
                  () => {
                    this.router.navigateByUrl('/customers');
                  }
                );
              }
            );
          });
        })
      ).subscribe();
    } else {
      console.log('No change');
      this.customerTypeService.findByIdCustomerType(this.customerFormEdit.get('customerType').value).subscribe(
        (data) => {
          this.customerFormEdit.patchValue({
            customerType: data
          });
        },
        () => {
        },
        () => {
          this.customerService.updateCustomer(id, this.customerFormEdit.value).subscribe(
            () => {
              this.router.navigateByUrl('/customers').then(() => {
                Swal.fire('Thành công',
                  'Đã chỉnh sửa khách hàng thành công',
                  'success');
              });
              /*console.log(this.customerFormEdit);*/
            },
            (error) => {
              Swal.fire('Lỗi',
                'Không chỉnh sửa khách hàng thành công',
                'success');
              console.log(error);

            },
            () => {
              this.router.navigateByUrl('/customers');
            }
          );
        }
      );
    }
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
