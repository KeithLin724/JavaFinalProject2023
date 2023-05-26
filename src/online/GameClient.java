package online;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

import online.InternetBase.InternetBaseClass;
import online.InternetBase.InternetFunction;

public class GameClient extends InternetBaseClass {
    public static final Logger LOGGER = Logger.getLogger(GameClient.class.getName());

    private String userName;

    public GameClient(String IP, int port, String userName) throws UnknownHostException, IOException {
        super(new Socket(IP, port));
        this.userName = userName;
    }

    private void commandProcess(String commandStr) {
        String[] command = InternetFunction.commandSplit(commandStr);
        LOGGER.info(commandStr);
        this.sendMessagePlug(commandStr);

    }

    public Runnable listenedServer() {
        return () -> {
            String recvStr;

            try {
                while ((recvStr = recvMessagePlug()) != null) {
                    // String[] command = InternetFunction.commandSplit(recvStr);
                    commandProcess(recvStr);
                }
            } catch (IOException e) {
                close();
            }
            close();
        };
    }

    public Runnable sendMessageConsole() {
        return () -> {
            // sendMessagePlug(__userName);
            Scanner scanner = new Scanner(System.in);
            while (_sock.isConnected()) {
                String inputConsole = scanner.nextLine();
                String[] command = InternetFunction.commandSplit(inputConsole);

                if (command[0].toUpperCase().equals("QUIT")) {
                    break;
                }
                commandProcess(inputConsole);
            }
            scanner.close();
            close();
        };
    }
}
