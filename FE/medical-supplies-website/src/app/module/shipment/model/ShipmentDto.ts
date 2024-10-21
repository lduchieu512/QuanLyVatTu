import {ShipmentDetailDto} from './ShipmentDetailDto';

export interface ShipmentDto {
  shipmentId?: number;
  invoiceCode?: string;
  employeeId?: number;
  customerId?: number;
  shipmentTypeId?: number;
  listShipmentDetailDtos?: ShipmentDetailDto[];
  phone?: string;
}
