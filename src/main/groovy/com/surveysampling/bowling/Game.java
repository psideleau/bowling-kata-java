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
        int frameIndex = 0;

        for (int frame = 0; frame < NUMBER_OF_FRAMES; frame++) {
            if (rolls[frameIndex] == 10) {
                totalScore += 10 + rolls[frameIndex + 1] +  rolls[frameIndex + 2];
                frameIndex++;
            }
            else if (isSpare(frameIndex)) {
                totalScore += 10 + rolls[frameIndex + 2];
                frameIndex+= 2;
            }
            else {
                totalScore += rolls[frameIndex] + rolls[frameIndex + 1];
                frameIndex+= 2;
            }


        }

        return totalScore;
    }

    private boolean isSpare(int i) {
        return rolls[i] + rolls[i + 1] == 10;
    }
}
