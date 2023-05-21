package online;

import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class GameClientTest {
    public static final Logger LOGGER = Logger.getLogger(GameClientTest.class.getName());

    private static final int port = 7000;

    private static final String IP = "127.0.0.70";

    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");
        GameClient client = new GameClient(IP, port, "KY");
        client.sendMessagePlug("hello world");

        var threadPool = Executors.newFixedThreadPool(2);
        threadPool.execute(client.listenedServer());
        threadPool.execute(client.sendMessageConsole());
        threadPool.shutdown();
        LOGGER.info("open client");
    }
}
