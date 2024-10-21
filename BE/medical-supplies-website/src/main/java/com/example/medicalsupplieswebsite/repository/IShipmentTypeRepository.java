package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.ShipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IShipmentTypeRepository extends JpaRepository<ShipmentType,Long> {
    @Query(value = "select shipment_type_id, shipment_type_name from shipment_type ", nativeQuery = true)
    List<ShipmentType> findAllShipmentType();
}
