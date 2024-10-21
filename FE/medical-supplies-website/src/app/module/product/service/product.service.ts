import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../model/Product';
import {TokenStorageService} from '../../security/service/token-storage.service';
import {ProductCreateFormDTO} from "../model/ProductCreateFormDTO";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private _API_URL = 'http://localhost:8080/api/v1/product';
  private products: Product[] = [];

  constructor(private httpClient: HttpClient,
              private tokenStorageService: TokenStorageService) {
  }

  findByIdProductDetail(id: number): Observable<Product> {
    // const token = this.tokenStorageService.getToken();
    // const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<Product>(this._API_URL + '/detail/' + id);
  }

  getCartItems(): Product[] {
    return this.products;
  }

  addToCart(item: Product): void {
    const existingItem = this.products.find((data) => data.productId === item.productId);

    if (existingItem) {
      existingItem.productQuantity += item.productQuantity;
    } else {
      this.products.push(item);
    }
  }

  clearCart(): void {
    this.products = [];
  }
  saveProduct(product: ProductCreateFormDTO): Observable<string> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    product.expireDate = this.converterDate(product.expireDate.toString());
    return this.httpClient.post<string>(`${this._API_URL}`, (product), {headers});
  }

  findById(id: string): Observable<ProductCreateFormDTO> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<ProductCreateFormDTO>(`${this._API_URL}/detail1/${id}`, {headers});
  }


  updateProductId(product: ProductCreateFormDTO): Observable<ProductCreateFormDTO> {
    const token = this.tokenStorageService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.patch<ProductCreateFormDTO>(`${this._API_URL}/update`, product, {headers});
  }

  converterDate(date: string): string {
    const dateInfo = date.replace('/', '-');
    return dateInfo.replace('/', '-');
  }
}
