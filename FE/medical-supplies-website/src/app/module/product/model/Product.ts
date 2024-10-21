import {Customer} from '../../customer/model/Customer';
import {Category} from './Category';
import {ProductInfo} from './ProductInfo';

export interface Product {
  productId?: number;
  productCode?: string;
  productName?: string;
  productPrice?: number;
  productQuantity?: number;
  productImg?: string;
  expireDate?: Date;
  customer?: Customer;
  category?: Category;
  productInfo?: ProductInfo;
  enable?: boolean;
}
