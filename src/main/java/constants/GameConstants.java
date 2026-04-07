package constants;

public class GameConstants {

    private GameConstants() {}

    // Starting values
    public static final int STARTING_DAY = 1;
    public static final int STARTING_LOCATION_INDEX = 0;
    public static final int STARTING_CASH = 50000;
    public static final int STARTING_MORALE = 100;
    public static final int STARTING_COFFEE = 50;
    public static final int STARTING_BUGS = 1;
    public static final int STARTING_CONSECUTIVE_DAYS_WITHOUT_COFFEE = 0;

    // Stat bounds
    public static final int MIN_CASH = 0;

    public static final int MIN_MORALE = 0;
    public static final int MAX_MORALE = 100;

    public static final int MIN_COFFEE = 0;
    public static final int MAX_COFFEE = 50;

    public static final int MIN_BUGS = 0;
    public static final int MAX_BUGS = 10;

    public static final int MAX_CONSECUTIVE_DAYS_WITHOUT_COFFEE = 2;

    public static final int MAP_PROGRESS_MULTIPLIER = 10;

    // Default action values
    public static final int REST_MORALE_MOD = 10;
    public static final int REST_COFFEE_MOD = -3;

    public static final int WORK_ON_PRODUCT_MORALE_MOD = -3;
    public static final int WORK_ON_PRODUCT_COFFEE_MOD = -5;
    public static final int WORK_ON_PRODUCT_BUG_MOD = -2;

    // Save file stat indexes
    public static final int DAY_INDEX = 0;
    public static final int LOCATION_INDEX = 1;
    public static final int CASH_INDEX = 2;
    public static final int MORALE_INDEX = 3;
    public static final int COFFEE_INDEX = 4;
    public static final int BUG_INDEX = 5;
    public static final int CONSECUTIVE_DAYS_WITHOUT_COFFEE_INDEX = 6;

    // Number of stats to display
    public static final int NUMBER_OF_STATS = 7;

    // Convert stats to score
    public static final int CASH_SCORE = 15;
    public static final int MORALE_SCORE = 10;
    public static final int COFFEE_SCORE = 5;
    public static final int BUGS_SCORE = -5;
    public static final int DAY_SCORE = -2;
}
