import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CartWithDetail} from '../model/cart-with-detail';
import {Observable} from 'rxjs';
import {TokenStorageService} from '../../security/service/token-storage.service';
import {CartDetail} from '../model/CartDetail';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  // Author: NhatLH
  private _API_URL = 'http://localhost:8080/api/v1/cart';

  constructor(private httpClient: HttpClient,
              private tokenStorageService: TokenStorageService) {
  }

  getCart(): Observable<CartWithDetail> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<CartWithDetail>(this._API_URL, {headers});
  }

  updateCart(cartWithDetail: CartWithDetail): Observable<CartWithDetail> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.put<CartWithDetail>(`${this._API_URL}/update`, cartWithDetail, {headers});
  }

  checkout(cartWithDetail: CartWithDetail): Observable<CartWithDetail> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`).set('Content-Type', 'application/json').set('charset', 'utf-8');
    return this.httpClient.put<CartWithDetail>(`${this._API_URL}/checkout`, cartWithDetail, {headers});
  }

  addToCart(productId: number): Observable<CartDetail[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<CartDetail[]>(`${this._API_URL}/add/${productId}`, {headers});
  }
}
