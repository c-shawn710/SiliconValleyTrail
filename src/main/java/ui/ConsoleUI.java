package ui;

import constants.GameConstants;
import enums.ActionType;
import enums.PostGameOption;
import model.*;

import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public int promptMainMenuChoice() {
        System.out.println("""
                ========================================
                SILICON VALLEY TRAIL - Main Menu
                ========================================
                1. New Game
                2. Load Game
                3. Delete save file
                4. Quit
                """);

        return readIntInRange(1, 4, "Invalid Choice, try again.");
    }

    public void printWelcomeInstructions() {
        System.out.println("""
                ========================================
                Welcome to Silicon Valley Trail!
                
                Your goal is to guide your startup team from
                San Jose to San Francisco.
                
                You win by:
                - Reaching San Francisco
                
                You lose if:
                - Cash reaches $0
                - Morale reaches 0/100
                - Coffee stays 0/50 for 2 consecutive days
                - Bugs reach 10/10
                
                Each day, choose carefully.
                Traveling triggers special events.""");
    }

    public void printState(GameState state, Location currentLocation, WeatherEffect weatherEffect) {
        System.out.println("========================================");
        System.out.println("Day " + state.getDay() + " | " + currentLocation);
        System.out.println("Cash: $" + String.format("%,d", state.getCash()));
        System.out.println("Morale: " + state.getMorale() + "/" + GameConstants.MAX_MORALE);
        System.out.println("Coffee: " + state.getCoffee() + "/" + GameConstants.MAX_COFFEE);
        System.out.println("Bugs: " + state.getBugs() + "/" + GameConstants.MAX_BUGS);
        System.out.println("Progress: " + getProgressPercent(state) + "% to San Francisco");
        System.out.println("Weather: " + formatWeatherSummary(weatherEffect) + "°F");
    }

    public ActionType promptPlayerAction() {
        System.out.println("\nWhat will you do?");
        System.out.println("1. Travel to next location");
        System.out.println("2. Rest and recover (restore morale, use coffee)");
        System.out.println("3. Work on Product (reduce bugs, use coffee)");
        System.out.println("4. Save game");
        System.out.println("5. Quit to menu");

        int choice = readIntInRange(1, 5, "Invalid choice, try again.");

        return switch (choice) {
            case 1 -> ActionType.TRAVEL;
            case 2 -> ActionType.REST;
            case 3 -> ActionType.WORK_ON_PRODUCT;
            case 4 -> ActionType.SAVE;
            case 5 -> ActionType.QUIT;
            default -> throw new IllegalStateException("Unexpected action choice: " + choice);
        };
    }

    public EventChoice promptEventChoice(Event event) {
        System.out.println("----------------------------------------");
        System.out.println("Event: " + event.getTitle());
        System.out.println(event.getDescription());
        System.out.println("----------------------------------------");

        System.out.println("1. " + event.getChoiceOne().getDescription()
                + " (" + formatChoiceEffects(event.getChoiceOne()) + ")");
        System.out.println("2. " + event.getChoiceTwo().getDescription()
                + " (" + formatChoiceEffects(event.getChoiceTwo()) + ")");

        int choice = readIntInRange(1, 2, "Invalid choice, try again.");

        return (choice == 1) ? event.getChoiceOne() : event.getChoiceTwo();
    }

    public boolean confirmDeleteSave() {
        System.out.println("""
                Are you sure you want to delete this save file?
                1. Yes
                2. No""");

        int choice = readIntInRange(1, 2, "Invalid choice, try again.");
        return choice == 1;
    }

    public PostGameOption promptWinMenu() {
        System.out.println("""
                1. New Game
                2. Back to Main menu
                3. Quit
                """);

        int choice = readIntInRange(1, 3, "Invalid choice, try again");

        return switch (choice) {
            case 1 -> PostGameOption.NEW_GAME;
            case 2 -> PostGameOption.MAIN_MENU;
            case 3 -> PostGameOption.QUIT;
            default -> throw new IllegalStateException("Unexpected post-game choice: " + choice);
        };
    }

    public void printWinMessage() {
        System.out.println("Your startup survived the Silicon Valley Trail! You win!");
        System.out.println("========================================");
    }

    public void printLossMessage() {
        System.out.println("""
                Your startup failed. Game over.
                Returning to main menu...
                ========================================
                """);
    }

    private int readIntInRange(int min, int max, String errorMessage) {
        while (true) {
            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println(errorMessage);
                continue;
            }

            int value = scanner.nextInt();
            scanner.nextLine();

            if (value >= min && value <= max) {
                return value;
            }

            System.out.println(errorMessage);
        }
    }

    private int getProgressPercent(GameState state) {
        return state.getCurrentLocationIndex() * GameConstants.MAP_PROGRESS_MULTIPLIER;
    }

    public String formatWeatherSummary(WeatherEffect weatherEffect) {
        return weatherEffect.getType() + ", " + Math.round(weatherEffect.getTemperatureFahrenheit());
    }

    private String formatChoiceEffects(EventChoice choice) {
        StringBuilder effects = new StringBuilder();

        appendEffect(effects, "Cash", choice.getCashChange());
        appendEffect(effects, "Morale", choice.getMoraleChange());
        appendEffect(effects, "Coffee", choice.getCoffeeChange());
        appendEffect(effects, "Bugs", choice.getBugsChange());

        if (effects.isEmpty()) {
            return "No change";
        }

        return effects.toString();
    }

    private void appendEffect(StringBuilder builder, String label, int value) {
        if (value == 0) {
            return;
        }

        if (!builder.isEmpty()) {
            builder.append(", ");
        }

        if (value > 0) {
            builder.append(label).append(" +").append(value);
        } else {
            builder.append(label).append(" ").append(value);
        }
    }
}
