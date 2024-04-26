package panel.stat;

import panel.game.*;
import panel.config.PanelConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelStat {
    public static void statGame(String username) {
        JFrame f = new JFrame("Voir les statistiques");
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 10, 10));

        addButton(panel, "+ ou -", "img/plusoumoins.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CheckStat.hasPlay(username, "plusoumoins")) {
                    PanelStatPlusouMoins.check(username);
                } else {
                    JOptionPane.showMessageDialog(null, "Vous n'avez encore jamais joué à ce jeu.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        addButton(panel, "True Or False", "img/vraioufaux.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CheckStat.hasPlay(username, "vraioufaux")) {
                    PanelStatVraiouFaux.check(username);
                } else {
                    JOptionPane.showMessageDialog(null, "Vous n'avez encore jamais joué à ce jeu.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        addButton(panel, "Jeu du Pendu", "img/hangman.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CheckStat.hasPlay(username, "hangman")) {
                    PanelStatHangman.check(username);
                } else {
                    JOptionPane.showMessageDialog(null, "Vous n'avez encore jamais joué à ce jeu.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        addButton(panel, "Memory", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        addButton(panel, "BlackJack", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        addButton(panel, "Sudoku", "img/sudoku.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CheckStat.hasPlay(username, "sudoku")) {
                    PanelStatSudoku.check(username);
                } else {
                    JOptionPane.showMessageDialog(null, "Vous n'avez encore jamais joué à ce jeu.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        addButton(panel, "2048", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        addButton(panel, "Snake", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        addButton(panel, "Flappy Bird", "list/flappybird/flappybird.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CheckStat.hasPlay(username, "flappybird")) {
                    PanelStatFlappyBird.check(username);
                } else {
                    JOptionPane.showMessageDialog(null, "Vous n'avez encore jamais joué à ce jeu.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        addButton(panel, "PacMan", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        addButton(panel, "Démineur", "img/demineur.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        addButton(panel, "Retour", "img/back.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                PanelGame.chooseGame(username);
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