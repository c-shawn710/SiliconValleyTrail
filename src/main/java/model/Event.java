package model;

import enums.WeatherType;

public class Event {

    private final String title;
    private final String description;
    private final EventChoice choiceOne;
    private final EventChoice choiceTwo;
    private final WeatherType requiredWeatherType;

    public Event(String title, String description, EventChoice choiceOne, EventChoice choiceTwo) {
        this(title, description, choiceOne, choiceTwo, null);
    }

    public Event(String title, String description, EventChoice choiceOne, EventChoice choiceTwo, WeatherType requiredWeatherType) {
        this.title = title;
        this.description = description;
        this.choiceOne = choiceOne;
        this.choiceTwo = choiceTwo;
        this.requiredWeatherType = requiredWeatherType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public EventChoice getChoiceOne() {
        return choiceOne;
    }

    public EventChoice getChoiceTwo() {
        return choiceTwo;
    }

    public boolean isWeatherEligible(WeatherType currentWeatherType) {
        return requiredWeatherType == currentWeatherType;
    }
}
