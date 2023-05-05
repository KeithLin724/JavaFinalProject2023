package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow() {

    }

    public void init(GamePanel gamePanel) {
        this.setVisible(true);
        this.add(gamePanel);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowFocusListener(settingWindowFocusListener(gamePanel));
    }

    private WindowFocusListener settingWindowFocusListener(GamePanel gamePanel) {
        return new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {

                // throw new UnsupportedOperationException("Unimplemented method
                // 'windowGainedFocus'");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowLostFocus();
            }

        };
    }

}
