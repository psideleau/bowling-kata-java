package com.surveysampling.bowling

/**
 * Created by SSI.
 */
class Bowler {
    final Game game
    int roll
    int pins

    Bowler(Game game) {
        this.game = game
    }

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