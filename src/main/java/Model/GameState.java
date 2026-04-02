package Model;

import Constants.GameConstants;

public class GameState {

    private int day;
    private int currentLocationIndex;

    private int cash;
    private int morale;
    private int coffee;
    private int bugs;

    private int consecutiveDaysWithoutCoffee;

    private boolean gameOver;
    private boolean gameWon;

    public GameState() {
        day = GameConstants.STARTING_DAY;
        currentLocationIndex = GameConstants.STARTING_LOCATION_INDEX;
        consecutiveDaysWithoutCoffee = GameConstants.STARTING_CONSECUTIVE_DAYS_WITHOUT_COFFEE;
        gameOver = false;
        gameWon = false;

        cash = clampCash(GameConstants.STARTING_CASH);
        morale = clampMorale(GameConstants.STARTING_MORALE);
        coffee = clampCoffee(GameConstants.STARTING_COFFEE);
        bugs = clampBugs(GameConstants.STARTING_BUGS);
    }

    public GameState(int day,
                     int currentLocationIndex,
                     int cash,
                     int morale,
                     int coffee,
                     int bugs,
                     int consecutiveDaysWithoutCoffee,
                     boolean gameOver,
                     boolean gameWon) {
        this.day = day;
        this.currentLocationIndex = currentLocationIndex;
        this.cash = clampCash(cash);
        this.morale = clampMorale(morale);
        this.coffee = clampCoffee(coffee);
        this.bugs = clampBugs(bugs);
        this.consecutiveDaysWithoutCoffee = consecutiveDaysWithoutCoffee;
        this.gameOver = gameOver;
        this.gameWon = gameWon;
    }

    public int getDay() {
        return day;
    }

    public int getCurrentLocationIndex() {
        return currentLocationIndex;
    }

    public int getCash() {
        return cash;
    }

    public int getMorale() {
        return morale;
    }

    public int getCoffee() {
        return coffee;
    }

    public int getBugs() {
        return bugs;
    }

    public int getConsecutiveDaysWithoutCoffee() {
        return consecutiveDaysWithoutCoffee;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void incrementDay() {
        day++;
    }

    public void advanceLocation() {
        currentLocationIndex++;
    }

    public void adjustCash(int amount) {
        cash = clampCash(cash + amount);
    }

    public void adjustMorale(int amount) {
        morale = clampMorale(morale + amount);
    }

    public void adjustCoffee(int amount) {
        coffee = clampCoffee(coffee + amount);
    }

    public void adjustBugs(int amount) {
        bugs = clampBugs(bugs + amount);
    }

    public void incrementConsecutiveDaysWithoutCoffee() {
        consecutiveDaysWithoutCoffee++;
    }

    public void resetConsecutiveDaysWithoutCoffee() {
        consecutiveDaysWithoutCoffee = GameConstants.STARTING_CONSECUTIVE_DAYS_WITHOUT_COFFEE;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    private int clampCash(int value) {
        return Math.max(GameConstants.MIN_CASH, value);
    }

    private int clampMorale(int value) {
        return Math.max(GameConstants.MIN_MORALE,
                Math.min(value, GameConstants.MAX_MORALE));
    }

    private int clampCoffee(int value) {
        return Math.max(GameConstants.MIN_COFFEE,
                Math.min(value, GameConstants.MAX_COFFEE));
    }

    private int clampBugs(int value) {
        return Math.max(GameConstants.MIN_BUGS,
                Math.min(value, GameConstants.MAX_BUGS));
    }

    @Override
    public String toString() {
        return "GameState{" +
                "day=" + day +
                ", currentLocationIndex=" + currentLocationIndex +
                ", cash=" + cash +
                ", morale=" + morale +
                ", coffee=" + coffee +
                ", bugs=" + bugs +
                ", consecutiveDaysWithoutCoffee=" + consecutiveDaysWithoutCoffee +
                ", gameOver=" + gameOver +
                ", gameWon=" + gameWon +
                "}";
    }
}
