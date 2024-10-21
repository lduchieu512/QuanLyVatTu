import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {
  @Input()
  totalPages = 1;
  listOfPage: number[] = [];
  currentPage = 1;
  @Output()
  emitCurrentPage: EventEmitter<number> = new EventEmitter<number>();

  ngOnInit(): void {
    this.emitCurrentPage.emit(this.currentPage);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.totalPages) {
      this.setPageInfo();
      this.currentPage = 1;
      this.emitCurrentPage.emit(this.currentPage);
    }
  }

  private setPageInfo() {
    this.listOfPage = [];
    for (let i = 1; i <= this.totalPages; i++) {
      this.listOfPage.push(i);
    }
  }

  public nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.emitCurrentPage.emit(this.currentPage);
    }
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.emitCurrentPage.emit(this.currentPage);
    }
  }

  accessPage(page: number) {
    this.currentPage = page;
    this.emitCurrentPage.emit(this.currentPage);
  }
}
