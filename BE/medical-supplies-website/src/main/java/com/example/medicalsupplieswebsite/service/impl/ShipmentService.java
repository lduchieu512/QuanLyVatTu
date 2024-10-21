package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.dto.shipmentdto.IShipmentDto;
import com.example.medicalsupplieswebsite.dto.shipmentdto.ShipmentDto;
import com.example.medicalsupplieswebsite.entity.Shipment;
import com.example.medicalsupplieswebsite.entity.ShipmentDetail;
import com.example.medicalsupplieswebsite.repository.IShipmentRepository;
import com.example.medicalsupplieswebsite.service.IService;
import com.example.medicalsupplieswebsite.service.IShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ShipmentService implements IShipmentService {
    @Autowired
    IShipmentRepository shipmentRepository;


    @Override
    public Page<Shipment> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Shipment findById(Long id) {
        return null;
    }

    @Override
    public Shipment update(Shipment shipment) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void addShipment(String invoiceCode, Date dateOfCreate, Long shipmentTypeId, Long customerId, Long employeeId) {
        shipmentRepository.addShipment(invoiceCode,dateOfCreate,shipmentTypeId,customerId,employeeId);
    }

    @Override
    public Long findByShipmentIDInvoice(String invoiceCode) {
        return shipmentRepository.findByShipmentIDInvoice(invoiceCode);
    }

    @Override
    public Shipment findByIdShipmentInvoiceCode(String invoiceCode) {
        return shipmentRepository.findByIdShipmentInvoiceCode(invoiceCode);
    }

    @Override
    public List<IShipmentDto> findAllShipment() {
        return shipmentRepository.findAllShipment();
    }

}
