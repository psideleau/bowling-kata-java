package com.surveysampling.bowling

import spock.lang.Specification

/**
 * Created by SSI.
 */
class MemoryBowlingAccessTest extends Specification {
    MemoryBowlingAccess access = new MemoryBowlingAccess()
    Game game = new Game(UUID.randomUUID().toString())
    def "should save game to memory"() {
        when:
        access.saveGame(game)

        then:
        access.gameMap.get(game.gameId) == game;
    }

    def "should read game from memory"() {
        given:
        access.saveGame(game)

        when:
        Game returnedGame = access.findGame(game.gameId).get()

        then:
        returnedGame.is(game)
    }
}
