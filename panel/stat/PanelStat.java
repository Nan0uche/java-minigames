package panel.stat;

import panel.game.*;
import panel.config.PanelConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelStat {
    private static JFrame f; // Déclaration de la variable f en tant que variable de classe

    public static void statGame(String username) {
        f = new JFrame("Voir les statistiques"); // Initialisation de f
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 10, 10));

        addButton(panel, "+ ou -", "img/plusoumoins.png", "plusoumoins", username);
        addButton(panel, "True Or False", "img/vraioufaux.png", "vraioufaux", username);
        addButton(panel, "Jeu du Pendu", "img/hangman.png", "hangman", username);
        addButton(panel, "Memory", "img/memory.png", "memory", username);
        addButton(panel, "BlackJack", "img/Cards/BACK.png", "blackjack", username);
        addButton(panel, "Sudoku", "img/sudoku.png", "sudoku", username);
        addButton(panel, "2048", null, "2048", username);
        addButton(panel, "Snake", null, "snake", username);
        addButton(panel, "Flappy Bird", "list/flappybird/flappybird.png", "flappybird", username);
        addButton(panel, "PacMan", null, "pacman", username);
        addButton(panel, "Démineur", "img/demineur.png", "demineur", username);
        addButton(panel, "Retour", "img/back.png", "retour", username);

        f.getContentPane().add(panel);
        f.setVisible(true);
    }

    private static void addButton(JPanel panel, String text, String imagePath, String gameName, String username) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Retour")) {
                    f.dispose(); // Dispose la fenêtre avant de retourner au panneau de sélection de jeu
                    PanelGame.chooseGame(username);
                    return;
                }
                if (CheckStat.hasPlay(username, gameName)) {
                    PanelStatGame.check(username, gameName);
                } else {
                    JOptionPane.showMessageDialog(null, "Vous n'avez encore jamais joué à ce jeu.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
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
