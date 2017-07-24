package com.surveysampling.bowling;

/**
 * Created by SSI.
 */
public interface BowlingService {
    GameResponse startGame();
    GameResponse rollPins(GameRollRequest request);
}
