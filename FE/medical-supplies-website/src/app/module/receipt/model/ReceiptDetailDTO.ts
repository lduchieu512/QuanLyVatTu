export class ReceiptDetailDTO {
  public productId: number;
  public quantity: number;

  constructor(productId: number, quantity: number) {
    this.productId = productId;
    this.quantity = quantity;
  }
}
