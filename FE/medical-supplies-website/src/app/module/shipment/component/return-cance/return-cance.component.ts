import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {CustomerDto} from '../../model/CustomerDto';
import {ShipmentType} from '../../model/ShipmentType';
import {ShipmentService} from '../../service/shipment.service';
import {Router} from '@angular/router';
import {ShipmentDetailDto} from '../../model/ShipmentDetailDto';
import {Employee} from '../../../employee/model/Employee';
import { FormControl, FormGroup, Validators} from '@angular/forms';
import {ShipmentDto} from '../../model/ShipmentDto';
import Swal from 'sweetalert2';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';
import {Shipment} from '../../model/Shipment';
import {IShipmentDto} from '../../model/IShipmentDto';


@Component({
  selector: 'app-return-cance',
  templateUrl: './return-cance.component.html',
  styleUrls: ['./return-cance.component.css']
})
export class ReturnCanceComponent implements OnInit {
  @ViewChild('htmlContent') htmlContent!: ElementRef;
  @ViewChild('pdfViewer') pdfViewer!: ElementRef;
  currentDate: Date = new Date();
  day: number = this.currentDate.getDate();
  month: number = this.currentDate.getMonth() + 1; // Tháng trong JavaScript bắt đầu từ 0, vì vậy cần cộng thêm 1.
  year: number = this.currentDate.getFullYear();
  phone = null;
  customerDto: CustomerDto;
  notFound: boolean;
  shipmentTypes: ShipmentType[];
  shipmentDetailDtos: ShipmentDetailDto[] = [];
  shipmentDetail: ShipmentDetailDto;
  employees: Employee;
  shipmentForm: FormGroup;
  shipmentDto: ShipmentDto;
  shipment: Shipment;
  iShipmentDtos: IShipmentDto[];
  isSaved: boolean = false;
  constructor(private shipmentService: ShipmentService,
              private router: Router) {
    this.shipmentForm = new FormGroup({
      shipmentTypeId: new FormControl(''),
      invoiceCode: new FormControl(''),
      customerId: new FormControl(''),
      phone: new FormControl('')
    });
  }

  ngOnInit(): void {
    this.shipmentForm = new FormGroup({
      shipmentTypeId: new FormControl('2', [Validators.required]),
      invoiceCode: new FormControl('', [Validators.required, Validators.pattern('HD-\\d{3}')]),
      customerId: new FormControl(''),
      phone: new FormControl('', [Validators.required, Validators.pattern('^0\\d{9}$')])
    });
    if (this.shipmentService.getTempShipmentDto() !== undefined) {
      this.shipmentForm.patchValue(this.shipmentService.getTempShipmentDto());
      const phoneElement = document.getElementById('phone-number') as HTMLInputElement;
      phoneElement.value = this.shipmentService.getTempShipmentDto().phone;
      this.onPhoneInputChange();
    }
    this.findAllShipmentInvoceCode();
    this.findAllShipmentType();
    this.findAllProductShipmentCreate();
    this.getAllEmployyUserName();
  }

  findAllShipmentType() {
    this.shipmentService.findAllShipmentType().subscribe(shipmentTypes => {
      this.shipmentTypes = shipmentTypes;
    });
  }
  /*hiển thị hết mã hóa đơn*/
  findAllShipmentInvoceCode() {
    this.shipmentService.findAllInvoiceCode().subscribe(shipment => {
      this.iShipmentDtos = shipment;
    }) ;
  }

  /*Gọi API xử lí có số điện thoại thì tự động điền*/
  onPhoneInputChange() {
    const inputElement = document.getElementById('phone-number') as HTMLInputElement;
    this.phone = inputElement.value;
    this.notFound = false;
    if (this.phone.length >= 9 && this.phone.length <= 11 ) {// Chỉ gọi API bắt đầu nhập vào ô input
      this.shipmentService.selectPhone(this.phone).subscribe(data => {
          this.customerDto = data;
          this.notFound = false; // Reset biến notFound khi API trả về dữ liệu
        },
        (error) => {
          console.error(error);
          this.customerDto = null;
          this.notFound = true; // Báo lỗi nếu số điện thoại không tồn tại
        }
      );
    }
  }
  /*Hiển thị vật tư tạm*/
  findAllProductShipmentCreate() {
    this.shipmentDetailDtos = this.shipmentService.getShipmentDetailDto();
  }

