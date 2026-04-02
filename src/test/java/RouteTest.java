import Model.GameState;
import Model.Location;
import Model.Route;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RouteTest {

    @Test
    void currentLocation_startsAtSanJose() {
        Route route = new Route();
        GameState state = new GameState();
        Location startingLocation = route.getCurrentLocation(state);

        assertEquals("San Jose", startingLocation.getName());
    }

    @Test
    void finalLocation_isSanFrancisco() {
        Route route = new Route();
        Location finalLocation = route.getLocation(route.size() - 1);

        assertEquals("San Francisco", finalLocation.getName());
    }

    @Test
    void hasNextLocation_isFalseAtFinalLocation() {
        Route route = new Route();
        GameState state = new GameState();

        while (route.hasNextLocation(state)) {
            state.advanceLocation();
        }

        assertTrue(route.isAtFinalLocation(state));
        assertFalse(route.hasNextLocation(state));
    }
}
