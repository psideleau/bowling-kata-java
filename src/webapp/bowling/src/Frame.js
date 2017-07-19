import React, { Component } from 'react';
class Frame extends Component {
    render(props) {
        return (
            <div className="frame">
                <div className="progress">
                    <span className="roll1">{this.props.frame.roll1}</span>
                    <span className="roll2">{this.props.frame.roll2}</span>
                </div>
                <div className="frameScore">{this.props.frame.score}</div>
            </div>
        );
    }
}
export default Frame;