import {Component, OnInit} from '@angular/core';
import {ProductService} from "../services/product.service";
import {ActivatedRoute} from "@angular/router";
import {mergeMap} from "rxjs/operators";
import {Observable} from "rxjs";
import {ProductModel} from "../models/product.model";

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss']
})
export class ProductDetailComponent implements OnInit {

  product: Observable<ProductModel>;

  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.product = this.activatedRoute.params.pipe(mergeMap(value => {
      let code = value.code;
      return this.productService.findByCode(code);
    }));
  }

}
