import React, { Component } from 'react';
import Frame from './Frame.js'

class Game extends Component {
    constructor(props) {
        super(props);
        this.gameGateway = props.gameGateway;
        console.log("THE GATEWAY IS2" + this.gameGateway + props.toString());
        this.pins = '0';
        this.state = {frames: [], gameId: '0'};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        const response = this.gameGateway.startGame();
        this.setState({frames: response.frames, gameId: response.gameId});
    }

    handleChange(event) {
        this.pins = event.target.value;
    }

    handleSubmit(event) {
        const updatedGame = this.gameGateway.rollPins(this.state.gameId, this.pins);
        console.log("updated game is", updatedGame);
        this.setState({frames: updatedGame, gameId: this.state.gameId});
        event.preventDefault();
    }

    render() {
        const frameComponents = this.state.frames.map((frame, i) => {
            return <Frame key={'x' + i} frame={frame} />
        });

        return (
          <div>
                {frameComponents}
                <div id="driver">
                    <form onSubmit={this.handleSubmit}>
                        <input type="number" id="pins" onChange={this.handleChange} name="pins" min="0" max="10" />
                        <button id="roll" type="submit">ROLL</button>
                    </form>
                </div>
          </div>
        );
     }

    updateFrames(frames) {
        this.setState = {frames: frames}
    }
}

export default Game;
