import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Category} from '../model/Category';
import {TokenStorageService} from '../../security/service/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private _API_URL = 'http://localhost:8080/api/v1/category';

  constructor(private http: HttpClient,
              private tokenStorageService: TokenStorageService) {
  }

  getAll(): Observable<Category[]> {
    const token: string = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<Category[]>(this._API_URL, {headers});
  }
}
