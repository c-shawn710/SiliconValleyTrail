import Core.GameRules;
import Model.GameState;
import Model.Route;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameRulesTest {

    @Test
    void zeroCash_setsGameOver() {
        GameRules rules = new GameRules();
        Route route = new Route();
        GameState state = new GameState();

        state.adjustCash(-99999);

        rules.evaluateGameStatus(state, route);

        assertTrue(state.isGameOver());
        assertFalse(state.isGameWon());
    }

    @Test
    void zeroMorale_setsGameOver() {
        GameRules rules = new GameRules();
        Route route = new Route();
        GameState state = new GameState();

        state.adjustMorale(-999);

        rules.evaluateGameStatus(state, route);

        assertTrue(state.isGameOver());
        assertFalse(state.isGameWon());
    }

    @Test
    void maxBugs_setsGameOver() {
        GameRules rules = new GameRules();
        Route route = new Route();
        GameState state = new GameState();

        state.adjustBugs(999);

        rules.evaluateGameStatus(state, route);

        assertTrue(state.isGameOver());
        assertFalse(state.isGameWon());
    }

    @Test
    void twoConsecutiveDaysWithoutCoffee_setsGameOver() {
        GameRules rules = new GameRules();
        GameState state = new GameState();

        state.adjustCoffee(-999);

        rules.processEndOfDay(state);
        assertFalse(state.isGameWon());
        assertEquals(1, state.getConsecutiveDaysWithoutCoffee());

        rules.processEndOfDay(state);
        assertTrue(state.isGameOver());
        assertEquals(2, state.getConsecutiveDaysWithoutCoffee());
    }

    @Test
    void coffeeAboveZero_resetsCounter() {
        GameRules rules = new GameRules();
        GameState state = new GameState();

        state.adjustCoffee(-999);
        rules.processEndOfDay(state);
        assertEquals(1, state.getConsecutiveDaysWithoutCoffee());

        state.adjustCoffee(5);
        rules.processEndOfDay(state);
        assertEquals(0, state.getConsecutiveDaysWithoutCoffee());

        assertFalse(state.isGameOver());
    }

    @Test
    void reachingFinalLocation_setsWinAndGameOver() {
        GameRules rules = new GameRules();
        Route route = new Route();
        GameState state = new GameState();

        while (route.hasNextLocation(state)) {
            state.advanceLocation();
        }

        rules.evaluateGameStatus(state, route);

        assertTrue(state.isGameWon());
        assertTrue(state.isGameOver());
    }

    @Test
    void normalState_doesNotSetGameOver() {
        GameRules rules = new GameRules();
        Route route = new Route();
        GameState state = new GameState();

        rules.evaluateGameStatus(state, route);

        assertFalse(state.isGameWon());
        assertFalse(state.isGameOver());
    }
}
