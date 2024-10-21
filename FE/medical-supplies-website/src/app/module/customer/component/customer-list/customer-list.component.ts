import {Component, OnInit} from '@angular/core';
import {Customer} from '../../model/Customer';
import {CustomerType} from '../../model/CustomerType';
import {CustomerService} from '../../service/customer.service';
import Swal from 'sweetalert2';
import {ActivatedRoute, Router} from '@angular/router';
import {FormControl, FormGroup} from '@angular/forms';


@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {
  keyword: string;
  customerForm: FormGroup;
  customers: Customer[] = [];
  customer: Customer;
  customerType: CustomerType[] = [];
  msg = false;
  totalPages: number [] = [];
  totalPage = 0;
  currentPage = 0;
  page = 0;

  ngOnInit(): void {
    this.getAll();
    this.getAllCustomerType();
  }

  constructor(private customerService: CustomerService, private route: ActivatedRoute, private router: Router) {
    this.route.queryParams.subscribe(param => {
      this.page = param.page || 1;
      this.currentPage = param.page || 1;
      this.keyword = '?';
      if (this.page !== 0 && this.page != null) {
        this.keyword += `page=${this.page}`;
      }
    });
    this.customerForm = new FormGroup({
      customerType: new FormControl(''),
      customerName: new FormControl(''),
      customerAddress: new FormControl(''),
      customerPhone: new FormControl(''),
    });
  }

  getAll() {
    this.customerService.getAllCustomerAndSearch(this.keyword).subscribe(customers => {
      if (customers != null) {
        this.customers = customers.content;
        this.totalPage = customers.totalPages;
        this.currentPage = customers.number;
        this.msg = false;
        this.totalPages = [];
        for (let j = 0; j < this.totalPage; j++) {
          this.totalPages.push(j);
        }
      } else {
        this.customers = [];
        this.msg = true;
      }
    });
  }

  nextPage() {
    this.page++;
    if (this.keyword.includes('page')) {
      const pageIndex = this.keyword.indexOf('page=');
      if (pageIndex !== -1) {
        this.keyword = this.keyword.substring(0, pageIndex);
      }
    }
    if (this.page !== 0 && this.page != null) {
      this.keyword += `page=${this.page}`;
    }
    console.log('next: ' + this.keyword);
    this.customerService.getAllCustomerAndSearch(this.keyword).subscribe(next => {
      this.customers = next.content;
      this.currentPage = next.number;
    });
  }

  previousPage() {
    this.page--;
    if (this.keyword.includes('page')) {
      console.log('keyword before remove page: ' + this.keyword);
      const pageIndex = this.keyword.indexOf('page=');
      if (pageIndex !== -1) {
        this.keyword = this.keyword.substring(0, pageIndex);
      }
      console.log('keyword after remove page: ' + this.keyword);
    }
    if (this.page !== 0 && this.page != null) {
      this.keyword += `page=${this.page}`;
    }
    this.customerService.getAllCustomerAndSearch(this.keyword).subscribe(next => {
      this.customers = next.content;
      this.currentPage = next.number;
    });
  }

  accessPage(page: number) {
    this.page = page;
    if (this.keyword.includes('page')) {
      const pageIndex = this.keyword.indexOf('page=');
      if (pageIndex !== -1) {
        this.keyword = this.keyword.substring(0, pageIndex);
      }
    }
    if (this.page !== 0 && this.page != null) {
      this.keyword += `page=${this.page}`;
    }
    this.customerService.getAllCustomerAndSearch(this.keyword).subscribe(next => {
      this.customers = next.content;
      this.currentPage = next.number;
    });
  }

  searchCustomer() {
    this.keyword = '?';
    const customerType = this.customerForm.value.customerType;
    if (customerType !== '' && customerType != null) {
      this.keyword += `type=${customerType}&`;
    }
    const customerName = this.customerForm.value.customerName;
    if (customerName !== '' && customerName != null) {
      this.keyword += `name=${customerName}&`;
    }
    const customerAddress = this.customerForm.value.customerAddress;
    if (customerAddress !== '' && customerAddress != null) {
      this.keyword += `address=${customerAddress}&`;
    }
    const customerPhone = this.customerForm.value.customerPhone;
    if (customerPhone !== '' && customerPhone != null) {
      this.keyword += `phone=${customerPhone}&`;
    }
    this.keyword += 'page=1';
    this.customerService.getAllCustomerAndSearch(this.keyword).subscribe(next => {
      if (next != null) {
        this.customers = next.content;
        this.totalPage = next.totalPages;
        this.currentPage = next.number;
        this.totalPages = [];
        this.msg = false;
        for (let j = 0; j < this.totalPage; j++) {
          this.totalPages.push(j);
        }
      } else {
        this.totalPages = [];
        this.customers = [];
        this.msg = true;
      }
    });
  }


  getAllCustomerType() {
    this.customerService.getAllCustomerType().subscribe(customerType => {
      this.customerType = customerType;
    });
  }

  deleteCustomer() {
    this.customerService.deleteCustomer(this.customer.customerId).subscribe(() => {
      this.customerService.getAllCustomerAndSearch(this.keyword).subscribe(data => {
        if (data !== null) {
          this.customers = data.content;
          this.totalPage = data.totalPages;
          this.currentPage = data.number;
          this.msg = false;
          this.totalPages = [];
          for (let j = 0; j < this.totalPage; j++) {
            this.totalPages.push(j);
          }
        } else {
          this.totalPages = [];
          this.customers = [];
          this.msg = true;
        }
      });
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Xóa thành công',
        showConfirmButton: false,
        timer: 1500
      });
    });
  }

  getCustomerId(id: number) {
    this.customerService.getCustomerId(id).subscribe(data => {
      this.customer = data;
    });
  }

  createCustomer() {
    this.router.navigateByUrl('/customers/create');
  }

  editCustomer(customerId: number) {
    this.router.navigateByUrl(`/customers/edit/${customerId}`);
  }
}
