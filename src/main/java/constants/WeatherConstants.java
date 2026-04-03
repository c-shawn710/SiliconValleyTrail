package constants;

public class WeatherConstants {

    private WeatherConstants() {}

    // API Config
    public static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    public static final String QUERY_TEMPLATE = "%s?latitude=%f&longitude=%f&current=temperature_2m,weather_code";

    // Temperature
    public static final double HEAT_THRESHOLD_F = 85.0;
    public static final double DEFAULT_TEMPERATURE_F = 70.0;

    // Weather codes
    public static final int CLEAR_CODE_MIN = 0;
    public static final int CLEAR_CODE_MAX = 3;

    public static final int FOG_CODE_1 = 45;
    public static final int FOG_CODE_2 = 48;

    public static final int RAIN_CODE_MIN_1 = 51;
    public static final int RAIN_CODE_MAX_1 = 67;

    public static final int RAIN_CODE_MIN_2 = 80;
    public static final int RAIN_CODE_MAX_2 = 82;

    // Gameplay Effects
    public static final int CLEAR_MORALE_MOD = 5;
    public static final int CLEAR_COFFEE_MOD = 0;
    public static final int CLEAR_BUG_MOD = 0;

    public static final int RAIN_MORALE_MOD = -5;
    public static final int RAIN_COFFEE_MOD = 0;
    public static final int RAIN_BUG_MOD = 1;

    public static final int FOG_MORALE_MOD = -2;
    public static final int FOG_COFFEE_MOD = 0;
    public static final int FOG_BUG_MOD = 0;

    public static final int HEAT_MORALE_MOD = -3;
    public static final int HEAT_COFFEE_MOD = -3;
    public static final int HEAT_BUG_MOD = 0;

    public static final int NEUTRAL_MORALE_MOD = 0;
    public static final int NEUTRAL_COFFEE_MOD = 0;
    public static final int NEUTRAL_BUG_MOD = 0;
}
