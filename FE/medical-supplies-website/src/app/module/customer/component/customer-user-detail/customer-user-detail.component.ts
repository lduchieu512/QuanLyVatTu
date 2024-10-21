import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {DatePipe} from '@angular/common';
import {CustomerUserDetail} from '../../model/CustomerUserDetail';
import {CustomerService} from '../../service/customer.service';
import Swal from 'sweetalert2';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-customer-user-detail',
  templateUrl: './customer-user-detail.component.html',
  styleUrls: ['./customer-user-detail.component.css']
})
export class CustomerUserDetailComponent implements OnInit {
  // tslint:disable-next-line:variable-name
  private _customerUserDetail: CustomerUserDetail;
  // tslint:disable-next-line:variable-name
  private _mainForm: FormGroup;

  // tslint:disable-next-line:variable-name
  constructor(private _customerService: CustomerService,
              // tslint:disable-next-line:variable-name
              private _router: Router) {
  }

  ngOnInit(): void {
    window.scroll(0, 0);
    this.mainForm = new FormGroup({
      customerName: new FormControl(''),
      phone: new FormControl(''),
      idCard: new FormControl(''),
      dateOfBirth: new FormControl(''),
      customerAddress: new FormControl(''),
      accountEmail: new FormControl('')
    });

    this._customerService.getUserDetail().pipe(
      tap(response => {
        if (response.status === 202) {
          Swal.fire({
            position: 'center',
            icon: 'info',
            title: 'Bạn phải đăng nhập để sử dụng chức năng này!',
            showConfirmButton: false,
            timer: 1500
          });
          this._router.navigateByUrl('/login');
        }
      })
    ).subscribe(response => {
      this.customerUserDetail = response.body;
      if (this.customerUserDetail.dateOfBirth) {
        this.customerUserDetail.dateOfBirth = new DatePipe('en-US').transform(new Date(this.customerUserDetail.dateOfBirth), 'yyyy-MM-dd');
      }
      this.mainForm.patchValue(this.customerUserDetail);
    }, error => {
      Swal.fire({
        position: 'center',
        icon: 'error',
        title: 'Bạn không có quyền truy cập chức năng này',
        showConfirmButton: false,
        timer: 1500
      });
      this._router.navigateByUrl('/');
    });
  }

  // Getters/Setters Begin.
  get customerUserDetail(): CustomerUserDetail {
    return this._customerUserDetail;
  }

  set customerUserDetail(value: CustomerUserDetail) {
    this._customerUserDetail = value;
  }

  get mainForm(): FormGroup {
    return this._mainForm;
  }

  set mainForm(value: FormGroup) {
    this._mainForm = value;
  }

  // Getters/Setters End.
}
