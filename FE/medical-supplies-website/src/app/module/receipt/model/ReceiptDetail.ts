import {Product} from '../../product/model/Product';
import {Receipt} from './Receipt';

export interface ReceiptDetail {
  receiptDetailId?: number;
  quantity?: number;
  product?: Product;
  receipt?: Receipt;
}
