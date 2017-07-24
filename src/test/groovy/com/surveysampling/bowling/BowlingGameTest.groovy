package com.surveysampling.bowling

import spock.lang.*
/**
 * Created by SSI.
 */
class BowlingGameTest extends Specification {
    @Subject
    Game game = new Game()
    Bowler bowler = new Bowler(game)

    @Unroll
    def "should sum score using property based testing for #pins"() {
        given: "let bower take number of turns"
        int turns = 20
        bowler.turns(turns).hitPins(pins)

        when:
        bowler.rollBall()

        then:
        game.score == turns * pins

        where:
        pins  << [0, 1, 2, 3, 4]
    }

    def "should calculate a spare"() {
       given: "the bowler gets a spare in the first frame"
       game.roll(5)
       game.roll(5)

       when:  "the bower rolls his or her first ball"
       game.roll(3)

       then:  "add the number of pins hit to the first frame along with including the score"
       game.score == 16
    }

    def "should get the score of a frame"() {
        given:
        bowler.turns(3).hitPins(10).rollBall()

        when:
        int frameScore = game.getRunningScore(0)

        then:
        frameScore == 30
    }

    def "should return scores of -1 if game not start yet"() {
        when:
        List frameScores = game.getRunningScorePerFramePlayed()

        then:
        frameScores.size() == 12
        frameScores.every { it == -1}
    }


    def "should calculate running score through each frame"() {
        given: " the bowler has 3 strikes in a row"
        bowler.turns(3).hitPins(10).rollBall()

        and: "then knocks down 3 and 4 pins in frame 4, respectively"
        bowler.turns(1).hitPins(3).rollBall()
        bowler.turns(1).hitPins(4).rollBall()

        when:
        List<Integer> runningScores = game.getRunningScorePerFramePlayed();

        def frame1Score = runningScores[0]
        def frame2Score = runningScores[1]
        def frame3Score = runningScores[2]
        def frame4Score = runningScores[3]

        then: "frame 1 should be 10 pins knocked down in frame 1 + the 10 pins knocked down in second and third frames"
        frame1Score == 10 + 10 + 10

        and: "frame 2 score should include running total + 10 pins knocked down in frame 2  + strike in frame 3 and first roll in frame 4"
        frame2Score == frame1Score + 10 + 10 + 3

        and: "frame 3 score should include running total + 10 pinks knocked down in frame 3 + sum of next 2 rolls in frame 4"
        frame3Score == frame2Score + 10 + 3 + 4

        and: "frame 4 is running score + the seven picked knocked down in that frame"
        frame4Score == frame3Score + 3 + 4
    }

    def "should calculate a strike"() {
        given:
        bowler.turns(1).hitPins(10).rollBall()

        when:
        bowler.turns(1).hitPins(3).rollBall()
        bowler.turns(1).hitPins(4).rollBall()

        then:
        game.score == 24
    }

    def "should calculate a perfect game"() {
        when:
        bowler.turns(12).hitPins(10).rollBall()

        then:
        game.score == 300
    }

    def "should have an extra turn if bowler gets spare in last frame"() {
        given: "the bowler knocks down 1 pin on each turn"
        bowler.turns(19).hitPins(1).rollBall()

        and: "on the last turn in the 10th frame rolls a spare"
        bowler.turns(1).hitPins(9).rollBall()

        when: "bowler rules again"
        bowler.turns(1).hitPins(9).rollBall()

        then: "last roll is added to final frame"
        game.score == 37
    }

    @Unroll
    def "should throw illegal argument exception when invalid pins: #pins"() {
        when:
        game.roll(pins)

        then:
        thrown(IllegalArgumentException)

        where:
        pins << [-1, 11, 100, Integer.MAX_VALUE]
    }

    @Unroll
    def "should not be able knock down an invalid number of pins: #pins"() {
        given:
        bowler.turns(1).hitPins(4).rollBall()

        when:
        bowler.turns(1).hitPins(pins).rollBall()

        then:
        thrown(IllegalArgumentException)

        where:
        pins << [7, 8, 9, 10]
    }

    @Unroll
    def "can knockdown 10 pins in a frame: #pins"() {
        given:
        bowler.turns(1).hitPins(4).rollBall()

        when:
        bowler.turns(1).hitPins(pins).rollBall()

        then:
        game.score == 4 + pins

        where:
        pins << [6, 5, 4, 3, 2, 1, 0]
    }



}