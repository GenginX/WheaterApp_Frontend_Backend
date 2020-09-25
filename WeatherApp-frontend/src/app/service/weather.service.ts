import {Injectable} from '@angular/core';

import {environment} from '../../environments/environment';

const list: Array<number> = new Array<number>();

@Injectable({
  providedIn: 'root'
})
export class WeatherService {

  constructor() {
  }

  public getWeatherWithDetails(city: string, country: string): void {
    window.open(`${environment.api_base}${city}/${country}`);
  }




  // @ts-ignore
  public getLocation(): void {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(resp => {
        const longitude = resp.coords.longitude;
        const latitude = resp.coords.latitude;
        window.open(`${environment.api_base}coordinates/${latitude}/${longitude}`);
        }
      );
    }
  }
}
