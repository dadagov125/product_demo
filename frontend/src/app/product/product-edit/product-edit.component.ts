import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProductModel} from "../models/product.model";
import *as moment from 'moment';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.scss']
})
export class ProductEditComponent implements OnInit {
  product: ProductModel;
  isFormValid: boolean;

  constructor(public dialogRef: MatDialogRef<ProductEditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ProductModel,
              private ref: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.product = Object.assign({}, this.data, {produced: moment(this.data.produced).toDate()});
  }


  onVelidation(value: boolean) {
    this.isFormValid = value;
    this.ref.detectChanges();
  }
}
