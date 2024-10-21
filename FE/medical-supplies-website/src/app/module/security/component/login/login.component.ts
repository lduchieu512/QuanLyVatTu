import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import {TokenStorageService} from '../../service/token-storage.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ShareService} from '../../service/share.service';
import {tap} from 'rxjs/operators';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  formLogin: FormGroup;
  username = '';
  roles: string[] = [];
  returnUrl: string;
  message = '';
  showPassword = false;

  constructor(
    private authService: AuthService,
    private tokenStorageService: TokenStorageService,
    private shareService: ShareService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    // Lấy returnUrl từ query params khi người dùng chuyển hướng từ AuthGuard
    this.returnUrl = this.activatedRoute.snapshot.queryParams['returnUrl'] || '/';
    this.formLogin = new FormGroup({
        username: new FormControl('', [
          Validators.required,
          Validators.pattern('^[^!@#$%^&*()_+=-]+$')
        ]),
        password: new FormControl('', [
          Validators.required,
          Validators.maxLength(32)
        ]),
        remember_me: new FormControl('')
      }
    );

    if (this.tokenStorageService.getToken()) {
      const user = this.tokenStorageService.getUser();
      this.authService.isLoggedIn = true;
      this.roles = this.tokenStorageService.getRole();
      this.username = this.tokenStorageService.getUser();
    }
  }

  onSubmit() {
    let timerInterval
    Swal.fire({
      title: 'Đang tải dữ liệu!',
      html: '<b></b>',
      timer: 2000,
      timerProgressBar: true,
      didOpen: () => {
        Swal.showLoading()
        const b = Swal.getHtmlContainer().querySelector('b')
        timerInterval = setInterval(() => {
          b.textContent = String(Swal.getTimerLeft())
        }, 100)
      },
      willClose: () => {
        clearInterval(timerInterval)
      }
    }).then((result) => {
      /* Read more about handling dismissals below */
      if (result.dismiss === Swal.DismissReason.timer) {
        console.log('I was closed by the timer')
      }
    })
    this.authService.login(this.formLogin.value)
      .pipe(
        tap(response => {
          Swal.fire({
            icon: 'error',
            title: 'Lỗi...',
            text: 'Thông tin đăng nhập không hợp lệ.',
            showConfirmButton: false,
            timer: 1500
          });
        })
      )
      .subscribe(data => {
          if (this.formLogin.value.remember_me) {
            sessionStorage.clear();
            this.tokenStorageService.saveTokenLocal(data.token);
            this.tokenStorageService.saveUserLocal(data.username);
            this.tokenStorageService.saveRoleLocal(data.roles[0]);
          } else {
            localStorage.clear();
            this.tokenStorageService.saveTokenSession(data.token);
            this.tokenStorageService.saveUserSession(data.username);
            this.tokenStorageService.saveRoleSession(data.roles[0]);
          }
          this.authService.isLoggedIn = true;
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Đăng nhập thành công',
            showConfirmButton: false,
            timer: 1500
          });
          this.formLogin.reset();
          this.router.navigateByUrl(this.returnUrl);
          this.shareService.sendClickEvent();
        },
        err => {
          this.authService.isLoggedIn = false;
        });
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }
}
