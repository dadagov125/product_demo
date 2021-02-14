export interface Pagenable<T> {

  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
}
