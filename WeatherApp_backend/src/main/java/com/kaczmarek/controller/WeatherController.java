package com.kaczmarek.controller;

import com.kaczmarek.dto.Weather;
import com.kaczmarek.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WeatherController {

    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}/{country}")
    public ResponseEntity<Weather> getWeather(@PathVariable("city") String city, @PathVariable("country") String country){
        Weather currentWeather = weatherService.getCurrentWeather(city, country);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(currentWeather);
    }

    @GetMapping("coordinates/{latitude}/{longitude}")
    public ResponseEntity<Weather> getWeatherFromCoordinates(@PathVariable("latitude") Float latitude, @PathVariable("longitude") Float longitude){
        Weather currentWeather = weatherService.getCurrentWeatherCoordinates(longitude, latitude);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(currentWeather);
    }

}
