import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Account} from '../../model/Account';
import {AccountService} from '../../service/account.service';
import {Role} from '../../model/Role';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent implements OnInit {
  accountForm: FormGroup;
  roles: Role[] = [];


  constructor(private accountService: AccountService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getAllRoles();
    this.accountForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9]{4,30}$')]),
      // tslint:disable-next-line:max-line-length
      encryptPassword: new FormControl('', [Validators.required, Validators.pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,32}$'), Validators.minLength(6), Validators.maxLength(32)]),
      rePassword: new FormControl('', [Validators.required, Validators.pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,32}$'), Validators.minLength(6), Validators.maxLength(32)]),
      // tslint:disable-next-line:max-line-length
      email: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9_.+-]+@gmail.com+$'), Validators.minLength(6), Validators.maxLength(128)]),
      role: new FormControl('', Validators.required)
    });
  }

  getAllRoles() {
    this.accountService.getRoles().subscribe(
      (data: Role[]) => {
        this.roles = data;
      },
      (error) => {
        console.log('Error retrieving roles:', error);
      }
    );
  }

  submitAddAccount(): void {
    if (this.accountForm.valid) {
      if (this.accountForm.get('encryptPassword').value !== this.accountForm.get('rePassword').value) {
        Swal.fire({
          icon: 'error',
          title: 'Mật khẩu không giống nhau!',
          text: 'Vui lòng kiểm tra lại mật khẩu.',
        });
        return;
      }

      const username = this.accountForm.get('username').value;
      const email = this.accountForm.get('email').value;

      // Kiểm tra tài khoản và email có tồn tại trong cơ sở dữ liệu hay không
      this.accountService.checkExistingUsername(username).subscribe(
        (usernameExists) => {
          if (usernameExists) {
            Swal.fire({
              icon: 'error',
              title: 'Tài khoản đã tồn tại!',
              text: 'Vui lòng chọn tên tài khoản khác.',
            });
            return;
          } else {
            this.accountService.checkExistingEmail(email).subscribe(
              (emailExists) => {
                if (emailExists) {
                  Swal.fire({
                    icon: 'error',
                    title: 'Email đã tồn tại!',
                    text: 'Vui lòng chọn địa chỉ email khác.',
                  });
                  return;
                } else {
                  // Nếu tài khoản và email không trùng lặp, tiến hành thêm tài khoản vào cơ sở dữ liệu
                  const accountData: Account = {
                    username: this.accountForm.get('username').value,
                    encryptPassword: this.accountForm.get('encryptPassword').value,
                    email: this.accountForm.get('email').value,
                    role: this.accountForm.get('role').value
                  };
                  const roleId: number = this.accountForm.get('role').value.roleId;

                  this.accountService.addAccount(accountData, roleId).subscribe(
                    (data) => {
                      localStorage.setItem('tempAccount', JSON.stringify(data));
                      this.accountForm.reset();
                      this.router.navigateByUrl('/employees/create');
                      // Handle any additional actions or navigation here.
                      // Swal.fire({
                      //   icon: 'success',
                      //   title: 'Thành công!',
                      //   text: 'Bạn đã thêm tài khoản cho nhân viên thành công!',
                      // });
                    }, (error) => {
                      console.log('Error adding account:', error);
                      Swal.fire({
                        icon: 'error',
                        title: 'Bị lỗi rồi!',
                        text: 'Bạn không thêm tài khoản thành công',
                      });
                    }
                  );
                }
              },
              (error) => {
                console.log('Error checking existing email:', error);
              }
            );
          }
        },
        (error) => {
          console.log('Error checking existing username:', error);
        }
      );
    } else {
      for (const control in this.accountForm.controls) {
        if (this.accountForm.controls[control].invalid) {
          this.accountForm.controls[control].markAsTouched();
        }
      }
    }
  }

  resetForm(): void {
    this.accountForm.reset();
  }
}
