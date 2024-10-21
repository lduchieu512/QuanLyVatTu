import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ProductService} from '../../service/product.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {FormControl, FormGroup} from '@angular/forms';
import {Product} from '../../model/Product';
import Swal from 'sweetalert2';
import {TokenStorageService} from "../../../security/service/token-storage.service";
import {CartService} from "../../../cart/service/cart.service";
import {CartDetail} from "../../../cart/model/CartDetail";

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  selectedImage: string;
  images: string[] = [
    '../../../../../assets/images/khautrang.png',
    '../../../../../assets/images/khautrang2.png',
    '../../../../../assets/images/khautrang3.png',
    '../../../../../assets/images/khautrang4.png'
  ];

  id: number;
  products: Product[] = [];
  productViewDetail: Product = {
    productInfo: {}
  };
  productDetail: FormGroup;
  quantity = 1;
  maxQuantity = 1;
  details: CartDetail[] = [];

  @ViewChild('quantityInput', {static: true}) quantityInput: ElementRef<HTMLInputElement>;
  role = '';

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private tokenStorageService: TokenStorageService,
              private cartService: CartService) {
  }

  ngOnInit(): void {
    this.getAll();
    this.getRole();
    if(this.role=='ROLE_USER'){
      this.getCart();
    }
  }

  getAll() {
    this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {
      this.id = +paramMap.get('id');
      this.productService.findByIdProductDetail(this.id).subscribe((productData) => {
        console.log(productData);
        this.productViewDetail = productData;
        this.quantity = this.productViewDetail.productQuantity || 1;
        this.maxQuantity = this.productViewDetail.productQuantity;
        this.quantityInput.nativeElement.value = this.quantity.toString();
        this.initProductDetailForm(productData);
      });
    });
  }

  getRole() {
    this.role = this.tokenStorageService.getRole()
  }

  private initProductDetailForm(productData: Product) {
    this.productDetail = new FormGroup({
      productId: new FormControl(productData.productId),
      productName: new FormControl(productData.productName),
      productPrice: new FormControl(productData.productPrice),
      productQuantity: new FormControl(productData.productQuantity),
      productImg: new FormControl(productData.productImg),
      productInfo: new FormControl(productData.productInfo),
    });
  }

  selectImage(image: string) {
    this.selectedImage = image;
  }

  increaseQuantity(): void {
    if (this.quantity < this.maxQuantity) {
      this.quantity++;
      this.quantityInput.nativeElement.value = this.quantity.toString();
    }
  }

  decreaseQuantity(): void {
    const minValue = 1;
    if (this.quantity > minValue) {
      this.quantity--;
      this.quantityInput.nativeElement.value = this.quantity.toString();
    }
  }

  addToCart(productId: number) {
    let flag = false;
    this.details.forEach(value => {
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

  getCart() {
    return this.cartService.getCart().subscribe(next => {
      this.details = next.cartDetailList;
    });
  }
}
