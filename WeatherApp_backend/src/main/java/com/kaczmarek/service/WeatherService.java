package com.kaczmarek.service;

import com.kaczmarek.dto.Weather;

public interface WeatherService {

    public Weather getCurrentWeather(String city, String country);

    public Weather getCurrentWeatherCoordinates(Float longitude, Float latitude);

}
