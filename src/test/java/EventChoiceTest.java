import model.EventChoice;
import model.GameState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventChoiceTest {

    @Test
    void eventChoice_updatesResourcesCorrectly() {
        GameState state = new GameState();

        EventChoice choice = EventChoice.builder("Test choice")
                .cash(-3000)
                .morale(-5)
                .coffee(-2)
                .bugs(-1)
                .build();

        state.adjustCash(choice.getCashChange());
        state.adjustMorale(choice.getMoraleChange());
        state.adjustCoffee(choice.getCoffeeChange());
        state.adjustBugs(choice.getBugsChange());

        assertEquals(47000, state.getCash());
        assertEquals(95, state.getMorale());
        assertEquals(48, state.getCoffee());
        assertEquals(0, state.getBugs());
    }
}
