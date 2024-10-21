import {ReceiptDetailDTO} from './ReceiptDetailDTO';

export interface ReceiptDTO {
  receiptId: number;
  receiptTypeId: number;
  invoiceCode: string;
  dateOfCreate: Date;
  employeeId: number;
  customerId: number;
  receiptDetailDTOS: ReceiptDetailDTO[];
}
