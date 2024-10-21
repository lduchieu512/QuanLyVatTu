import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ProductMain} from '../model/product-main';


@Injectable({
  providedIn: 'root'
})
export class HomeService {
  private API_PRO_MAIN = 'http://localhost:8080/api/v1/home';

  constructor(private httpClient: HttpClient) {
  }

  findAll(categoryId?: number, productName?: string, page?: number, size?: number): Observable<any> {
    if (categoryId === undefined) {
      categoryId = 0;
    }

    if (productName === undefined) {
      productName = '';
    }

    if (page === undefined) {
      page = 1;
    }

    if (size === undefined) {
      size = 10;
    }

    const url = `${this.API_PRO_MAIN}?categoryId=${categoryId}&productName=${productName}&page=${page}&size=${size}`;
    return this.httpClient.get<any>(url);
  }

  getProductHighest(): Observable<any> {
    return this.httpClient.get<any>(this.API_PRO_MAIN + '/highest');
  }
}
