package panel.game;

import panel.config.PanelConfig;
import panel.stat.PanelStat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelGame {
    public static void chooseGame(String username) {
        JFrame f = new JFrame("Choisissez un jeu");
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 10, 10));

        addButton(panel, "+ ou -", "img/plusoumoins.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelPlusouMoins.play(username);
            }
        });
        addButton(panel, "True Or False", "img/vraioufaux.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelVraiouFaux.play(username);
            }
        });
        addButton(panel, "Jeu du Pendu", "img/hangman.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelHangman.play(username);
            }
        });
        addButton(panel, "Memory", "img/memory.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelMemory.play(username);
            }
        });
        addButton(panel, "BlackJack", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à exécuter lorsque le bouton est cliqué
            }
        });
        addButton(panel, "Sudoku", "img/sudoku.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSudoku.play(username);
            }
        });
        addButton(panel, "2048", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à exécuter lorsque le bouton est cliqué
            }
        });
        addButton(panel, "Snake", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSnake.play(username);
            }
        });
        addButton(panel, "Flappy Bird", "list/flappybird/flappybird.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelFlappyBird.play(username);
            }
        });
        addButton(panel, "PacMan", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à exécuter lorsque le bouton est cliqué
            }
        });
        addButton(panel, "Démineur", "img/demineur.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelDemineur.play(username);
            }
        });
        addButton(panel, "Configuration", "img/custom.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                PanelConfig.configGame(username);
            }
        });

        addButton(panel, "Stat", "img/stat.jpg", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                PanelStat.statGame(username);
            }
        });

        f.getContentPane().add(panel);
        f.setVisible(true);
    }

    private static void addButton(JPanel panel, String text, String imagePath, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
        }
        panel.add(button);
    }
}