import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryHomeService {
  private API_CATE_MAIN = 'http://localhost:8080/api/v1/category';

  constructor(private httpClient: HttpClient) {
  }

  getCategories(): Observable<any> {
    return this.httpClient.get<any>(this.API_CATE_MAIN);
  }
}

