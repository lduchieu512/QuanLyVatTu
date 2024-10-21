import { Component, OnInit } from '@angular/core';
import {ShipmentService} from '../../service/shipment.service';
import {Product} from '../../../product/model/Product';
import {ProductDto} from '../../model/ProductDto';
import {Router} from '@angular/router';

@Component({
  selector: 'app-shipment-create',
  templateUrl: './shipment-create.component.html',
  styleUrls: ['./shipment-create.component.css']
})
export class ShipmentCreateComponent implements OnInit {
  products: Product[];
  productDtos: ProductDto[];
  p = 1;
  selectAll = false;


  constructor(public shipmentService: ShipmentService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.findAllProductShipmentCreate();
  }

  /* Hiển thị danh sách chọn vật tư*/
  findAllProductShipmentCreate() {
    this.shipmentService.findAllProduct().subscribe(data => {
      this.productDtos = data;
    });
  }

  /*Chọn check box để thêm qua list tạm*/
  chonChekBox(p: ProductDto) {
    if (p.select) {
      this.shipmentService.addShipmentProductDetailDto(p);
    } else {
      this.shipmentService.removeShipmentProductDetailDto(p);
    }
  }

  /*Thêm qua list tạm*/
  create() {
    const shipmentItems = this.shipmentService.getShipmentDetailDto();
    // Sử dụng danh sách tạm shipmentItems ở đây để thực hiện các thao tác bạn cần với các sản phẩm đã chọn
    console.log(shipmentItems);
    // Tiến hành reset lại danh sách tạm
    // this.shipmentService.clearShipmentItems();
    this.router.navigate(['/shipments/shipment']);
  }

  /*Chon tat ca checkbox*/
  selectAllCheckboxes() {
    for (const item of this.productDtos) {
      item.select = this.selectAll;
      if (item.select) {
        this.shipmentService.addShipmentProductDetailDto(item);
      } else {
        this.shipmentService.removeShipmentProductDetailDto(item);
      }
    }
  }
}
