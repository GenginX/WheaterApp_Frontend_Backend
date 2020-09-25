import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {WeatherModel} from '../model/WeatherModel';
import {WeatherService} from '../../service/weather.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements OnInit {
  public weatherForm = new FormGroup({
    country: new FormControl(''),
    city: new FormControl('')
  });

  constructor(private weatherService: WeatherService) {
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const value: WeatherModel = this.weatherForm.value;
    console.log(value);
    return this.weatherService.getWeatherWithDetails(value.city, value.country);
  }

  onClick(): void {
    const location = this.weatherService.getLocation();
    return this.weatherService.getLocation();
  }

}
