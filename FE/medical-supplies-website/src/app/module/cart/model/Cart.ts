import {Customer} from '../../customer/model/Customer';

export interface Cart {
  cartId?: number;
  receiverName?: string;
  receiverAddress?: string;
  receiverPhone?: string;
  receiverEmail?: string;
}
