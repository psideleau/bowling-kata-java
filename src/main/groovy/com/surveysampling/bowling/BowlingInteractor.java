package com.surveysampling.bowling;

import java.util.*;

/**
 * Created by SSI.
 */
public class BowlingInteractor implements BowlingService {
    private final BowlingAccess access;

    public BowlingInteractor(BowlingAccess access) {
        this.access = access;
    }

    public GameResponse startGame() {
        String gameId = UUID.randomUUID().toString();
        Game game = new Game(gameId);

        access.saveGame(game);

        return GameResponse.builder()
                .gameId(gameId)
                .frames(toDTO(game))
                .totalScore(0)
                .build();
    }

    private List<FrameDTO> toDTO(Game game) {
        Queue<Integer> queue = new LinkedList(game.getRunningScorePerFramePlayed());
        return game.mapFrames(frame -> new FrameDTO(frame.getRoll1(), frame.getRoll2(), queue.poll()));
    }

    @Override
    public GameResponse rollPins(GameRollRequest request) {
        Game game = access.findGame(request.getGameID()).get();

        game.roll(request.getPins());

        access.saveGame(game);

        return GameResponse.builder()
                .gameId(game.getGameId())
                .totalScore(game.getScore())
                .frames(toDTO(game))
                .finished(game.isFinished())
                .build();
    }
}
