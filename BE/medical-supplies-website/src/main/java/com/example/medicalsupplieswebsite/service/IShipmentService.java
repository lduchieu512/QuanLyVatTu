package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.dto.shipmentdto.IShipmentDto;
import com.example.medicalsupplieswebsite.dto.shipmentdto.ShipmentDto;
import com.example.medicalsupplieswebsite.entity.Shipment;

import java.sql.Date;
import java.util.List;

public interface IShipmentService extends IService<Shipment>{
    void addShipment(String invoiceCode, Date dateOfCreate, Long shipmentTypeId, Long customerId, Long employeeId);
    Long findByShipmentIDInvoice(String invoiceCode);

    Shipment findByIdShipmentInvoiceCode(String invoiceCode);
    List<IShipmentDto> findAllShipment();

}
