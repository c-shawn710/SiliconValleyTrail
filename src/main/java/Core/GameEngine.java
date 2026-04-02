package Core;

import Constants.GameConstants;
import Enums.ActionType;
import Enums.GameSessionResult;
import Enums.PostGameOption;
import Model.*;
import Service.*;

import java.util.Scanner;

public class GameEngine {

    private final EventResolver eventResolver;
    private final GameRules gameRules;
    private final Route route;
    private final SaveService saveService;
    private final Scanner scanner;
    private final WeatherService weatherService;

    public GameEngine() {
        this.eventResolver = new EventResolver();
        this.gameRules = new GameRules();
        this.route = new Route();
        this.saveService = new SaveService();
        this.scanner = new Scanner(System.in);
        this.weatherService = new WeatherService();
    }

    public void startApp() {
        boolean running = true;

        while (running) {
            printMainMenu();

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    startNewGame();
                    break;

                case 2:
                    loadGameFromMenu();
                    break;

                case 3:
                    handleDeleteSave();
                    break;

                case 4:
                    running = false;
                    System.out.println("\nGoodbye.");
                    break;

                default:
                    System.out.println("\nInvalid choice, try again.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("""
                ========================================
                SILICON VALLEY TRAIL - Main Menu
                ========================================
                1. New Game
                2. Load Game
                3. Delete save file
                4. Quit
                """);
    }

    private void startNewGame() {
        GameState state = new GameState();
        printWelcomeInstructions();
        runGameLoop(state);
    }

    private void loadGameFromMenu() {
        try {
            GameState loadedState = saveService.loadGame();

            if (loadedState == null) {
                System.out.println("\nNo save file found.");
            } else {
                startLoadedGame(loadedState);
            }
        } catch (Exception e) {
            System.out.println("\nFailed to load game: " + e.getMessage());
        }
    }

    public void startLoadedGame(GameState state) {
        runGameLoop(state);
    }

    private void runGameLoop(GameState state) {
        while (!state.isGameOver()) {
            Location currentLocation = route.getCurrentLocation(state);
            WeatherEffect currentWeather = weatherService.getWeatherEffect(currentLocation);

            printState(state, currentLocation, currentWeather);

            ActionType action = promptPlayerAction();
            GameSessionResult result = handleAction(action, state);

            if (result == GameSessionResult.RETURNED_TO_MENU) {
                return;
            }

            gameRules.evaluateGameStatus(state, route);
        }

        if (state.isGameWon()) {
            handleWinFlow();
        } else {
            printLossMessage();
        }
    }

    private void handleDeleteSave() {
        if (!saveService.saveExists()) {
            System.out.println("\nNo save file found.");
            return;
        }

        boolean confirmed = confirmDeleteSave();

        if (!confirmed) {
            System.out.println("\nDelete save file cancelled.");
            return;
        }

        boolean deleted = saveService.deleteSave();

        if (deleted) {
            System.out.println("\nSave file deleted successfully.");
        } else {
            System.out.println("\nFailed to delete save file.");
        }
    }

    private void printState(GameState state, Location currentLocation, WeatherEffect weatherEffect) {
        System.out.println("========================================");
        System.out.println("Day " + state.getDay() + " | " + currentLocation);
        System.out.println("Cash: $" + String.format("%,d", state.getCash()));
        System.out.println("Morale: " + state.getMorale() + "/" + GameConstants.MAX_MORALE);
        System.out.println("Coffee: " + state.getCoffee() + "/" + GameConstants.MAX_COFFEE);
        System.out.println("Bugs: " + state.getBugs() + "/" + GameConstants.MAX_BUGS);
        System.out.println("Progress: " + getProgressPercent(state) + "% to San Francisco");
        System.out.println("Weather: " + formatWeatherSummary(weatherEffect) + "°F");
    }

    private ActionType promptPlayerAction() {
        System.out.println("\nWhat will you do?");
        System.out.println("1. Travel to next location");
        System.out.println("2. Rest and recover (restore morale, use coffee)");
        System.out.println("3. Work on Product (reduce bugs, use coffee)");
        System.out.println("4. Save game");
        System.out.println("5. Quit to menu");

        int choice = scanner.nextInt();

        return switch (choice) {
            case 1 -> ActionType.TRAVEL;
            case 2 -> ActionType.REST;
            case 3 -> ActionType.WORK_ON_PRODUCT;
            case 4 -> ActionType.SAVE;
            case 5 -> ActionType.QUIT;
            default -> {
                System.out.println("Invalid choice, try again.");
                yield promptPlayerAction();
            }
        };
    }

    private GameSessionResult handleAction(ActionType action, GameState state) {
        switch (action) {
            case TRAVEL:
                handleTravel(state);

                if (!state.isGameOver()) {
                    gameRules.processEndOfDay(state);
                    state.incrementDay();
                }

                return GameSessionResult.CONTINUE;

            case REST:
                state.adjustMorale(GameConstants.REST_MORALE_MOD);
                state.adjustCoffee(GameConstants.REST_COFFEE_MOD);
                System.out.println("You rest and recover...");

                if (!state.isGameOver()) {
                    gameRules.processEndOfDay(state);
                    state.incrementDay();
                }

                return GameSessionResult.CONTINUE;

            case WORK_ON_PRODUCT:
                state.adjustMorale(GameConstants.WORK_ON_PRODUCT_MORALE_MOD);
                state.adjustCoffee(GameConstants.WORK_ON_PRODUCT_COFFEE_MOD);
                state.adjustBugs(GameConstants.WORK_ON_PRODUCT_BUG_MOD);
                System.out.println("Your team focuses on squashing bugs...");

                if (!state.isGameOver()) {
                    gameRules.processEndOfDay(state);
                    state.incrementDay();
                }

                return GameSessionResult.CONTINUE;

            case SAVE:
                handleSave(state);
                return GameSessionResult.CONTINUE;

            case QUIT:
                return GameSessionResult.RETURNED_TO_MENU;

            default:
                return GameSessionResult.CONTINUE;
        }
    }


    private void handleTravel(GameState state) {
        if (!route.hasNextLocation(state)) {
            System.out.println("You are already at the final location.");
            return;
        }

        state.advanceLocation();

        Location currentLocation = route.getCurrentLocation(state);

        System.out.println("\n========================================");
        System.out.println("You traveled to " + currentLocation.getName() + ".");

        if (route.isAtFinalLocation(state)) {
            state.setGameWon(true);
            state.setGameOver(true);
            return;
        }

        WeatherEffect weatherEffect = weatherService.getWeatherEffect(currentLocation);

        System.out.println("Current weather: " + formatWeatherSummary(weatherEffect) + "°F");

        // Weather effects only
        state.adjustCoffee(weatherEffect.getCoffeeModifier());
        state.adjustMorale(weatherEffect.getMoraleModifier());
        state.adjustBugs(weatherEffect.getBugModifier());

        Event event = eventResolver.resolveEvent(currentLocation, weatherEffect.getType());
        EventChoice selectedChoice = promptEventChoice(event);
        applyEventChoice(selectedChoice, state);

        System.out.println("\nYou chose: " + selectedChoice.getDescription());

    }

    private void handleSave(GameState state) {
        try {
            saveService.saveGame(state);
            System.out.println("\nGame saved successfully.");
        } catch (Exception e) {
            System.out.println("\nFailed to save game: " + e.getMessage());
        }
    }

    private EventChoice promptEventChoice(Event event) {
        System.out.println("----------------------------------------");
        System.out.println("Event: " + event.getTitle());
        System.out.println(event.getDescription());
        System.out.println("----------------------------------------");

        System.out.println("1. " + event.getChoiceOne().getDescription()
                + " (" + formatChoiceEffects(event.getChoiceOne()) + ")");
        System.out.println("2. " + event.getChoiceTwo().getDescription()
                + " (" + formatChoiceEffects(event.getChoiceTwo()) + ")");

        int choice = scanner.nextInt();

        if (choice == 1) {
            return event.getChoiceOne();
        } else if (choice == 2) {
            return event.getChoiceTwo();
        } else {
            System.out.println("Invalid choice, try again.");
            return promptEventChoice(event);
        }
    }

    private void applyEventChoice(EventChoice choice, GameState state) {
        state.adjustCash(choice.getCashChange());
        state.adjustMorale(choice.getMoraleChange());
        state.adjustCoffee(choice.getCoffeeChange());
        state.adjustBugs(choice.getBugsChange());
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

    private int getProgressPercent(GameState state) {
        return state.getCurrentLocationIndex() * GameConstants.MAP_PROGRESS_MULTIPLIER;
    }

    private String formatWeatherSummary(WeatherEffect weatherEffect) {
        return weatherEffect.getType() + ", " + Math.round(weatherEffect.getTemperatureFahrenheit());
    }

    private void printWelcomeInstructions() {
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
                Traveling triggers special events.
                """);
    }

    private PostGameOption promptWinMenu() {
        System.out.println("""
                1. New Game
                2. Back to Main menu
                3. Quit""");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return PostGameOption.NEW_GAME;

            case 2:
                return PostGameOption.MAIN_MENU;

            case 3: return PostGameOption.QUIT;

            default:
                System.out.println("Invalid choice, try again.");
                return promptWinMenu();
        }
    }

    private boolean confirmDeleteSave() {
        System.out.println("""
                Are you sure you want to delete this save file?
                1. Yes
                2. No
                """);

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return true;

            case 2:
                return false;

            default:
                System.out.println("Invalid choice, try again.");
                return confirmDeleteSave();
        }
    }

    private void handleWinFlow() {
        System.out.println("Your startup survived the Silicon Valley Trail! You win!");
        System.out.println("========================================");

        PostGameOption option = promptWinMenu();

        switch (option) {
            case NEW_GAME:
                startNewGame();
                break;

            case MAIN_MENU:
                break;

            case QUIT:
                System.out.println("Goodbye.");
                System.exit(0);
                break;
        }
    }

    private void printLossMessage() {
        System.out.println("""
                Your startup failed. Game over.
                Returning to main menu...
                ========================================""");
    }
}
