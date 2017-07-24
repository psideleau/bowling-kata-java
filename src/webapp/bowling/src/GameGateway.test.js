
import GameGateway from './GameGateway'
import sinon from 'sinon'


describe('calling game gateways service', () => {
   var gameGateway;
   var server;
    beforeEach(() => {
        console.log("CREATING SERVER");
        gameGateway  = new GameGateway('');
        server = sinon.fakeServer.create();
    });

    afterEach(() => {
       server.restore();
    });

    it('should start game', () => {
        const newGame = {gameId: '1234', frames: [{roll1: 5, roll2: 4, score:9}]};
        server.respondWith(
            'PUT',
            '/games',
            [
            200,
            { "Content-Type": "application/json" },
            JSON.stringify(newGame)]);

        var callback = sinon.spy();

        gameGateway.startGame(callback);

        server.respond();
        sinon.assert.calledWith(callback, newGame);
    });

    it('should roll pins', () => {
        const game = {gameId: '1234', frames: [{roll1: 5, roll2: 4, score:9}]};
        server.respondWith(
            'POST',
            '/games/1234?pins=5',
            [
                200,
                { "Content-Type": "application/json" },
                JSON.stringify(game)]);

        var callback = sinon.spy();

        gameGateway.rollPins('1234', 5, callback);

        server.respond();
        sinon.assert.calledWith(callback, game);
    });

});

