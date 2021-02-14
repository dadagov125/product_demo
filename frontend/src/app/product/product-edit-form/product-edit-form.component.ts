import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {CreateProductModel} from "../models/product.model";
import {NgForm} from "@angular/forms";


@Component({
  selector: 'app-product-edit-form',
  templateUrl: './product-edit-form.component.html',
  styleUrls: ['./product-edit-form.component.scss']
})
export class ProductEditFormComponent implements OnInit, AfterViewInit {
  @Input() product: CreateProductModel;
  @Output() validationStatusChanged = new EventEmitter<boolean>();
  @ViewChild(NgForm) form: NgForm;

  constructor() {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.form.form.statusChanges.subscribe(value => {
      this.validationStatusChanged.emit(value === 'VALID');
    });
    this.form.form.updateValueAndValidity();
  }

}
