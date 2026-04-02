import Constants.GameConstants;
import Model.GameState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateTest {

    @Test
    void adjustCash_clampsToMin() {
        GameState state = new GameState();

        state.adjustCash(-9999999);
        assertEquals(GameConstants.MIN_CASH, state.getCash());
    }

    @Test
    void adjustMorale_clampsToMax() {
        GameState state = new GameState();

        state.adjustMorale(9999);
        assertEquals(GameConstants.MAX_MORALE, state.getMorale());
    }

    @Test
    void adjustMorale_clampsToMin() {
        GameState state = new GameState();

        state.adjustMorale(-9999);
        assertEquals(GameConstants.MIN_MORALE, state.getMorale());
    }

    @Test
    void adjustCoffee_clampsToMax() {
        GameState state = new GameState();

        state.adjustCoffee(9999);
        assertEquals(GameConstants.MAX_COFFEE, state.getCoffee());
    }

    @Test
    void adjustCoffee_clampsToMin() {
        GameState state = new GameState();

        state.adjustCoffee(-9999);
        assertEquals(GameConstants.MIN_COFFEE, state.getCoffee());
    }

    @Test
    void adjustBugs_clampsToMax() {
        GameState state = new GameState();

        state.adjustBugs(9999);
        assertEquals(GameConstants.MAX_BUGS, state.getBugs());
    }

    @Test
    void adjustBugs_clampsToMin() {
        GameState state = new GameState();

        state.adjustBugs(-9999);
        assertEquals(GameConstants.MIN_BUGS, state.getBugs());
    }

}
