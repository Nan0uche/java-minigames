package panel.config;

import panel.game.PanelGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelConfig {
    public static void configGame(String username) {
        JFrame fcustom = new JFrame("Configuration des Jeux");
        fcustom.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fcustom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pannelcustom = new JPanel();
        pannelcustom.setBorder(new EmptyBorder(20, 20, 20, 20));
        pannelcustom.setLayout(new GridLayout(0, 2, 20, 20));

        ButtonGroup groupecustom = new ButtonGroup();

        addButton(pannelcustom, "Jeu du Pendu", "img/hangman.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelConfigHangman.configHangman();
            }
        });
        addButton(pannelcustom, "Vrai ou Faux", "img/vraioufaux.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelConfigVraiouFaux.configVraiouFaux();
            }
        });

        JButton bouton4 = new JButton("Memory");
        groupecustom.add(bouton4);
        pannelcustom.add(bouton4);
        JButton bouton5 = new JButton("BlackJack");
        groupecustom.add(bouton5);
        pannelcustom.add(bouton5);
        addButton(pannelcustom, "Sudoku", "img/sudoku.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelConfigSudoku.configSudoku(username);
            }
        });
        JButton bouton7 = new JButton("2048");
        groupecustom.add(bouton7);
        pannelcustom.add(bouton7);
        JButton bouton8 = new JButton("Snake");
        groupecustom.add(bouton8);
        pannelcustom.add(bouton8);
        JButton bouton10 = new JButton("PacMan");
        groupecustom.add(bouton10);
        pannelcustom.add(bouton10);
        addButton(pannelcustom, "Retour", "img/back.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fcustom.dispose();
                PanelGame.chooseGame(username);
            }
        });

        fcustom.getContentPane().add(pannelcustom);
        fcustom.setVisible(true);
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