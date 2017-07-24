import React, { Component } from 'react';
class Frame extends Component {
    render(props) {
        return (
            <div className="frame">
                <div className="progress">
                    <span className="roll1">{this.formatRoll1()}</span>
                    <span className="roll2">{this.formatRoll2()}</span>
                </div>
                <div className="frameScore">{this.props.frame.score}</div>
            </div>
        );
    }

    formatRoll1() {
        return this.props.frame.roll1 < 10 ? this.props.frame.roll1 : 'X';
    }

    formatRoll2() {
        return this.props.frame.roll1 + this.props.frame.roll2 === 10 ? '/' : this.props.frame.roll2;
    }
}
export default Frame;