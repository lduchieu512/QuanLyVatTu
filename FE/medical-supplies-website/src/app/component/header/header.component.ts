import {Component, OnInit} from '@angular/core';
import {ShareService} from '../../module/security/service/share.service';
import {TokenStorageService} from '../../module/security/service/token-storage.service';
import {Router} from '@angular/router';
import {EmployeeService} from '../../module/employee/service/employee.service';
import {CustomerService} from '../../module/customer/service/customer.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username: string;
  currentUser: string;
  role = '';
  isLoggedIn = false;
  returnUrl: string;
  userDetailUrl = '';
  avatarSrc = '';

  constructor(private tokenStorageService: TokenStorageService,
              private shareService: ShareService,
              private router: Router,
              private employeeService: EmployeeService,
              private customerService: CustomerService) {
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
    });
  }

  ngOnInit(): void {
    this.loadHeader();
  }

  loadHeader(): void {
    if (this.tokenStorageService.getToken()) {
      this.currentUser = this.tokenStorageService.getUser();
      this.role = this.tokenStorageService.getRole();
      this.username = this.tokenStorageService.getUser();
      this.getUserDetail();
    }
    this.isLoggedIn = this.username != null;
    console.log(`Role hien tai la ${this.role}`);
  }

  logOut() {
    this.tokenStorageService.signOut();
    location.reload();
  }

  getUserDetail(): void {
    if (this.role === 'ROLE_USER') {
      this.userDetailUrl = 'customers/detail';
      this.customerService.getUserDetail().subscribe(response => {
        this.avatarSrc = response.body.customerImg;
      });
    } else {
      this.userDetailUrl = 'employees/detail';
      this.employeeService.getUserDetail().subscribe(response => {
        this.avatarSrc = response.body.employeeImg;
      });
    }
  }
}
