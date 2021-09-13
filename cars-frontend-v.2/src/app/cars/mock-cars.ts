import { Car } from "./car";

export const CARS:Car[]=[
    { id:1,
      model:"X5",
      brand:{brandName:"BMW", countryOrigin:"Germany", created:new Date()},
      number:"xx0000xx",
      created:new Date(),
      price:30000,
      urlImageSet:new Set<string>().add("/cars/bmw/x5/bmw-x5-1.jpg")
    },
    { id:1,
        model:"X6",
        brand:{brandName:"BMW", countryOrigin:"Germany", created:new Date()},
        number:"xx0000xx",
        created:new Date(),
        price:35000,
        urlImageSet:new Set<string>().add("/cars/bmw/x5/bmw-x6-1.jpg")
      },
      { id:1,
        model:"M3",
        brand:{brandName:"BMW", countryOrigin:"Germany", created:new Date()},
        number:"xx0000xx",
        created:new Date(),
        price:25000,
        urlImageSet:new Set<string>().add("/cars/bmw/x5/bmw-m3-1.jpg")
      }

];

