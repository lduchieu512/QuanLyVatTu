import {Component, OnInit} from '@angular/core';
import {ProductMain} from '../../model/product-main';
import {HomeService} from '../../service/home.service';

@Component({
  selector: 'app-list-highest',
  templateUrl: './list-highest.component.html',
  styleUrls: ['./list-highest.component.css']
})
export class ListHighestComponent implements OnInit {
  highestProductPriceList: ProductMain[];
  productCounter = 0;

  constructor(private homeService: HomeService) {
    this.getHighestProductPrice();
  }

  ngOnInit(): void {
  }

  private getHighestProductPrice() {
    this.homeService.getProductHighest().subscribe(
      (result) => {
        console.log('check');
        console.log(result);
        this.highestProductPriceList = result;
      },
      (error) => {
        console.error('Error fetching highest product prices:', error);
      }
    );
  }
}
