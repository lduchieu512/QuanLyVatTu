import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {TokenStorageService} from './module/security/service/token-storage.service';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router,
              private tokenStorageService: TokenStorageService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const token = this.tokenStorageService.getToken();
    if (token !== null) {
      return true;
    }
    Swal.fire({
      position: 'center',
      icon: 'info',
      title: 'Bạn phải đăng nhập để sử dụng chức năng này!',
      showConfirmButton: false,
      timer: 1500
    });
    return this.router.createUrlTree(['/login'], {queryParams: {returnUrl: state.url}});
  }
}
