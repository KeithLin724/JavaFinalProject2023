package main;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow() {

    }

    public void init(GamePanel gamePanel) {
        setVisible(true);
        add(gamePanel);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
