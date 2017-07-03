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
        pins  << [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    def "should calculate a spare"() {
       given: "the bowler gets a spare in the first frame"
       game.roll(6)
       game.roll(4)

       when:  "the bower rolls his or her first ball"
       game.roll(3)

       then:  "add the number of pins hit to the first frame along with including the score"
       game.score ==
            g.roll(5);
            g.roll(5); // spare
            g.roll(3);
            rollMany(17,0);
            assertEquals(16,g.score());
        }
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