package Game.PLUG.online;

import Game.GUI.GameOnline;
import online.GameClient;

public interface GameOnlineSetter {
    public GameOnlineSetter setGameClient(GameClient client);

    public GameOnlineSetter setGameOnline(GameOnline gameOnline);
}
