import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ReceiptService} from '../../service/receipt.service';
import {ReceiptType} from '../../model/ReceiptType';
import {Employee} from '../../../employee/model/Employee';
import {Supplier} from '../../model/Supplier';
import {ProductDTO} from '../../model/ProductDTO';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ReceiptDetailDTO} from '../../model/ReceiptDetailDTO';
import Swal from 'sweetalert2';
import {Receipt} from '../../model/Receipt';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-receipt-list',
  templateUrl: './receipt-list.component.html',
  styleUrls: ['./receipt-list.component.css']
})
export class ReceiptListComponent implements OnInit {
  @ViewChild('htmlContent') htmlContent!: ElementRef;
  @ViewChild('pdfViewer') pdfViewer!: ElementRef;
  colorBg = 'none';
  color = 'white';
  receiptTypes: ReceiptType[] = [];
  suppliers: Supplier[] = [];
  todayDate: string;
  employee: Employee ;
  address: string;
  productDTOs: ProductDTO[] = [];
  formReceipt: FormGroup;
  productDTO: ProductDTO ;
  listProduct: ProductDTO[] = [];
  listReceiptDetailDTO: ReceiptDetailDTO[] = [];
  receipt: Receipt;
  receipts: Receipt[];
  totalAmount: number;
  isSaved: boolean = false;
  constructor(private receiptService: ReceiptService) {
    // Lấy ngày hôm nay
    const today = new Date();

    // Định dạng ngày theo y-m-d
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
    const yyyy = today.getFullYear();

    this.todayDate = `${yyyy}-${mm}-${dd}`;
  }

  ngOnInit(): void {
    this.receiptService.findAllReceiptType().subscribe(next => {
      this.receiptTypes = next;

    });
    this.receiptService.getNameEmployee().subscribe(next => {
      this.employee = next;
    });
    this.receiptService.findAllSupplier().subscribe(next => {
      this.suppliers = next;
    });
    this.formReceipt = new FormGroup({
      // tslint:disable-next-line:radix
      receiptTypeId: new FormControl('', [Validators.required]),
      invoiceCode: new FormControl('', [Validators.required, Validators.pattern('^HD-\\d{10}$')]),
      dateOfCreate: new FormControl(this.todayDate, ),
      employeeId: new FormControl('', ),
      customerId: new FormControl('', [Validators.required]),
      productId: new FormControl('', [Validators.required]),
      quantity: new FormControl('', [Validators.required, Validators.min(1)]),
    });
  }

