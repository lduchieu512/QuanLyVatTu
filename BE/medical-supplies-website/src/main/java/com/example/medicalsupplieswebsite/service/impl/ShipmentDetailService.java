package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.Role;
import com.example.medicalsupplieswebsite.entity.ShipmentDetail;
import com.example.medicalsupplieswebsite.repository.IShipmentDetailRepository;
import com.example.medicalsupplieswebsite.service.IService;
import com.example.medicalsupplieswebsite.service.IShipmentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShipmentDetailService implements IShipmentDetailService {
    @Autowired
    IShipmentDetailRepository shipmentDetailRepository;

    @Override
    public Page<ShipmentDetail> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ShipmentDetail findById(Long id) {
        return null;
    }

    @Override
    public ShipmentDetail update(ShipmentDetail shipmentDetail) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public void addNewShipmentDetail(int quantity,String note, Long shipmentId, Long productId) {
        shipmentDetailRepository.addNewShipmentDetail(quantity,note,shipmentId,productId);
    }
}
