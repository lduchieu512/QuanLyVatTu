import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {ShipmentType} from '../model/ShipmentType';
import {TokenStorageService} from '../../security/service/token-storage.service';
import {Customer} from '../../customer/model/Customer';
import {ProductDto} from '../model/ProductDto';
import {ShipmentDetailDto} from '../model/ShipmentDetailDto';
import {ShipmentDto} from '../model/ShipmentDto';
import {Employee} from '../../employee/model/Employee';
import {Shipment} from '../model/Shipment';
import {IShipmentDto} from '../model/IShipmentDto';
import Swal from 'sweetalert2';


@Injectable({
  providedIn: 'root'
})
export class ShipmentService {
  private _API_URL = 'http://localhost:8080/api/v1/shipment/';
  shipmentDetailDto: ShipmentDetailDto[];
  tempShipmentDto: ShipmentDto;
  selectedProductIds: number[] = []; // Duy trì danh sách id sản phẩm đã chọn
  constructor(private httpClient: HttpClient,
              private tokenStorageService: TokenStorageService) {
  }

  findAllShipmentType(): Observable<ShipmentType[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<ShipmentType[]>(this._API_URL + 'shipment-type', {headers});
  }

  /*Nhập phone tìm khách hàng*/
  selectPhone(phone: string): Observable<Customer> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get(this._API_URL + 'phone/' + phone, {headers});
  }

  /*Lấy vật tư để chọn*/
  findAllProduct(): Observable<ProductDto[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<ProductDto[]>(this._API_URL + 'product-shipment-create', {headers});
  }

  /*Lấy dữ liệu in hóa đơn*/
  printInvoiceCode(invoiceCode): Observable<Shipment> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<Shipment>(this._API_URL + 'invoiceCode/' + invoiceCode, {headers});
  }

  /*FindAll mã hóa đơn*/
  findAllInvoiceCode(): Observable<IShipmentDto[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<IShipmentDto[]>(this._API_URL + 'listShipment', {headers});
  }

  /*Lưu hóa đơn*/
  saveInvoice(shipmentDto): Observable<ShipmentDto> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<ShipmentDto>(this._API_URL + 'create', shipmentDto, {headers});
  }

  /*Thêm vật tư qua list tạm ShipmentDetailDto[];*/
  addShipmentProductDetailDto(p: ProductDto) {
    let productDtos: ProductDto[] = [];
    this.findAllProduct().subscribe(data => {
      productDtos = data;
      if (!this.shipmentDetailDto) {
        this.shipmentDetailDto = [];
      }
      const existingShipmentItem = this.shipmentDetailDto.find(item => item.productId === p.product_Id);
      if (existingShipmentItem) {
        // Nếu sản phẩm đã tồn tại, tăng số lượng và cập nhật ghi chú
        existingShipmentItem.quantity += p.product_Quantity;
        existingShipmentItem.note = p.note;
      } else {
        const shipmentItem: ShipmentDetailDto = {
          productId: p.product_Id,
          productName: p.product_Name,
          productPrice: p.product_Price,
          quantity: p.product_Quantity,
          note: p.note
        };
        // tslint:disable-next-line:prefer-for-of
        for (let i = 0; i < productDtos.length; i++) {
          if (shipmentItem.productId === productDtos[i].product_Id) {
            if (shipmentItem.quantity > productDtos[i].product_Quantity) {
              Swal.fire({
                position: 'center',
                icon: 'warning',
                title: 'Số lượng lớn hơn trong kho!',
                showConfirmButton: false,
                timer: 1500
              });
            } else if (shipmentItem.quantity <= 0) {
              Swal.fire({
                position: 'center',
                icon: 'warning',
                title: 'Số lượng phải lớn hơn 0!',
                showConfirmButton: false,
                timer: 1500
              });
            } else {
              this.shipmentDetailDto.push(shipmentItem);
              this.selectedProductIds.push(p.product_Id); // Đánh dấu sản phẩm đã được chọn
            }
          }
        }
      }
    });
  }

  /*Xóa chọn checkbox*/
  removeShipmentProductDetailDto(p: ProductDto) {
    const existingShipmentItemIndex = this.shipmentDetailDto.findIndex(item => item.productId === p.product_Id);
    if (existingShipmentItemIndex !== -1) {
      this.shipmentDetailDto.splice(existingShipmentItemIndex, 1);
      this.selectedProductIds = this.selectedProductIds.filter(productId => productId !== p.product_Id);
    }
  }

  /*Hiển thị list tạm ShipmentDetailDto[];*/
  getShipmentDetailDto() {
    return this.shipmentDetailDto;
  }

  /*Tìm id vật tư tạm*/
  findByShipmentDetailDto(id: number) {
    return this.shipmentDetailDto.find(data => data.productId === id);
  }

  /*Update vật tư tạm*/
  updateShipmentDetailDto(id: number, product: ShipmentDetailDto) {
    for (let i = 0; i < this.shipmentDetailDto.length; i++) {
      if (this.shipmentDetailDto[i].productId === id) {
        this.shipmentDetailDto[i] = product;
      }
    }
  }

  /*Xóa vật tư tạm*/
  deleteShipmentDeatailDto(id: number) {
    this.shipmentDetailDto = this.shipmentDetailDto.filter(data => {
      return data.productId !== id;
    });
  }

  /*lấy ra tên đăng nhập*/
  getEmployee(): Observable<Employee> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<Employee>(this._API_URL + 'name-employee', {headers});
  }

  /*giữ thông tin*/
  setTempShipmentDto(tempShipmentDto: ShipmentDto) {
    this.tempShipmentDto = tempShipmentDto;
  }

  getTempShipmentDto(): ShipmentDto {
    return this.tempShipmentDto;
  }
}
