package com.kaczmarek.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaczmarek.dto.Weather;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URI;

@Service
public class WeatherServiceImp implements WeatherService {

    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}&";
    private static final String WEATHER_COORDINATE_URL = "http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}&";
    private static final String API_KEY = "849991a70858a3937a92d9cfb9164e37";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherServiceImp(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Weather getCurrentWeather(String city, String country) {
        if (parameterValidation(city, country)) {
            URI url = new UriTemplate(WEATHER_URL).expand(city, country, API_KEY);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            return convert(responseEntity);
        }
        throw new RuntimeException("Invalid path variables");

    }

    @Override
    public Weather getCurrentWeatherCoordinates(Float longitude, Float latitude) {
        URI url = new UriTemplate(WEATHER_COORDINATE_URL).expand(latitude,longitude, API_KEY);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        return convert(responseEntity);
    }


    private boolean parameterValidation(String city, String country) {
        return !city.isBlank() && country.length() == 2 && !country.isBlank();
    }

    private Weather convert(ResponseEntity<String> response) {
        BigDecimal kelvin = new BigDecimal("273.15");

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return new Weather(root.path("sys").path("country").asText(),
                    root.path("name").asText(),
                    root.path("weather").get(0).path("main").asText(),
                    BigDecimal.valueOf(root.path("main").path("temp").asDouble()).subtract(kelvin).round(new MathContext(3, RoundingMode.HALF_UP)),
                    BigDecimal.valueOf(root.path("main").path("feels_like").asDouble()).subtract(kelvin).round(new MathContext(3, RoundingMode.HALF_UP)),
                    BigDecimal.valueOf(root.path("wind").path("speed").asDouble()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

}
