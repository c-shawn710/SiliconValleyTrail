package service;

import constants.WeatherConstants;
import enums.WeatherType;
import model.Location;
import model.WeatherEffect;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {


    private final HttpClient httpClient;

    public WeatherService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public WeatherEffect getWeatherEffect(Location location) {
        try {
            String url = String.format(
                    WeatherConstants.QUERY_TEMPLATE,
                    WeatherConstants.BASE_URL,
                    location.getLatitude(),
                    location.getLongitude()
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != WeatherConstants.STATUS_CODE_OK) {
                return getFallbackWeatherEffect();
            }

            JSONObject root = new JSONObject(response.body());
            JSONObject current = root.getJSONObject("current");

            int weatherCode = current.getInt("weather_code");
            double temperatureCelsius = current.getDouble("temperature_2m");

            double temperatureFahrenheit = convertToFahrenheit(temperatureCelsius);
            WeatherType weatherType = mapToWeatherType(weatherCode,temperatureFahrenheit);
            return mapToWeatherEffect(weatherType, temperatureFahrenheit);

        } catch (IOException | InterruptedException e) {
            return getFallbackWeatherEffect();
        }
    }

    private double convertToFahrenheit(double temperatureC) {
        return (temperatureC * 9.0 / 5.0) + 32.0;
    }

    private WeatherType mapToWeatherType(int weatherCode, double temperatureF) {
        if (temperatureF >= WeatherConstants.HEAT_THRESHOLD_F) {
            return WeatherType.HEAT;
        }

        if (weatherCode >= WeatherConstants.CLEAR_CODE_MIN &&
            weatherCode <= WeatherConstants.CLEAR_CODE_MAX) {
            return WeatherType.CLEAR;
        }

        if (weatherCode == WeatherConstants.FOG_CODE_1 ||
            weatherCode == WeatherConstants.FOG_CODE_2) {
            return WeatherType.FOG;
        }

        if (weatherCode >= WeatherConstants.RAIN_CODE_MIN_1 && weatherCode <= WeatherConstants.RAIN_CODE_MAX_1 ||
            weatherCode >= WeatherConstants.RAIN_CODE_MIN_2 && weatherCode <= WeatherConstants.RAIN_CODE_MAX_2) {
            return WeatherType.RAIN;
        }

        return WeatherType.NEUTRAL;
    }

    private WeatherEffect mapToWeatherEffect(WeatherType weatherType, double temperatureFahrenheit) {
        return switch (weatherType) {
            case CLEAR -> new WeatherEffect(
                    WeatherType.CLEAR,
                    WeatherConstants.CLEAR_MORALE_MOD,
                    WeatherConstants.CLEAR_COFFEE_MOD,
                    WeatherConstants.CLEAR_BUG_MOD,
                    temperatureFahrenheit
            );
            case RAIN -> new WeatherEffect(
                    WeatherType.RAIN,
                    WeatherConstants.RAIN_MORALE_MOD,
                    WeatherConstants.RAIN_COFFEE_MOD,
                    WeatherConstants.RAIN_BUG_MOD,
                    temperatureFahrenheit
            );
            case FOG -> new WeatherEffect(
                    WeatherType.FOG,
                    WeatherConstants.FOG_MORALE_MOD,
                    WeatherConstants.FOG_COFFEE_MOD,
                    WeatherConstants.FOG_BUG_MOD,
                    temperatureFahrenheit
            );
            case HEAT -> new WeatherEffect(
                    WeatherType.HEAT,
                    WeatherConstants.HEAT_MORALE_MOD,
                    WeatherConstants.HEAT_COFFEE_MOD,
                    WeatherConstants.HEAT_BUG_MOD,
                    temperatureFahrenheit
            );
            default -> getFallbackWeatherEffect();
        };
    }

    private WeatherEffect getFallbackWeatherEffect() {
        return new WeatherEffect(
                WeatherType.NEUTRAL,
                WeatherConstants.NEUTRAL_MORALE_MOD,
                WeatherConstants.NEUTRAL_COFFEE_MOD,
                WeatherConstants.NEUTRAL_BUG_MOD,
                WeatherConstants.DEFAULT_TEMPERATURE_F
        );
    }
}
