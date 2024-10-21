import {Employee} from './../model/Employee';
import {Injectable} from '@angular/core';
import {EmployeeUserDetail} from '../model/EmployeeUserDetail';
import {TokenStorageService} from '../../security/service/token-storage.service';
import {EmployeeInfo} from '../model/EmployeeInfo';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ChangePasswordDto} from '../model/ChangePasswordDto';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private _API_URL = 'http://localhost:8080/api/v1/employee';
  private _API_URL_ACCOUNT_CHANGE_PASSWORD = 'http://localhost:8080/api/v1/account/change-password';
  private _API_URL_EMPLOYEE = 'http://localhost:8080/api/v1/employee';


  constructor(private http: HttpClient,
              private tokenStorageService: TokenStorageService) {
  }

  getEmployeeWithNameAndDobAndPos(name: string, dateOfBirth: string, positionName: string): Observable<Employee[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    // @ts-ignore
    return this.http.get(this._API_URL + '?name=' + name + '&date=' + dateOfBirth + '&pos=' + positionName, {headers});
  }

  getAllEmployee(): Observable<Employee[]> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Employee[]>('http://localhost:8080/api/v1/employee/getAll', {headers});
  }

  public getUserDetail(): Observable<HttpResponse<EmployeeUserDetail>> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<EmployeeUserDetail>(this._API_URL + '/detail', {headers, observe: 'response'});
  }

  // @ts-ignore
  getEmployeeById(id: number): Observable<Employee> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(this._API_URL + '/' + id, {headers});
  }

  deleteByID(id: number): Observable<any> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete(this._API_URL + '/delete/' + id, {headers});
  }

  // @ts-ignore
  getAllPos(): Observable<any> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get('http://localhost:8080/api/v1/position', {headers});
  }

  saveEmployee(employeeInfo: EmployeeInfo): Observable<EmployeeInfo> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<EmployeeInfo>('http://localhost:8080/api/v1/employee', employeeInfo, {headers});
  }

  updateEmployee(employeeInfo: EmployeeInfo, id: number): Observable<any> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put<EmployeeInfo>('http://localhost:8080/api/v1/employee/' + id, employeeInfo, {headers});
  }

  /**
   * NhanTQ
   *
   */
  updateEmployeeDetail(employee: Employee): Observable<any> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.patch<Employee>(this._API_URL_EMPLOYEE + '/' + 'update-employee', employee, {headers});
  }

  findByEmployeeEqualUsername(): Observable<Employee> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Employee>(this._API_URL_EMPLOYEE + '/' + 'user-detail-update', {headers});
  }

  changePassword(changePasswordDto: ChangePasswordDto): Observable<any> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.patch<Account>(this._API_URL_ACCOUNT_CHANGE_PASSWORD, changePasswordDto, {headers});
  }

  /**
   * NhanTQ
   *
   */
}
