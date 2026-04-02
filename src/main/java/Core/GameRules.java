package Core;

import Constants.GameConstants;
import Model.GameState;
import Model.Route;

public class GameRules {

    public void evaluateGameStatus(GameState state, Route route) {
        checkWinCondition(state, route);

        if (state.isGameOver()) {
            return;
        }

        checkLoseCondition(state);
    }

    public void processEndOfDay(GameState state) {
        if (state.getCoffee() == GameConstants.MIN_COFFEE) {
            state.incrementConsecutiveDaysWithoutCoffee();

            if (state.getConsecutiveDaysWithoutCoffee() >= GameConstants.MAX_CONSECUTIVE_DAYS_WITHOUT_COFFEE) {
                state.setGameOver(true);
            }
        } else {
            state.resetConsecutiveDaysWithoutCoffee();
        }

        state.incrementDay();

    }

    private void checkWinCondition(GameState state, Route route) {
        if (route.isAtFinalLocation(state)) {
            state.setGameWon(true);
            state.setGameOver(true);
        }
    }

    private void checkLoseCondition(GameState state) {
        if (state.getCash() <= GameConstants.MIN_CASH ||
            state.getMorale() <= GameConstants.MIN_MORALE ||
            state.getBugs() >= GameConstants.MAX_BUGS) {

            state.setGameOver(true);
        }
    }
}
