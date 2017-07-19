import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import Frame from './Frame'

import { render } from 'enzyme';
import { mount } from 'enzyme';
import { shallow } from 'enzyme';
describe('a bowling game', () => {
    it('renders without crashing', () => {
        const div = document.createElement('div');
        ReactDOM.render(<App />, div);
    });
});

