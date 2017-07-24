package com.surveysampling.bowling

import spock.lang.Specification

import java.util.concurrent.atomic.AtomicReference

/**
 * Created by SSI.
 */
class BowlingInteractorTest extends Specification {

    BowlingAccess access = Mock()
    BowlingInteractor service = new BowlingInteractor(access)

    def "should start a new game"() {
        given:
        AtomicReference<Game> gameSpy = new AtomicReference<>()

        when:
        GameResponse response = service.startGame()

        then:
        1 * access.saveGame(_) >> { Game game ->
            gameSpy.set(game)
            assert UUID.fromString(game.gameId) != null
        }

        and:
        gameSpy.get() != null
        response.frames.size() == gameSpy.get().frames.size()
        response.frames.every {it.roll1 == Game.CANNOT_SCORE_YET &&
                               it.roll2 == Game.CANNOT_SCORE_YET &&
                               it.score == Game.CANNOT_SCORE_YET}

        response.gameId == gameSpy.get().gameId
        response.totalScore == 0
    }

    def "should update pins"() {
        given: "the bowler has gotten a perfect score"
        Game game = new Game(UUID.randomUUID().toString())
        Bowler bowler = new Bowler(game)
        bowler.turns(10).hitPins(10).rollBall()
        def pinsToKnockDown = 7

        when: "bowler rolls bonus frame"
        GameResponse response = service.rollPins(GameRollRequest.builder()
                .gameID(game.gameId)
                .pins(pinsToKnockDown)
                .build())

        then:
        1 * access.findGame(game.gameId) >> Optional.of(game);

        then:
        interaction {
            assertSaveGameAfterUpdatingScore(game, pinsToKnockDown)
        }

        assertValidResponse(response, game)
        assertThatResponseFramesMatchGameFrames(response, game)
    }

    private void assertSaveGameAfterUpdatingScore(Game game, int pins) {
        1 * access.saveGame(game) >> {
            // make sure game is saved after updating it
            assert game.frames[10].roll1 == pins
        }
    }

    private void assertValidResponse(GameResponse response, Game game) {
        response.totalScore == game.score
        response.gameId == game.gameId
        response.frames.size() == game.frames.size()
    }

    private void assertThatResponseFramesMatchGameFrames(GameResponse response, Game game) {
        for (int i = 0; i < Game.NUMBER_OF_FRAMES; i++) {
            assert response.frames[i].score == game.getRunningScore(i)
            assert response.frames[i].roll1 == game.frames[i].roll1
            assert response.frames[i].roll2 == game.frames[i].roll2
        }
    }

}