import {Cart} from './Cart';
import {Product} from '../../product/model/Product';

export interface CartDetail {
  cartDetailId?: number;
  quantity?: number;
  status?: boolean;
  cartId?: number;
  product?: Product;
}
