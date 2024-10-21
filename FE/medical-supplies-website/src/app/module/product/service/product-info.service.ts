import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ProductInfo} from '../model/ProductInfo';
import {TokenStorageService} from '../../security/service/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class ProductInfoService {
  constructor(private http: HttpClient,
              private tokenStorageService: TokenStorageService) { }
  private _API_URL = 'http://localhost:8080/api/v1/productInfo';

  getAll(): Observable<ProductInfo[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return  this.http.get<ProductInfo[]>(`${this._API_URL}/list`, {headers});
  }
}
