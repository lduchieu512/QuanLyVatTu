package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.ShipmentType;
import com.example.medicalsupplieswebsite.repository.IShipmentTypeRepository;
import com.example.medicalsupplieswebsite.service.IService;
import com.example.medicalsupplieswebsite.service.IShipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentTypeService implements IShipmentTypeService {
    @Autowired
    private IShipmentTypeRepository shipmentTypeRepository;


    @Override
    public Page<ShipmentType> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ShipmentType findById(Long id) {
        return null;
    }

    @Override
    public ShipmentType update(ShipmentType shipmentType) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ShipmentType> findAllShipmentType() {
        return shipmentTypeRepository.findAllShipmentType();
    }
}
