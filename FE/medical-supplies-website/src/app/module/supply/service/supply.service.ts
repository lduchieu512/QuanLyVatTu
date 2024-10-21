import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { TokenStorageService } from '../../security/service/token-storage.service';
import {Supply} from '../model/Supply';

@Injectable({
  providedIn: 'root'
})
export class SupplyService {

  private  _API_URL = 'http://localhost:8080/api/v1/supply';

  constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) { }

  getAll(): Observable<any> {
    console.log('check');
    const token: string = this.tokenStorageService.getToken();
    const headers: HttpHeaders = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this._API_URL, {headers});
  }

  search(keyword: string): Observable<any> {
    const token: string = this.tokenStorageService.getToken();
    const headers: HttpHeaders = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this._API_URL}${keyword}`, {headers});
  }
}
