import React from 'react';
import ReactDOM from 'react-dom';
import Frame from './Frame'
import Frames from './Frames'

import { render } from 'enzyme';
import { mount } from 'enzyme';
import { shallow } from 'enzyme';

describe('a list of frames', () => {
    const frames = [{roll1: 5, roll2: 4, score:9},
        {roll1:4, roll2:4, score:8}];

    const gameGateway = {
        startGame: () => {return {gameId: '1234', frames: frames};}
    };

    it('should start game', () => {
        const componentFrames = mount(<Frames gameGateway={gameGateway}/>);

        const frameComponents = componentFrames.find(Frame);
        frames.forEach((frame, i) => {
            expect(frameComponents.get(i).props.frame.roll1).toBe(frame.roll1);
            expect(frameComponents.get(i).props.frame.roll2).toBe(frame.roll2);
            expect(frameComponents.get(i).props.frame.score).toBe(frame.score);
        });
    });

});

