package panel.game;

import list.flappybird.FlappyBird;

import javax.swing.*;

public class PanelFlappyBird {
    public static void play(String username) {
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        // frame.setVisible(true);
		frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        FlappyBird flappyBird = new FlappyBird(username);
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}