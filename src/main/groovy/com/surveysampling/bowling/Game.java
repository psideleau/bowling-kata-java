package com.surveysampling.bowling;

/**
 * Created by SSI.
 */
public class Game {
    private static final int NUMBER_OF_FRAMES = 10;
    private int[] rolls = new int[21];
    private int currentRoll = 0;

    public void roll(int pins) {
        rolls[currentRoll++] = pins;
    }

    public int getScore() {
        int totalScore = 0;
        int i = 0;

        for (int frameIndex = 0; frameIndex < NUMBER_OF_FRAMES; frameIndex++) {
            if (isSpare(i)) {
                totalScore += 10 + rolls[i + 2];
            }
            else {
                totalScore += rolls[i] + rolls[i + 1];
            }
            i+= 2;
        }

        return totalScore;
    }

    private boolean isSpare(int i) {
        return rolls[i] + rolls[i + 1] == 10;
    }
}
