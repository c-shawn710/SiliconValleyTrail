package service;

import constants.GameConstants;
import model.GameState;

public class ScoreCalculator {
    private final GameState state;

    public ScoreCalculator(GameState state) {
        this.state = state;
    }

    public int calculateScore() {
        int score = 0;
        score += state.getCash() / GameConstants.CASH_SCORE;
        score += state.getMorale() * GameConstants.MORALE_SCORE;
        score += state.getCoffee() * GameConstants.COFFEE_SCORE;
        score += state.getBugs() * GameConstants.BUGS_SCORE;
        score += state.getDay() * GameConstants.DAY_SCORE;
        return Math.max(0, score);
    }
}
