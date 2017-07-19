import React, { Component } from 'react';
import Frame from './Frame.js'

class Frames extends Component {
    constructor(props) {
        super(props);
        this.gameGateway = props.gameGateway
        this.state = {frames: []};
    }

    componentDidMount() {
        const response = this.gameGateway.startGame();
        this.setState({frames: response.frames, gameId: response.gameId});
    }

    render() {
        const frameComponents = this.state.frames.map((frame, i) => {
            return <Frame key={'x' + i} frame={frame} />
        });

        return (
          <div>
                {frameComponents}
          </div>
        );
     }

    updateFrames(frames) {
        this.setState = {frames: frames}
    }
}

export default Frames;
