import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {EmployeeService} from '../../service/employee.service';
import {Router} from '@angular/router';
import {EmployeeUserDetail} from '../../model/EmployeeUserDetail';
import {DatePipe} from '@angular/common';
import Swal from 'sweetalert2';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-employee-user-detail',
  templateUrl: './employee-user-detail.component.html',
  styleUrls: ['./employee-user-detail.component.css']
})
export class EmployeeUserDetailComponent implements OnInit {
  private _employeeUserDetail: EmployeeUserDetail;
  private _mainForm: FormGroup;

  constructor(private _employeeService: EmployeeService,
              private _router: Router) {
  }

  ngOnInit(): void {
    window.scroll(0, 0);
    this.mainForm = new FormGroup({
      employeeName: new FormControl(''),
      phone: new FormControl(''),
      idCard: new FormControl(''),
      dateOfBirth: new FormControl(''),
      employeeAddress: new FormControl(''),
      accountEmail: new FormControl('')
    });

    this._employeeService.getUserDetail().pipe(
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
      this.employeeUserDetail = response.body;
      if (this.employeeUserDetail.dateOfBirth) {
        this.employeeUserDetail.dateOfBirth = new DatePipe('en-US').transform(new Date(this.employeeUserDetail.dateOfBirth), 'yyyy-MM-dd');
      }
      this.mainForm.patchValue(this.employeeUserDetail);
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

  public showUpdateComponent(): void {
    this._router.navigateByUrl('/employees/user-detail-update');
  }

  // Getters/Setters Begin.
  get employeeUserDetail(): EmployeeUserDetail {
    return this._employeeUserDetail;
  }

  set employeeUserDetail(value: EmployeeUserDetail) {
    this._employeeUserDetail = value;
  }

  get mainForm(): FormGroup {
    return this._mainForm;
  }

  set mainForm(value: FormGroup) {
    this._mainForm = value;
  }

  // Getters/Setters End.
}
