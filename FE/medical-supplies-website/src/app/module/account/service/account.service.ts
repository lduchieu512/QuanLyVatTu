import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenStorageService} from '../../security/service/token-storage.service';
import {Account} from '../model/Account';
import {Role} from '../model/Role';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private _API_URL = 'http://localhost:8080/api/v1/account';

  constructor(private httpClient: HttpClient,
              private tokenStorageService: TokenStorageService) {
  }

  addAccount(account: Account, roleId: number): Observable<Account> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `${this._API_URL}/addAccount?roleId=${roleId}`;
    return this.httpClient.post<Account>(url, account, {headers});
  }

  getRoles(): Observable<Role[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `${this._API_URL}/roles`;
    return this.httpClient.get<Role[]>(url, {headers});
  }

  checkExistingUsername(username: string): Observable<boolean> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `${this._API_URL}/checkExistingUsername?username=${username}`;
    return this.httpClient.get<boolean>(url, {headers});
  }

  checkExistingEmail(email: string): Observable<boolean> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `${this._API_URL}/checkExistingEmail?email=${email}`;
    return this.httpClient.get<boolean>(url, {headers});
  }

}
