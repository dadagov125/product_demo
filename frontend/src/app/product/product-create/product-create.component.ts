import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {CreateProductModel} from "../models/product.model";

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.scss']
})
export class ProductCreateComponent implements OnInit {
  product: CreateProductModel;
  isFormValid: boolean;

  constructor(private ref: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.product = Object.assign({});
  }

  onVelidation(value: boolean) {
    this.isFormValid = value;
    this.ref.detectChanges();
  }

}
