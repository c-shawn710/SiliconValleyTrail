package service;

import model.GameState;

public class ScoreCalculator {
    private final GameState state;

    public ScoreCalculator(GameState state) {
        this.state = state;
    }

    public int calculateScore() {
        int score = 0;
        score += state.getCash() /10 ;
        score += state.getMorale() * 10;
        score -= (state.getBugs() * 5);
        score -= state.getDay() * 5;
        return Math.max(0, score);
    }
}
