import Model.GameState;
import Service.SaveService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class SaveServiceTest {

    private final SaveService saveService = new SaveService();

    @AfterEach
    void cleanUp() {
        saveService.deleteSave();
    }

    @Test
    void saveGame_createsSaveFile() throws Exception{
        GameState state = new GameState();

        saveService.saveGame(state);

        assertTrue(saveService.saveExists());
    }

    @Test
    void loadGame_returnsSavedState() throws Exception {
        GameState originalState = new GameState();

        originalState.incrementDay();
        originalState.advanceLocation();
        originalState.adjustCash(-5000);
        originalState.adjustMorale(-10);
        originalState.adjustCoffee(-5);
        originalState.adjustBugs(1);
        originalState.incrementConsecutiveDaysWithoutCoffee();

        saveService.saveGame(originalState);

        GameState savedState = saveService.loadGame();

        assertNotNull(savedState);
        assertEquals(originalState.getDay(), savedState.getDay());
        assertEquals(originalState.getCurrentLocationIndex(), savedState.getCurrentLocationIndex());
        assertEquals(originalState.getCash(), savedState.getCash());
        assertEquals(originalState.getMorale(), savedState.getMorale());
        assertEquals(originalState.getCoffee(), savedState.getCoffee());
        assertEquals(originalState.getBugs(), savedState.getBugs());
        assertEquals(originalState.getConsecutiveDaysWithoutCoffee(), savedState.getConsecutiveDaysWithoutCoffee());
    }

    @Test
    void loadGame_returnsNull_whenNoSaveFileExists() throws Exception {
        saveService.deleteSave();

        GameState loadedState = saveService.loadGame();

        assertNull(loadedState);
    }

    @Test
    void deleteSavedFile() throws IOException {
        GameState state = new GameState();

        saveService.saveGame(state);
        assertTrue(saveService.saveExists());

        boolean deleted = saveService.deleteSave();

        assertTrue(deleted);
        assertFalse(saveService.saveExists());
    }
}
