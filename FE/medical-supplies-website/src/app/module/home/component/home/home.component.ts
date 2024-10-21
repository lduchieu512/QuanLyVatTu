import {Component, OnInit} from '@angular/core';
import {ProductMain} from '../../model/product-main';
import {HomeService} from '../../service/home.service';
import {FormControl, FormGroup} from '@angular/forms';
import {CategoryHomeService} from '../../service/category-home.service';
import {CategoryMain} from '../../model/category-main';
import {CartService} from '../../../cart/service/cart.service';
import {Cart} from '../../../cart/model/Cart';
import {CartDetail} from '../../../cart/model/CartDetail';
import Swal from 'sweetalert2';
import {ActivatedRoute} from '@angular/router';
import {TokenStorageService} from '../../../security/service/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  productsMain: ProductMain[] = [];
  rfSearch: FormGroup;
  categories: CategoryMain[];
  cart?: Cart;
  cartDetails?: CartDetail[];
  role = '';
  currentCategoryId = 0;
  currentProductNameSearch = '';
  totalPages = 0;

  constructor(private homeService: HomeService,
              private categoryHomeService: CategoryHomeService,
              private cartService: CartService,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.getAllCategory();

    this.getAllProduct();

    this.rfSearch = new FormGroup({
      productName: new FormControl('')
    });

    if (this.loadRole() === 'ROLE_USER') {
      this.getCart();
    }
  }

  getAllProduct() {
    this.currentCategoryId = 0;
    this.currentProductNameSearch = '';
    this.activeCategories(0);
    this.homeService.findAll().subscribe(next => {
        this.totalPages = next.totalPages;
        this.productsMain = next.content;
      },
      error => {
        console.error('Error fetching product: ', error);
      });
  }

  getAllCategory() {
    this.categoryHomeService.getCategories().subscribe((data) => {
        this.categories = data;
      },
      error => {
        console.error('Error fetching category:', error);
      }
    );
  }

  searchProductName() {
    this.activeCategories(0);
    const productName = this.rfSearch.value.productName;
    if (productName !== '' && productName != null) {
      this.homeService.findAll(undefined, productName).subscribe(next => {
        this.rfSearch.reset();
        const productListContainer = document.getElementById('main-home-start');
        productListContainer.scrollIntoView({behavior: 'smooth'});
        this.currentProductNameSearch = productName;
        if (next != null) {
          this.currentCategoryId = 0;
          this.totalPages = next.totalPages;
          this.productsMain = next.content;
        } else {
          this.productsMain = [];
          this.totalPages = 1;
          this.currentCategoryId = 0;
        }
      });
    }
  }

  searchCategory(categoryId: number) {
    this.activeCategories(categoryId);
    this.homeService.findAll(categoryId).subscribe(next => {
      const productListContainer = document.getElementById('main-home-start');
      productListContainer.scrollIntoView({behavior: 'smooth'});
      if (next != null) {
        this.currentCategoryId = categoryId;
        this.currentProductNameSearch = '';
        this.totalPages = next.totalPages;
        this.productsMain = next.content;
      } else {
        this.currentCategoryId = 0;
        this.currentProductNameSearch = '';
        this.totalPages = 1;
        this.productsMain = [];
      }
    });
  }

  /*
  * Author: NhatLH
  * Created: 2023-07-27
  * */
  getCart() {
    return this.cartService.getCart().subscribe(next => {
      this.cart = next.cart;
      this.cartDetails = next.cartDetailList;
    });
  }

  addToCart(productId: number) {
    let flag = false;
    this.cartDetails.forEach(value => {
      if (value.product.productId === productId) {
        flag = true;
      }
    });
    if (flag) {
      Swal.fire('Lưu ý',
        'Sản phẩm đã có trong giỏ',
        'info');
    } else {
      this.cartService.addToCart(productId).subscribe(next => {
        Swal.fire('Thành công',
          'Đã thêm sản phẩm vào giỏ',
          'success');
      });
    }
  }

  loadRole(): string {
    if (this.tokenStorageService.getToken()) {
      this.role = this.tokenStorageService.getRole();
    }
    return this.role;
  }

  activeCategories(categoryId: number) {
    const categoryElements = document.getElementsByClassName('category__item');
    const categories = Array.from(categoryElements);
    if (categories.length > 0) {
      for (const element of categories) {
        if (element.className.includes('active')) {
          element.classList.remove('active');
        }
      }
    }
    document.getElementById(`category__item-${categoryId}`).classList.add('active');
  }

  changePage($event: number) {
    const currentPage = $event;

    const productListContainer = document.getElementById('main-home-start');
    productListContainer.scrollIntoView({behavior: 'smooth'});

    this.homeService.findAll(this.currentCategoryId, this.currentProductNameSearch, currentPage).subscribe(next => {
      this.productsMain = next.content;
      this.totalPages = next.totalPages;
    });
  }
}
