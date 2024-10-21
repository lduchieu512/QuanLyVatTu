import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ReceiptType} from '../model/ReceiptType';
import {TokenStorageService} from '../../security/service/token-storage.service';
import {Employee} from '../../employee/model/Employee';
import {Supplier} from '../model/Supplier';
import {ProductDTO} from '../model/ProductDTO';
import {ReceiptDTO} from '../model/ReceiptDTO';
import {ReceiptDetailDTO} from '../model/ReceiptDetailDTO';
import {Receipt} from '../model/Receipt';

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {
  private _API_URL = 'http://localhost:8080/api/v1/receipt/';

  constructor(private httpClient: HttpClient,
              private tokenStorageService: TokenStorageService) { }

  findAllReceiptType(): Observable<ReceiptType[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}` );
    return this.httpClient.get<ReceiptType[]>(this._API_URL + 'receipt-type', {headers});
  }
  findAllReceipt(): Observable<Receipt[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}` );
    return this.httpClient.get<Receipt[]>(this._API_URL + 'findAllReceipt', {headers});
  }
  getNameEmployee(): Observable<Employee> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}` );
    return this.httpClient.get<Employee>(this._API_URL + 'name-employee', {headers});
  }
  getReceiptByInvoiceCode(invoiceCode: string): Observable<Receipt> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}` );
    return this.httpClient.get<Receipt>(this._API_URL + 'getInvoiceCode/' + invoiceCode, {headers});
  }
  findAllSupplier(): Observable<Supplier[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}` );
    return this.httpClient.get<Supplier[]>(this._API_URL + 'supplier', {headers});
  }
  getProductByCustomerId(customerId: number): Observable<ProductDTO[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}` );
    return this.httpClient.get<ProductDTO[]>(this._API_URL + 'product/' + customerId, {headers});
  }
  findProductDTOByProductId(productID: number): Observable<ProductDTO> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}` );
    return this.httpClient.get<ProductDTO>(this._API_URL + 'productDTO/' + productID, {headers});
  }
  saveInvoice(receiptDTO): Observable<ReceiptDTO> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<ReceiptDTO>(this._API_URL + 'create', receiptDTO , {headers});
  }
  deleteProductById(listProduct: ProductDTO[], targetId: number): boolean {
    const indexToDelete = listProduct.findIndex((product) => product.product_Id === targetId);

    if (indexToDelete !== -1) {
      listProduct.splice(indexToDelete, 1);
      return true;
    } else {
      return false;
    }
  }
  deleteReceiptDetailById(listReceiptDetailDTO: ReceiptDetailDTO[], targetId: number): boolean {
    const indexToDelete = listReceiptDetailDTO.findIndex((receiptDetailDTO) => receiptDetailDTO.productId === targetId);
    if (indexToDelete !== -1) {
      listReceiptDetailDTO.splice(indexToDelete, 1);
      return true;
    } else {
      return false;
    }
  }
  totalAmount(listProduct: ProductDTO[]) {
    let sum = 0;
    // tslint:disable-next-line:prefer-for-of
    for ( let i = 0; i < listProduct.length; i++) {
     sum = sum + +listProduct[i].product_Price * listProduct[i].product_Quantity;
    }
    return sum;
  }
  checkProduct(productDTOS: ProductDTO[], productId: number) {
    // tslint:disable-next-line:prefer-for-of
    for ( let i = 0 ; i < productDTOS.length ; i ++) {
      if (productDTOS[i].product_Id === productId) {
        return productDTOS[i];
      }
    }
    return null;
  }
  checkInvoiceCode(receipts: Receipt[], invoiceCode: string) {
    // tslint:disable-next-line:prefer-for-of
    for ( let i = 0 ; i < receipts.length ; i ++) {
      if (receipts[i].invoiceCode === invoiceCode) {
        return receipts[i];
      }
    }
    return null;
  }
}