  /*Tìm id vật tư list tạm*/
  getShipmentDetailDto(productId: number) {
    this.shipmentDetail = this.shipmentService.findByShipmentDetailDto(productId);
  }
  /*Xóa vật tư list tạm*/
  removeShipmentDetailDto() {
    this.shipmentDetail = this.shipmentService.findByShipmentDetailDto(this.shipmentDetail.productId);
    this.shipmentService.deleteShipmentDeatailDto(this.shipmentDetail.productId);
    Swal.fire({
      position: 'center',
      icon: 'success',
      title: 'Xóa vật tư thành công',
      showConfirmButton: false,
      timer: 1500
    });
    this.shipmentDetailDtos = this.shipmentService.getShipmentDetailDto();
  }

  /*get tên đăng nhập*/
  getAllEmployyUserName() {
    this.shipmentService.getEmployee().subscribe(data => {
      this.employees = data;
    });
  }

  /*Lưu xóa đơn xuất kho*/
  saveInvoice() {
    this.shipmentService.findAllInvoiceCode().subscribe(shipment => {
      this.iShipmentDtos = shipment;
      this.shipmentDto = this.shipmentForm.value;
      if (this.shipmentForm.invalid) {
        Swal.fire({
          position: 'center',
          icon: 'warning',
          title: 'Vui lòng nhập đầy đủ thông tin!',
          showConfirmButton: false,
          timer: 1500
        });
      } else if (this.checkInvoiceCode(this.iShipmentDtos, this.shipmentDto.invoiceCode) != null) {
        Swal.fire({
          position: 'center',
          icon: 'warning',
          title: 'Mã hóa đơn đã tồn tại !',
          showConfirmButton: false,
          timer: 1500
        });
      } else {
        this.shipmentDto.customerId = this.customerDto.customer_Id;
        this.shipmentDto.employeeId = this.employees.employeeId;
        this.shipmentDto.listShipmentDetailDtos = this.shipmentDetailDtos;
        if (!this.shipmentDto.listShipmentDetailDtos || this.shipmentDto.listShipmentDetailDtos.length === 0) {
          Swal.fire({
            position: 'center',
            icon: 'warning',
            title: 'Vui lòng nhập danh sách vật tư !',
            showConfirmButton: false,
            timer: 1500
          });
        }
        this.shipmentService.saveInvoice(this.shipmentDto).subscribe(data => {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Lưu hóa đơn thành công',
            showConfirmButton: false,
            timer: 1500
          });
          this.isSaved = true;
          console.log(data);
        });
      }
    }) ;
  }
  /*giữ nội dung khi chuyển trang*/
  saveTempShipmentDto() {
    this.shipmentDto = this.shipmentForm.value;
    this.shipmentDto.phone = this.phone;
    this.shipmentService.setTempShipmentDto(this.shipmentDto);
  }
  /*lay thong tin in hóa đơn*/
  selectInvoiceCode(value: string) {
    this.shipmentService.printInvoiceCode(value).subscribe(next => {
      this.shipment = next;
    });
  }
  /*in hóa đơn*/
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
  // Hàm tính tổng tiền
  calculateTotalPrice() {
    let totalPrice = 0;
    if (this.shipmentDetailDtos) {
      for (const item of this.shipmentDetailDtos) {
        totalPrice += item.productPrice * item.quantity;
      }
    }
    return totalPrice;
  }
  checkInvoiceCode(shipments: IShipmentDto[], invoiceCode: string) {
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < shipments.length ; i++) {
      if (shipments[i].invoice_Code === invoiceCode) {
        return shipments[i];
      }
    }
    return null;
  }
}
