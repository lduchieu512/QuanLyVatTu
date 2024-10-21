import {Component, OnInit} from '@angular/core';
import {CartWithDetail} from '../../model/cart-with-detail';
import {CartService} from '../../service/cart.service';
import {Cart} from '../../model/Cart';
import {CartDetail} from '../../model/CartDetail';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {PaymentService} from '../../service/payment.service';


@Component({
  selector: 'app-cart-list',
  templateUrl: './cart-list.component.html',
  styleUrls: ['./cart-list.component.css']
})
export class CartListComponent implements OnInit {
  rf: FormGroup;
  cart?: Cart;
  details?: CartDetail[];
  total = 0;
  paymentMethod = 'direct';
  allChecked: boolean = false;

  constructor(private cartService: CartService,
              private paymentService: PaymentService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getCart();
  }

  getTotalAmount() {
    let temp = 0;
    this.details.forEach(item => {
      if (item.status === true) {
        temp += item.quantity * item.product.productPrice;
      }
    });
    this.total = temp;
  }

  getCart() {
    this.cartService.getCart().subscribe(next => {
      this.cart = next.cart;
      this.details = next.cartDetailList;
      this.formBuilder();
    }, error => alert('Lỗi rồi đó'));
  }

  decreaseQuantity(cartDetailId: number) {
    const tempCartDetails: CartDetail[] = [];
    this.details.forEach(next => {
      if (next.cartDetailId === cartDetailId) {
          next.quantity--;
      }
      tempCartDetails.push(next);
    });
    this.details = tempCartDetails;
    this.getTotalAmount();
    this.cartService.updateCart(this.prepareCartForSendingToBackend()).subscribe();
  }

  increaseQuantity(cartDetailId: number) {
    const tempCartDetails: CartDetail[] = [];
    this.details.forEach(next => {
      if (next.cartDetailId === cartDetailId) {
        next.quantity++;
      }
      tempCartDetails.push(next);
    });
    this.details = tempCartDetails;
    this.getTotalAmount();
    this.cartService.updateCart(this.prepareCartForSendingToBackend()).subscribe();
  }

  checkAll() {
    this.allChecked = true;
    const tempCartDetails: CartDetail[] = [];
    this.details.forEach(item => {
      item.status = true;
      tempCartDetails.push(item);
    });
    this.details = tempCartDetails;
    this.getTotalAmount();
  }

  uncheckAll() {
    this.allChecked = false;
    const tempCartDetails: CartDetail[] = [];
    this.details.forEach(item => {
      item.status = false;
      tempCartDetails.push(item);
    });
    this.details = tempCartDetails;
    this.getTotalAmount();
  }

  formBuilder() {
    this.rf = new FormGroup({
      receiverName: new FormControl(this.cart.receiverName, [Validators.required, Validators.pattern('^[AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+ [AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+(?: [AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]*)*')]),
      receiverAddress: new FormControl(this.cart.receiverAddress, [Validators.required, Validators.pattern('^[^!@#$%^&*()_+<>?\'\"{}\\`~|/\\\\]+$')]),
      receiverPhone: new FormControl(this.cart.receiverPhone, [Validators.required, Validators.pattern('^0\\d{9,10}')]),
      receiverEmail: new FormControl(this.cart.receiverEmail, [Validators.required, Validators.email])
    });
  }

  save() {
    this.cartService.updateCart(this.prepareCartForSendingToBackend()).subscribe(next => {
      Swal.fire({
        title: 'Đã huỷ!',
        text: 'Đã huỷ thao tác, quay về trang chính',
        icon: 'info',
        confirmButtonText: 'Cool'
      });
      this.router.navigateByUrl('/');
    });
  }

  checkout() {
    if (this.paymentMethod === 'direct') {
      this.cartService.checkout(this.prepareCartForSendingToBackend()).subscribe(next => {
        Swal.fire({
          title: 'Thành công!',
          text: 'Đã đặt hàng thành công, xin cảm ơn',
          icon: 'success',
          confirmButtonText: 'Cool'
        });
        this.router.navigateByUrl('/');
      });
    } else {
      this.paymentService.getPaid(this.prepareCartForSendingToBackend()).subscribe(next => {
        const url = next.url;
        window.location.href = url;
      });
    }
  }

  prepareCartForSendingToBackend(): CartWithDetail {
    this.cart.receiverName = this.rf.value.receiverName;
    this.cart.receiverAddress = this.rf.value.receiverAddress;
    this.cart.receiverPhone = this.rf.value.receiverPhone;
    this.cart.receiverEmail = this.rf.value.receiverEmail;
    const cartWithDetail: CartWithDetail = {cart: this.cart, cartDetailList: this.details};
    cartWithDetail.cartDetailList = this.details;
    cartWithDetail.cart = this.cart;
    return cartWithDetail;
  }


  changeMethod(e) {
    this.paymentMethod = e.target.value;
  }
}
