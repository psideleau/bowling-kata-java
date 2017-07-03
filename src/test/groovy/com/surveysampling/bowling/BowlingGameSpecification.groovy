package com.surveysampling.bowling

import spock.lang.*
/**
 * Created by SSI.
 */
class BowlingGameSpecification extends Specification {
    @Subject
    Game game = new Game()
    Bowler bowler = new Bowler()

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

        and: "on the last turn in the 10 frame rolls a spare"
        bowler.turns(1).hitPins(9).rollBall()

        when: "bowler rules again"
        bowler.turns(1).hitPins(9).rollBall()

        then: "last roll is added to final frame"
        game.score == 37
    }


    def class Bowler {
        int roll
        int pins

        def turns(int times) {
            roll = times
            return this
        }

        def hitPins(int pins) {
            this.pins = pins
            return this
        }

        void rollBall() {
            roll.times {
                game.roll(pins)
            }
        }
    }


}