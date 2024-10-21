package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.ShipmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IShipmentDetailRepository extends JpaRepository<ShipmentDetail,Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into shipment_detail(quantity,note,shipment_id,product_id) values (?1,?2,?3,?4)", nativeQuery = true)
    void addNewShipmentDetail(int quantity,String note,Long shipmentId, Long productId);
}
