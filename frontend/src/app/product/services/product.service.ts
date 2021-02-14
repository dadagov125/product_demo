import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {CreateProductModel, ProductModel} from "../models/product.model";
import {Observable} from "rxjs";
import {SERVER_API_URL} from "../../constants";
import {Pagenable} from "../../common/interfaces/pagenable";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  public apiUrl = SERVER_API_URL + 'api/product';

  constructor(private http: HttpClient) {

  }

  create(model: CreateProductModel): Observable<ProductModel> {
    return this.http.post<ProductModel>(this.apiUrl, model,);
  }

  update(model: ProductModel): Observable<ProductModel> {
    return this.http.put<ProductModel>(this.apiUrl, model,);
  }

  findById(id: number): Observable<ProductModel> {
    return this.http.get<ProductModel>(`${this.apiUrl}/${id}`);
  }

  findByCode(code: string): Observable<ProductModel> {
    return this.http.get<ProductModel>(`${this.apiUrl}/code/${code}`);
  }

  findAll(query?: any): Observable<Pagenable<ProductModel>> {
    let params: HttpParams = new HttpParams();
    if (query) {
      Object.keys(query).forEach(key => {
        params = params.set(key, query[key]);
      });
    }
    return this.http.get<Pagenable<ProductModel>>(`${this.apiUrl}`, {params});
  }

  remove(id: number) {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
