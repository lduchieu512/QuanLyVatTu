package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.ShipmentDetail;

public interface IShipmentDetailService extends IService<ShipmentDetail>{
    void addNewShipmentDetail(int quantity,String note,Long shipmentId, Long productId);
}
