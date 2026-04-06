package core;

import constants.GameConstants;
import enums.ActionType;
import enums.ActionResult;
import enums.MainMenuOptions;
import enums.PostGameOption;
import model.*;
import service.*;
import ui.ConsoleUI;

public class GameEngine {

    private final ConsoleUI ui;
    private final EventResolver eventResolver;
    private final GameRules gameRules;
    private final Route route;
    private final SaveService saveService;
    private final WeatherService weatherService;

    public GameEngine() {
        this.ui = new ConsoleUI();
        this.eventResolver = new EventResolver();
        this.gameRules = new GameRules();
        this.route = new Route();
        this.saveService = new SaveService();
        this.weatherService = new WeatherService();
    }

    public void startApp() {
        boolean running = true;

        while (running) {
            MainMenuOptions choice = ui.promptMainMenuChoice();

            switch (choice) {
                case NEW_GAME:
                    startNewGame();
                    break;

                case LOAD_GAME:
                    loadGameFromMenu();
                    break;

                case READ_RULES:
                    ui.printGameRules();
                    break;

                case DELETE_SAVE_FILE:
                    handleDeleteSave();
                    break;

                case QUIT:
                    running = false;
                    System.out.println("\nGoodbye.");
                    break;

                default:
                    System.out.println("\nInvalid choice, try again.");
            }
        }
    }

    private void startNewGame() {
        GameState state = new GameState();
        ui.printWelcomeInstructions();
        runGameLoop(state);
    }

    private void loadGameFromMenu() {
        try {
            GameState loadedState = saveService.loadGame();

            if (loadedState == null) {
                System.out.println("\nNo save file found.");
            } else {
                runGameLoop(loadedState);
            }
        } catch (Exception e) {
            System.out.println("\nFailed to load game: " + e.getMessage());
        }
    }

    private void runGameLoop(GameState state) {
        while (!state.isGameOver()) {
            Location currentLocation = route.getCurrentLocation(state);
            WeatherEffect currentWeather = weatherService.getWeatherEffect(currentLocation);

            ui.printState(state, currentLocation, currentWeather);

            ActionType action = ui.promptPlayerAction();
            ActionResult result = handleAction(action, state);

            if (result == ActionResult.RETURNED_TO_MENU) {
                return;
            }

            gameRules.evaluateGameStatus(state, route);
        }

        if (state.isGameWon()) {
            handleWinFlow();
        } else {
            ui.printLossMessage();
        }
    }

    private void handleDeleteSave() {
        if (!saveService.saveExists()) {
            System.out.println("\nNo save file found.");
            return;
        }

        boolean confirmed = ui.confirmDeleteSave();

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

    private ActionResult handleAction(ActionType action, GameState state) {
        switch (action) {
            case TRAVEL:
                handleTravel(state);
                break;

            case REST:
                handleRest(state);
                break;

            case WORK_ON_PRODUCT:
                handleWorkOnProduct(state);
                break;

            case SAVE:
                handleSave(state);
                return ActionResult.CONTINUE;

            case QUIT:
                return ActionResult.RETURNED_TO_MENU;

            default:
                break;
        }

        if (!state.isGameOver()) {
            gameRules.processEndOfDay(state);
        }

        return ActionResult.CONTINUE;
    }


    private void handleTravel(GameState state) {
        if (!route.hasNextLocation(state)) {
            System.out.println("You are already at the final location.");
            return;
        }

        state.advanceLocation();

        Location currentLocation = route.getCurrentLocation(state);

        System.out.println("\n=============================================");
        System.out.println("You traveled to " + currentLocation.getName() + ".");

        if (route.isAtFinalLocation(state)) {
            state.setGameWon(true);
            state.setGameOver(true);
            return;
        }

        WeatherEffect weatherEffect = weatherService.getWeatherEffect(currentLocation);

        System.out.println("Current weather: " + ui.formatWeatherSummary(weatherEffect) + "°F");

        // Weather effects only
        state.adjustCoffee(weatherEffect.getCoffeeModifier());
        state.adjustMorale(weatherEffect.getMoraleModifier());
        state.adjustBugs(weatherEffect.getBugModifier());

        Event event = eventResolver.resolveEvent(currentLocation, weatherEffect.getType());
        EventChoice selectedChoice = ui.promptEventChoice(event);
        applyEventChoice(selectedChoice, state);

        System.out.println("\nYou chose: " + selectedChoice.getChoice());
    }

    private void handleRest(GameState state) {
        state.adjustMorale(GameConstants.REST_MORALE_MOD);
        state.adjustCoffee(GameConstants.REST_COFFEE_MOD);
        System.out.println("You rest and recover...");
    }

    private void handleWorkOnProduct(GameState state) {
        state.adjustMorale(GameConstants.WORK_ON_PRODUCT_MORALE_MOD);
        state.adjustCoffee(GameConstants.WORK_ON_PRODUCT_COFFEE_MOD);
        state.adjustBugs(GameConstants.WORK_ON_PRODUCT_BUG_MOD);
        System.out.println("Your team focuses on squashing bugs...");
    }

    private void handleSave(GameState state) {
        try {
            saveService.saveGame(state);
            System.out.println("\nGame saved successfully.");
        } catch (Exception e) {
            System.out.println("\nFailed to save game: " + e.getMessage());
        }
    }

    private void applyEventChoice(EventChoice choice, GameState state) {
        state.adjustCash(choice.getCashChange());
        state.adjustMorale(choice.getMoraleChange());
        state.adjustCoffee(choice.getCoffeeChange());
        state.adjustBugs(choice.getBugsChange());
    }

    private void handleWinFlow() {
        ui.printWinMessage();

        PostGameOption option = ui.promptWinMenu();

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
}
