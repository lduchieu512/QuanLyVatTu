import {Employee} from '../../employee/model/Employee';
import {Customer} from '../../customer/model/Customer';
import {ReceiptType} from './ReceiptType';

export interface Receipt {
  receiptId?: number;
  invoiceCode?: string;
  dateOfCreate?: Date;
  note?: string;
  employee?: Employee;
  customer?: Customer;
  receiptType?: ReceiptType;
}
