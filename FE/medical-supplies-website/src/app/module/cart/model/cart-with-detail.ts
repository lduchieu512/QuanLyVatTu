import {Cart} from './Cart';
import {CartDetail} from './CartDetail';

export interface CartWithDetail {
  cart?: Cart;
  cartDetailList?: CartDetail[];
}
