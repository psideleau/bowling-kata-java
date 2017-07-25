import React, { Component } from 'react';
import './App.css';
import GameGateway from './GameGateway.js'
import Game from "./Game.js";

class App extends Component {
  render() {
    return (

      <div className="App">
        <Game gameGateway={new GameGateway('http://localhost:8080')} />
      </div>
    );
  }
}


export default App;
