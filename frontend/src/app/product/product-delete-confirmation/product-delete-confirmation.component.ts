import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-product-delete-confirmation',
  templateUrl: './product-delete-confirmation.component.html',
  styleUrls: ['./product-delete-confirmation.component.scss']
})
export class ProductDeleteConfirmationComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ProductDeleteConfirmationComponent>) {
  }

  ngOnInit(): void {

  }

}
