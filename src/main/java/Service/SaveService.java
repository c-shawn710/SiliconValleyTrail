package Service;

import Constants.GameConstants;
import Model.GameState;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import static Constants.GameConstants.*;

public class SaveService {

    private static final File SAVE_FILE = new File("savegame.txt");

    public void saveGame(GameState state) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(String.valueOf(state.getDay()));
            writer.newLine();

            writer.write(String.valueOf(state.getCurrentLocationIndex()));
            writer.newLine();

            writer.write(String.valueOf(state.getCash()));
            writer.newLine();

            writer.write(String.valueOf(state.getMorale()));
            writer.newLine();

            writer.write(String.valueOf(state.getCoffee()));
            writer.newLine();

            writer.write(String.valueOf(state.getBugs()));
            writer.newLine();

            writer.write(String.valueOf(state.getConsecutiveDaysWithoutCoffee()));
            writer.newLine();
        }
    }

    public GameState loadGame() throws IOException{
        if (!SAVE_FILE.exists()) {
            return null;
        }

        List<String> lines = Files.readAllLines(SAVE_FILE.toPath());

        if (lines.size() < GameConstants.NUMBER_OF_STATS) {
            throw new IOException("save file is incomplete or corrupted.");
        }

        int day = Integer.parseInt(lines.get(DAY_INDEX));
        int currentLocationIndex = Integer.parseInt(lines.get(LOCATION_INDEX));
        int cash = Integer.parseInt(lines.get(CASH_INDEX));
        int morale = Integer.parseInt(lines.get(MORALE_INDEX));
        int coffee = Integer.parseInt(lines.get(COFFEE_INDEX));
        int bugs = Integer.parseInt(lines.get(BUG_INDEX));
        int consecutiveDaysWithoutCoffee = Integer.parseInt(lines.get(CONSECUTIVE_DAYS_WITHOUT_COFFEE_INDEX));

        return new GameState(
                day,
                currentLocationIndex,
                cash,
                morale,
                coffee,
                bugs,
                consecutiveDaysWithoutCoffee,
                false,
                false
        );
    }

    public boolean deleteSave() {
        return SAVE_FILE.exists() && SAVE_FILE.delete();
    }

    public boolean saveExists() {
        return SAVE_FILE.exists();
    }
}
