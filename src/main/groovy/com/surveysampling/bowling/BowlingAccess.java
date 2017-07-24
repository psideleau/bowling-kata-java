package com.surveysampling.bowling;

import java.util.*;

/**
 * Created by SSI.
 */
public interface BowlingAccess {
    void saveGame(Game game);
    Optional<Game> findGame(String id);
}
