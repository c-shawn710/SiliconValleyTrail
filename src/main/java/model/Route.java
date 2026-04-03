package model;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private final List<Location> locations;

    public Route() {
        locations = new ArrayList<>();
        initializeLocations();
    }

    private void initializeLocations() {
        locations.add(new Location("San Jose", 37.3382, -121.8863));
        locations.add(new Location("Santa Clara", 37.3541, -121.9552));
        locations.add(new Location("Sunnyvale", 37.3688, -122.0363));
        locations.add(new Location("Mountain View", 37.3861, -122.0839));
        locations.add(new Location("Palo Alto", 37.4419, -122.1430));
        locations.add(new Location("Menlo Park", 37.4530, -122.1817));
        locations.add(new Location("Redwood City", 37.4852, -122.2364));
        locations.add(new Location("San Mateo", 37.5630, -122.3255));
        locations.add(new Location("San Bruno", 37.6305, -122.4111));
        locations.add(new Location("Daly City", 37.6879, -122.4702));
        locations.add(new Location("San Francisco", 37.7749, -122.4194));
    }

    public Location getLocation(int index) {
        return locations.get(index);
    }

    public Location getCurrentLocation(GameState state) {
        return locations.get(state.getCurrentLocationIndex());
    }

    public boolean hasNextLocation(GameState state) {
        return state.getCurrentLocationIndex() < locations.size() - 1;
    }

    public boolean isAtFinalLocation(GameState state) {
        return state.getCurrentLocationIndex() == locations.size() - 1;
    }

    public int size() {
        return locations.size();
    }
}
