import {AfterViewInit, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ProductService} from "../services/product.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {CreateProductModel, ProductModel} from "../models/product.model";
import {MatSort} from "@angular/material/sort";
import {Subscription} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {ProductDeleteConfirmationComponent} from "../product-delete-confirmation/product-delete-confirmation.component";
import {filter, switchMap} from "rxjs/operators";
import {ProductEditComponent} from "../product-edit/product-edit.component";
import {ProductCreateComponent} from "../product-create/product-create.component";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  displayedColumns: string[] = ['code', 'name', 'price', 'article', 'produced', 'count', 'actions'];
  dataSource = new MatTableDataSource<ProductModel>([]);
  totalCount: number = 0;
  subscriptions: Subscription[] = [];

  constructor(private productService: ProductService, private changeDetectorRef: ChangeDetectorRef, private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;

    this.fetchProducts();

    this.subscriptions.push(this.sort.sortChange.subscribe(() => this.fetchProducts()));

    this.subscriptions.push(
      this.paginator.page.subscribe(() => this.fetchProducts()));

  }

  fetchProducts() {
    const query = {
      sort: `${this.sort.active},${this.sort.direction}`,
      page: this.paginator.pageIndex,
      size: this.paginator.pageSize
    };

    this.productService.findAll(query).subscribe(value => {
      this.dataSource.data = value.content;
      this.totalCount = value.totalElements;
      this.changeDetectorRef.detectChanges();
    });
  }


  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe())
  }

  tryDelete(id: number, e: MouseEvent) {
    e.stopPropagation();
    this.dialog.open(ProductDeleteConfirmationComponent)
      .afterClosed().pipe(
      filter(value => value),
      switchMap(() => this.productService.remove(id))
    )
      .subscribe(() => this.fetchProducts());

  }

  tryEdit(product: ProductModel, e: MouseEvent) {
    e.stopPropagation();
    this.dialog.open(ProductEditComponent, {
      data: product
    })
      .afterClosed().pipe(
      filter(value => value),
      switchMap(value => this.productService.update(value)),
    )
      .subscribe(() => this.fetchProducts());
  }

  tryCreate() {
    this.dialog.open<ProductCreateComponent, any, CreateProductModel>(ProductCreateComponent)
      .afterClosed().pipe(
      filter(value => !!value),
      switchMap(value => this.productService.create(value)),
    )
      .subscribe(() => this.fetchProducts());
  }
}
