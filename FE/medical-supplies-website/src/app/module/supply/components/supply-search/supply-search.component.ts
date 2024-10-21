import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Supply} from '../../model/Supply';
import {SupplyService} from '../../service/supply.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-supply-search',
  templateUrl: './supply-search.component.html',
  styleUrls: ['./supply-search.component.css']
})
export class SupplySearchComponent implements OnInit {
  supplyForm: FormGroup;
  supplies: Supply[] = [];
  @Output() newItemEvent = new EventEmitter<Supply[]>();
  @Output() keywordSearch = new EventEmitter<string>();
  @Output() totalPageAfterSearch = new EventEmitter<number>();

  constructor(private supplyService: SupplyService) {
    this.supplyForm = new FormGroup({
      productCode: new FormControl(''),
      productName: new FormControl(''),
      categoryName: new FormControl(''),
      customerName: new FormControl(''),
      beginDate: new FormControl(''),
      endDate: new FormControl(''),
    });
  }

  ngOnInit(): void {
    this.supplyForm = new FormGroup({
      productCode: new FormControl(''),
      productName: new FormControl(''),
      categoryName: new FormControl(''),
      customerName: new FormControl(''),
      beginDate: new FormControl(''),
      endDate: new FormControl(''),
    });
  }

  search() {
    let keyword = '?';
    const productCode = this.supplyForm.value.productCode;
    console.log(`productCode: ${productCode}`);
    if (productCode !== '' && productCode != null) {
      keyword += `productCode=${productCode}&`;
    }
    const productName = this.supplyForm.value.productName;
    console.log(`productName: ${productName}`);
    if (productName !== '' && productName != null) {
      keyword += `productName=${productName}&`;
    }
    const categoryName = this.supplyForm.value.categoryName;
    console.log(`categoryName: ${categoryName}`);
    if (categoryName !== '' && categoryName != null) {
      keyword += `categoryName=${categoryName}&`;
    }
    const customerName = this.supplyForm.value.customerName;
    console.log(`customerName: ${customerName}`);
    if (customerName !== '' && customerName != null) {
      keyword += `customerName=${customerName}&`;
    }
    const beginDate = this.supplyForm.value.beginDate;
    console.log(`expireDateStart: ${beginDate}`);
    if (beginDate !== '' && beginDate != null) {
      keyword += `expireDateStart=${beginDate}&`;
    }
    const endDate = this.supplyForm.value.endDate;
    console.log(`expireDateEnd: ${endDate}`);
    if (endDate !== '' && endDate != null) {
      keyword += `expireDateEnd=${endDate}`;
    }
    keyword += `page=1`;
    console.log(`keyword: ${keyword}`);
    this.supplyService.search(keyword).subscribe(next => {
      console.log(next);
      // this.supplyForm.reset();
      if (next != null) {
        this.newItemEvent.emit(next.content);
        this.totalPageAfterSearch.emit(next.totalPages);
      } else {
        this.newItemEvent.emit([]);
        this.totalPageAfterSearch.emit(0);
      }
      this.keywordSearch.emit(keyword);
    });
  }
}
