package online;

import Game.Player;
import Game.GUI.GameOnline;
import Game.PLUG.online.GameOnlineSetter;

public class GameMainOnlinePlayer extends Player implements GameOnlineSetter {

    private GameClient client;
    private GameOnline gameOnline;

    public GameMainOnlinePlayer() {

    }

    // // pass client
    // public void setGameClient(GameClient client) {
    // this.client = client;
    // }

    // send message to client
    // also update the data

    @Override
    public void update() {
        super.update();
        // send message to the server
    }

    @Override
    protected void checkAttack() {

        // super.checkAttack();
    }

    @Override
    public GameOnlineSetter setGameClient(GameClient client) {
        this.client = client;
        return this;
    }

    @Override
    public GameOnlineSetter setGameOnline(GameOnline gameOnline) {
        this.gameOnline = gameOnline;
        return this;
    }

}
