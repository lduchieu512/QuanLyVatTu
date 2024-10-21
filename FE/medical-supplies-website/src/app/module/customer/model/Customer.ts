import {Account} from '../../account/model/Account';
import {CustomerType} from './CustomerType';
import {Cart} from '../../cart/model/Cart';

export interface Customer {
  customerId?: number;
  customerCode?: string;
  name?: string;
  phone?: string;
  gender?: boolean;
  dateOfBirth?: Date;
  idCard?: string;
  email?: string;
  customerAddress?: string;
  customerImg?: string;
  customerType?: CustomerType;
  account?: Account;
  cart?: Cart;
  enable?: boolean;
}
