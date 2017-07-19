import React from 'react';
import ReactDOM from 'react-dom';
//import App from './App';
import Frame from './Frame';

import { render } from 'enzyme';

it('renders without crashing', () => {
  //const div = document.createElement('div');
 // ReactDOM.render(<App />, div);
});

describe('A Bowling frame', () => {
    var frameComponent;
    beforeEach(() => {
        const frame = {roll1 : "6", roll2:"/", score:"15"};
        frameComponent = render(<Frame frame={frame}/>);
    });

    it('shows the number of pins on the first roll', () => {
        const roll1 = frameComponent.find('.roll1');
        expect(roll1.text()).toBe('6');
    });

    it('and the number of pins on the second roll', () => {
        const roll2 = frameComponent.find('.roll2');
        expect(roll2.text()).toBe('/');
    });

    it('and gives the current total score on the frame', () => {
        const score = frameComponent.find('.frameScore');
        expect(score.text()).toBe('15');
    });
});
