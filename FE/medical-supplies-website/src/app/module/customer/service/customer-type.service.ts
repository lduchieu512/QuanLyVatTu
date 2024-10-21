import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CustomerType} from '../model/CustomerType';
import {TokenStorageService} from '../../security/service/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerTypeService {
  private _API_URL = 'http://localhost:8080/api/v1/customer-type';


  constructor(private httpClient: HttpClient,
              private tokenStorageService: TokenStorageService) {
  }

  getAllCustomerType(): Observable<CustomerType[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<CustomerType[]>(this._API_URL, {headers});
  }

  findByIdCustomerType(id: number): Observable<CustomerType> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<CustomerType>(this._API_URL + '/' + id, {headers});
  }

}