  onSelectionChange(event: any) {
    const selectedValue = +event.target.value;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0 ; i < this.suppliers.length; i ++) {
    if (selectedValue === this.suppliers[i].customer_Id) {
          this.address = this.suppliers[i].customer_Address;
          this.receiptService.getProductByCustomerId(selectedValue).subscribe(next => {
            this.productDTOs = next;
            console.log(this.productDTOs);
          });
        }
      }
    this.listProduct = [];
    this.listReceiptDetailDTO = [];
    }

  addProduct(productId: string, quantity: string) {
    debugger
    const productDTO = this.receiptService.checkProduct(this.listProduct, + productId);
    if (productId === '' || quantity === '' ) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Bạn cần nhập đủ tên vật tư và số lượng',
        showConfirmButton: false,
        timer: 2000
      });
    } else  if (+quantity <= 0) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Lưu ý số lượng không được bé hơn hoặc bằng 0',
        showConfirmButton: false,
        timer: 2000
      });
    } else if (productDTO != null) {
      Swal.fire({
        title: 'Vật tư này đã được thêm vào danh sách?',
        text: 'Bạn có muốn cập nhật lại số lượng vật tư này không',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Cập nhật',
        cancelButtonText: 'Hủy'
      }).then((result) => {
        if (result.isConfirmed) {
          productDTO.product_Quantity = +quantity;
          for ( let i = 0; i< this.listReceiptDetailDTO.length ; i++ ) {
            if (this.listReceiptDetailDTO[i].productId === +productId) {
              this.listReceiptDetailDTO[i].quantity = + quantity;
            }
          }
          Swal.fire(
            'Thành công!',
            'Cập nhật vật tư thành công.',
            'success'
          );
        }
      });
    } else {
      this.receiptService.findProductDTOByProductId(+productId).subscribe(next => {
        next.product_Quantity = + quantity;
        this.listReceiptDetailDTO.push(new ReceiptDetailDTO(+productId, +quantity));
        this.listProduct.push(next);
        console.log(this.listReceiptDetailDTO);
      });
    }
  }

  createReceipt() {
      const receipt = this.formReceipt.value;
      this.receiptService.findAllReceipt().subscribe(next => {
      this.receipts = next;
      if (this.formReceipt.invalid) {
          Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Bạn cần nhập đủ tất cả các thông tin!',
            showConfirmButton: false,
            timer: 1500
          });
        } else if (this.listProduct.length === 0) {
          Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Danh sách vật tư trống. Xin mời thêm danh sách vật tư!',
            showConfirmButton: false,
            timer: 1500
          });
        } else if (this.receiptService.checkInvoiceCode(this.receipts, receipt.invoiceCode ) != null) {
          Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Mã hóa đơn đã tồn tại vui lòng kiểm tra lại!',
            showConfirmButton: false,
            timer: 1500
          });
        } else {
          receipt.receiptDetailDTOS = [];
          // tslint:disable-next-line:prefer-for-of
          for (let i = 0 ; i < this.listReceiptDetailDTO.length ; i++ ) {
            receipt.receiptDetailDTOS.push(this.listReceiptDetailDTO[i]);
          }
          this.receiptService.saveInvoice(receipt).subscribe();
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Lưu hóa đơn thành công',
            showConfirmButton: false,
            timer: 1500
          });
          this.isSaved = true;
        }
    });
  }

  getProduct(productId: number) {
    this.receiptService.findProductDTOByProductId(productId).subscribe(next => {
      this.productDTO = next;
    });
  }

  deleteProduct(productId: number) {
    this.receiptService.deleteReceiptDetailById(this.listReceiptDetailDTO, productId);
    this.receiptService.deleteProductById(this.listProduct, productId);
  }

  selectInvoiceCode(value: string) {
      this.receiptService.getReceiptByInvoiceCode(value).subscribe(next => {
        this.receipt = next;
        this.totalAmount = this.receiptService.totalAmount(this.listProduct);
      });
  }
  printPDF() {
    const doc = new jsPDF();
    const pageWidth = doc.internal.pageSize.getWidth();
    const pageHeight = doc.internal.pageSize.getHeight();

    // Increase the height of the canvas
    html2canvas(this.htmlContent.nativeElement, { windowHeight: pageHeight }).then((canvas) => {
      const contentDataURL = canvas.toDataURL('image/png');
      doc.addImage(contentDataURL, 'PNG', 5, 0, pageWidth - 10, 0);

      // Calculate the additional height needed for the remaining content
      let remainingContentHeight = canvas.height - pageHeight;
      let currentY = pageHeight;

      // Loop through and add the remaining content in multiple pages if needed
      while (remainingContentHeight > 0) {
        doc.addPage();
        doc.addImage(contentDataURL, 'PNG', 5, -currentY, pageWidth - 10, 0);
        currentY += pageHeight;
        remainingContentHeight -= pageHeight;
      }

      doc.save('file.pdf');
    });
  }
  // checkInvoiceCode(control: AbstractControl): ValidationErrors | null {
  //   debugger
  //   const invoiceCode = control.value;
  //   if (this.receipts != null) {
  //     // tslint:disable-next-line:prefer-for-of
  //     for ( let i = 0 ; i < this.receipts.length ; i++) {
  //       if (invoiceCode === this.receipts[i].invoiceCode) {
  //         return {invalidScore: true};
  //       }
  //     }
  //   }
  //   return null;
  // }
}
