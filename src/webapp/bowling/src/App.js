import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Game from "./Game.js";

class App extends Component {
    constructor(props) {
        super(props);

        const gameId = 1
        const frames = []
        for (var i = 0; i < 9; i++) {
            frames.push({roll1: '5', roll2: '4', score:'9'});
        }

        frames.push({roll1: '_', roll2:'_', score:'_'});

        this.gameGateway = {
            rolled: false,
            gameId: 0,
            value: 0,
            startGame: () => {return {gameId: '1234', frames: frames};},
            rollPins: (gameId, value) => {
                this.gameGateway.rolled = true;
                this.gameGateway.value = value;
                this.gameGateway.gameId = gameId;
                const frames = []
                for (var i = 0; i < 9; i++) {
                    frames.push({roll1: '5', roll2: '4', score:'9'});
                }

                frames.push({roll1: value.toString(), roll2:'_', score:'_'});

                return frames;
            }
        };
    }

  render() {
    return (

      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to React</h2>
        </div>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
        <Game gameGateway={this.gameGateway} />
      </div>
    );
  }
}


export default App;
