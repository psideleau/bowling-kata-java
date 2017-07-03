package com.surveysampling.bowling;

/**
 * Created by SSI.
 */
public class Game {
    private int[] rolls = new int[21];
    private int currentRoll = 0;

    public void roll(int pins) {
        rolls[currentRoll++] = pins;
    }

    public int getScore() {
        int totalScore = 0;
        int previousFrameScore = 0;

        for (int i = 0; i < rolls.length; i++) {
            totalScore += rolls[i];
            if (i > 0 && i % 2 == 0) {
                if (previousFrameScore == 10) {
                    totalScore+= rolls[i];
                }

                previousFrameScore = 0;
            }

            previousFrameScore+= rolls[i];
        }
        return totalScore;
    }
}
