package com.surveysampling.bowling;

/**
 * Created by SSI.
 */
public class Game {
    int score = 0;

    public void roll(int pins) {
        score+=pins;
    }

    public int getScore() {
        return score;
    }
}
