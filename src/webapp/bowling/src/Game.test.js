import React from 'react';
import ReactDOM from 'react-dom';
import Frame from './Frame'
import Game from './Game'

import { render } from 'enzyme';
import { mount } from 'enzyme';
import { shallow } from 'enzyme';

describe('a 1 person bowling game', () => {
    const frames = [{roll1: 5, roll2: 4, score:9},
        {roll1:4, roll2:4, score:8}, {roll1:-1, roll2:-1, score:-1}];

    const gameGateway = {
        rolled: false,
        gameId: 0,
        value: 0,
        startGame: (callback) => { return callback({gameId: '1234', frames: frames});},
        rollPins: (gameId, value, callback) => {
            gameGateway.rolled = true;
            gameGateway.value = value;
            gameGateway.gameId = gameId;
            return callback({gameId: '1234', frames:[{roll1: 5, roll2: 4, score:9},
                {roll1:4, roll2:4, score:8}, {roll1:value, roll2:'_', score:'_'}]});
        }
    };

    it('should start game', () => {
        const game = mount(<Game gameGateway={gameGateway}/>);

        expect(game.state().frames).toBe(frames);
        expect(game.state().gameId).toBe('1234');

        const frameComponents = game.find(Frame);
        frames.forEach((frame, i) => {
            expect(frameComponents.get(i).props.frame.roll1).toBe(frame.roll1);
            expect(frameComponents.get(i).props.frame.roll2).toBe(frame.roll2);
            expect(frameComponents.get(i).props.frame.score).toBe(frame.score);
        });
    });

    it('should allow user to enter number of pins to hit', () => {
        const game = mount(<Game gameGateway={gameGateway}/>);
        const pinsField = game.find('#pins').get(0)

        expect(pinsField.type).toBe('number')
        expect(pinsField.min).toBe("0")
        expect(pinsField.max).toBe("10")
    });

    it('should display driver to knock down the pins', () => {
        const game = mount(<Game gameGateway={gameGateway}/>);

        const rollButton = game.find('#roll')

        expect(rollButton.get(0)).toBeDefined()
    });

    it('should update game after rolling', () => {
        const game = mount(<Game gameGateway={gameGateway}/>);

        expect(game.node.gameGateway).toBeDefined();

        const pinsField = game.find('#pins').get(0);
        game.find('#pins').simulate('change', {target: {value: '6'}});
        game.find('#roll').simulate('submit');

        const frameComponents = game.find(Frame);
        expect(frameComponents.get(2).props.frame.roll1).toBe(6);
        expect(gameGateway.rolled).toBe(true);
        expect(gameGateway.value).toBe(6);
        expect(gameGateway.gameId).toBe('1234')
    });

});

