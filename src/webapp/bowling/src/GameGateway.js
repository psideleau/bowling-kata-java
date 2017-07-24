
import $ from 'jquery'

export default class GameGateway  {
    constructor(protocolAndHost) {
        this.protocolAndHost = protocolAndHost;
    }
    startGame(callback) {
        $.ajax({type: 'PUT',
                url: this.protocolAndHost + "/games",
                success: function (data) {
                    callback(data);
                },
                dataType: 'json'});
    }

    rollPins(gameId, pins, callback) {
        $.post(this.protocolAndHost + "/games/" + gameId  + "?pins=" + pins, function (data) {
            callback(data);
        }, 'json');
    }
}



