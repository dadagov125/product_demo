export interface ProductModel extends CreateProductModel {
  id: number;
}

export interface CreateProductModel {
  code: string;
  name: string
  price: number;
  article: string;
  produced: string;
  count: number
}
