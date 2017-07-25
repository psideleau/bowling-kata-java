package com.surveysampling.bowling;

import lombok.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Created by SSI.
 */
public class Game {
    private static final int BONUS_FRAMES = 2;
    private static final int TOTAL_PINS = 10;
    public static final int NUMBER_OF_FRAMES = 10;
    public static final int FRAMES_PLUS_BONUS_FRAMES = NUMBER_OF_FRAMES + BONUS_FRAMES;
    public static final int CANNOT_SCORE_YET = -1;

    private final List<Frame> frames = new ArrayList<>(NUMBER_OF_FRAMES + BONUS_FRAMES);
    private final String gameId;

    public Game(String gameId) {
        this.gameId = gameId;
        for (int frame = 0; frame < NUMBER_OF_FRAMES + BONUS_FRAMES; frame++) {
            frames.add(new Frame());
        }
    }

    String getGameId() {
        return gameId;
    }

    public boolean isFinished() {
        Frame lastFrame = frames.get(NUMBER_OF_FRAMES - 1);

        if (isSpare(lastFrame)) {
            return frames.get(NUMBER_OF_FRAMES).roll1 > CANNOT_SCORE_YET;
        }
        else if (isStrike(lastFrame)) {
            return frames.get(NUMBER_OF_FRAMES + 1).roll1 > CANNOT_SCORE_YET;
        }
        else {
            return lastFrame.roll2 > CANNOT_SCORE_YET;
        }
    }

    public int getRunningScore(int frameIdx) {
        return getRunningScoresUpToInclusive(frameIdx).runningScore;
    }

    private Game.RunningScoreSum getRunningScoresUpToInclusive(int frameIdx) {
        return getRunningScore(Game.RunningScoreSum.builder()
                .runningScore(0)
                .currentFrameIdx(0)
                .desiredFrameIdx(frameIdx)
                .scoreSumList(new ArrayList<>())
                .build());
    }

    public void roll(int pins) {
        throwErrorIfInvalidNumberOfPins(pins);
        Frame frame = getCurrentFrame();

        if (hasBowlerNotStartedFrame(frame)) {
            frame.roll1 = pins;
        }
        else  {
            throwErrorIfTwoRollsInFrameExceedsLimit(pins, frame);
            frame.roll2 = pins;
        }
    }

    private Frame getCurrentFrame() {
        return frames.stream()
                .filter(frame -> hasBowlerNotStartedFrame(frame)
                                 || (!this.isStrike(frame) && frame.roll2 == CANNOT_SCORE_YET))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private boolean hasBowlerNotStartedFrame(Frame frame) {
        return frame.roll1 == CANNOT_SCORE_YET;
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
        return getRunningScore(NUMBER_OF_FRAMES -1);
    }

    public List<Integer> getRunningScorePerFramePlayed() {
        List<Integer> scoreSumList = getRunningScoresUpToInclusive(NUMBER_OF_FRAMES - 1).scoreSumList;

        for (int i = scoreSumList.size(); i < FRAMES_PLUS_BONUS_FRAMES; i++) {
            scoreSumList.add(CANNOT_SCORE_YET);
        }

        return scoreSumList;
    }

    public <T> List<T> mapFrames(Function<Frame, T> mapper) {
        return excludeBonusFrames()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    private List<Frame> excludeBonusFrames() {
        return this.frames.subList(0, Game.NUMBER_OF_FRAMES);
    }

    private RunningScoreSum getRunningScore(RunningScoreSum runningScore) {
        if (runningScore.currentFrameIdx > runningScore.desiredFrameIdx ||
            hasBowlerNotStartedFrame(frames.get(runningScore.currentFrameIdx))) {
            return runningScore;
        }

        int newRunningScore = runningScore.runningScore + getRawScoreForFrame(runningScore.currentFrameIdx);
        runningScore.scoreSumList.add(newRunningScore);
        int nextFrame = runningScore.currentFrameIdx + 1;

        return getRunningScore(RunningScoreSum.builder().runningScore(newRunningScore)
                .currentFrameIdx(nextFrame)
                .desiredFrameIdx(runningScore.desiredFrameIdx)
                .runningScore(newRunningScore)
                .scoreSumList(runningScore.scoreSumList)
                .build());
    }

    private int getRawScoreForFrame(int currentFrameIdx) {
        Frame frame = frames.get(currentFrameIdx);
        Frame nextFrame = frames.get(currentFrameIdx + 1);

        if (isStrike(frame)) {
            return TOTAL_PINS +
                    getPinsFromFirstRollAfterAStrike(currentFrameIdx) +
                    getPinsFromSecondRollAfterAStrike(currentFrameIdx);
        }
        else if (isSpare(frame)) {
            return TOTAL_PINS + getPinsKnockedDown(nextFrame.roll1);
        }
        else {
            return getPinsKnockedDown(frame.roll1) + getPinsKnockedDown(frame.roll2);
        }
    }

    private int getPinsFromFirstRollAfterAStrike(int currentFrameIdx) {
        Frame nextFrame = frames.get(currentFrameIdx + 1);
        return getPinsKnockedDown(nextFrame.roll1);
    }

    private int getPinsFromSecondRollAfterAStrike(int currentFrameIdx) {
        Frame nextFrame = frames.get(currentFrameIdx + 1);
        return (isStrike(nextFrame))
                ? getPinsKnockedDown(frames.get(currentFrameIdx + 2).roll1)
                : getPinsKnockedDown(nextFrame.roll2);
    }

    private int getPinsKnockedDown(int pinsKnockedDown) {
        return pinsKnockedDown == CANNOT_SCORE_YET ? 0 : pinsKnockedDown;
    }

    private boolean isStrike(Frame frame) {
        return getPinsKnockedDown(frame.roll1) == TOTAL_PINS;
    }

    private boolean isSpare(Frame frame) {
        return (!isStrike(frame) &&
                (getPinsKnockedDown(frame.roll1) +
                getPinsKnockedDown(frame.roll2) == TOTAL_PINS));
    }

    @Value
    @Builder
    private static class RunningScoreSum {
        private final int desiredFrameIdx;
        private final int currentFrameIdx;
        private final int runningScore;
        private final List<Integer> scoreSumList;
    }

    @Getter
    public static class Frame {
        private int roll1 = CANNOT_SCORE_YET;
        private int roll2 = CANNOT_SCORE_YET;
    }
}