import {Brand} from './brand';

export class Car {
  constructor(model: string,
              brand: string, price: number) {
    this.model = model;
    this.brand = new Brand(brand);
    this.price = price;
  }

  id: string;
  model: string;
  brand: Brand;
  number: string;
  created: Date;
  price: number;
  urlImageList: string[];
}
