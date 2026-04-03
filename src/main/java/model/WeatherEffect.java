package model;

import enums.WeatherType;

public class WeatherEffect {

    private final WeatherType type;
    private final int moraleModifier;
    private final int coffeeModifier;
    private final int bugModifier;
    private final double temperatureFahrenheit;

    public WeatherEffect(WeatherType type, int moraleModifier, int coffeeModifier, int bugModifier, double temperatureFahrenheit) {
        this.type = type;
        this.moraleModifier = moraleModifier;
        this.coffeeModifier = coffeeModifier;
        this.bugModifier = bugModifier;
        this.temperatureFahrenheit = temperatureFahrenheit;
        }

    public WeatherType getType() {
        return type;
    }

    public int getMoraleModifier() {
        return moraleModifier;
    }

    public int getCoffeeModifier() {
        return coffeeModifier;
    }

    public int getBugModifier() {
        return bugModifier;
    }

    public double getTemperatureFahrenheit() {
        return temperatureFahrenheit;
    }

    @Override
    public String toString() {
        return "Model.WeatherEffect{" +
                "type=" + type +
                ", moraleModifier=" + moraleModifier +
                ", coffeeModifier=" + coffeeModifier +
                ", bugModifier=" + bugModifier +
                ", temperatureFahrenheit=" + temperatureFahrenheit +
                "}";
    }
}
