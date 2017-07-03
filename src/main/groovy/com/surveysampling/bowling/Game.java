package com.surveysampling.bowling;

import java.util.*;
import java.util.stream.*;

/**
 * Created by SSI.
 */
public class Game {
    private static final int BONUS_FRAME = 2;
    private static final int TOTAL_PINS = 10;
    private static final int NUMBER_OF_FRAMES = 10;
    public static final int NOT_ROLLED = -1;
    private int currentFrame = 0;
    private List<Frame> frames = new ArrayList<>(NUMBER_OF_FRAMES + BONUS_FRAME);

    public Game() {
        for (int frame = 0; frame < NUMBER_OF_FRAMES + BONUS_FRAME; frame++) {
            frames.add(new Frame());
        }
    }

    public void roll(int pins) {
        throwErrorIfInvalidNumberOfPins(pins);
        Frame frame = frames.get(currentFrame);

        if (isBowlerFirstTurnInFrame(frame)) {
            frame.roll1 = pins;
            if (frame.isStrike()) {
                currentFrame+=1;
            }
        }
        else  {
            throwErrorIfTwoRollsInFrameExceedsLimit(pins, frame);
            frame.roll2 = pins;
            currentFrame+=1;
        }
    }

    private boolean isBowlerFirstTurnInFrame(Frame frame) {
        return frame.roll1 == NOT_ROLLED;
    }

    private void throwErrorIfTwoRollsInFrameExceedsLimit(int pins, Frame frame) {
        if (frame.roll1 + pins > TOTAL_PINS) {
            throw new IllegalArgumentException(String.format("Cannot knock down more than %d pins.", TOTAL_PINS));
        }
    }

    private void throwErrorIfInvalidNumberOfPins(int pins) {
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException(pins + " is an invalid roll. expecting 0-10");
        }
    }

    public int getScore() {
        return IntStream.range(0, NUMBER_OF_FRAMES)
              .map(this::calculateScoreForFrame)
              .sum();
    }

    private int calculateScoreForFrame(int i) {
        Frame frame = frames.get(i);
        Frame nextFrame = frames.get(i + 1);

        if (frame.isStrike()) {
            return getPinsFromFirstRollAfterAStrike(i) + getPinsFromSecondRollAfterAStrike(i);
        }
        else if (frame.isSpare()) {
            return TOTAL_PINS + nextFrame.getRoll1();
        }
        else {
            return frame.getScore();
        }
    }

    private int getPinsFromFirstRollAfterAStrike(int currentFrameIdx) {
        Frame nextFrame = frames.get(currentFrameIdx + 1);
        return TOTAL_PINS + nextFrame.getRoll1();
    }

    private int getPinsFromSecondRollAfterAStrike(int currentFrameIdx) {
        Frame nextFrame = frames.get(currentFrameIdx + 1);
        return (nextFrame.isStrike()) ? frames.get(currentFrameIdx + 2).getRoll1() : nextFrame.getRoll2();
    }


    private static class Frame {
        private int roll1 = -1;
        private int roll2 = -1;

        boolean isStrike() {
            return roll1 == TOTAL_PINS;
        }

        boolean isSpare() {
            return (roll1 + roll2) == TOTAL_PINS;
        }

        int getScore() {
            return  getRoll1() + getRoll2();
        }

        public int getRoll1() {
            return roll1 == NOT_ROLLED ? 0 : roll1;
        }

        public int getRoll2() {
            return roll2 == NOT_ROLLED ? 0 : roll2;
        }
    }

}
