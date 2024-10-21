package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.ShipmentType;

import java.util.List;

public interface IShipmentTypeService extends IService<ShipmentType>{
    List<ShipmentType> findAllShipmentType();
}
