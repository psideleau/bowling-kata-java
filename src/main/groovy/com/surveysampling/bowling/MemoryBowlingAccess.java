package com.surveysampling.bowling;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SSI.
 */
public class MemoryBowlingAccess implements  BowlingAccess {
    private final Map<String, Game> gameMap = new ConcurrentHashMap<>();

    @Override
    public void saveGame(Game game) {
        gameMap.put(game.getGameId(), game);
    }

    @Override
    public Optional<Game> findGame(String id) {
        return Optional.ofNullable(gameMap.get(id));
    }
}
