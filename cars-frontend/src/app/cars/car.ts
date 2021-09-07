import { Brand } from './brand';

export interface Car {
    id: string;
    model:string;
    brand:Brand;
    number:string;
    created:Date;
    price:number;
    urlImageSet:Set<string>;
}