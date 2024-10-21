import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CartWithDetail} from '../model/cart-with-detail';
import {PaymentResponse} from '../model/payment-response';
import {TokenStorageService} from '../../security/service/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private _API_URL = 'http://localhost:8080/api/v1/payment';

  constructor(private httpClient: HttpClient,
              private tokenStorageService: TokenStorageService) { }

  getPaid(cartWithDetail: CartWithDetail): Observable<PaymentResponse> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.put<PaymentResponse>(`${this._API_URL}`, cartWithDetail, {headers});
  }

  transactionSuccess(txnRef: string): Observable<any> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(`${this._API_URL}/transaction/${txnRef}`, {headers});
  }

  transactionFail(txnRef: string): Observable<any> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(`${this._API_URL}/fail/${txnRef}`, {headers});
  }
}
