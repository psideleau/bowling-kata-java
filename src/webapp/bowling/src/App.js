import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Frame from './Frame.js'

class App extends Component {
  render() {
      var frames1 = [];
      for (var i = 0; i < 10; i++) {
          frames1.push(<Frame key={'x' + i} roll1="7" roll2="2" score="9" />);
      }

      var frames2 = [];
      for (var i = 0; i < 10; i++) {
          frames2.push(<Frame key={'y' + i} roll1="6" roll2="/" score="15"  />);
      }
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to React</h2>
        </div>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
      {frames1}
      <div className="frameSeperator">test</div>
      {frames2}
      </div>

    );
  }
}


export default App;
