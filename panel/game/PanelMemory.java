package panel.game;

import db.Database;
import list.memory.Card;
import list.memory.Memory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

class PanelMemory extends JPanel {
    private Memory memory;
    private List<Card> cards;
    private JButton[] buttons;
    private int selectedIndex = -1;
    private JButton firstButton;
    private JButton secondButton;
    private int pairsFound = 0;
    private int secondsElapsed = 0;
    private JLabel timerLabel;
    private JLabel attemptsLabel;
    private Timer timer;
    private boolean isPair = true;
    private int tentative = 0;

    public PanelMemory(String username) {
        this.setLayout(new BorderLayout());
        JPanel infoPanel = new JPanel(new BorderLayout());

        attemptsLabel = new JLabel("Tentatives : 0");
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(attemptsLabel);
        infoPanel.add(leftPanel, BorderLayout.WEST);

        timerLabel = new JLabel("Temps écoulé : 0 seconde");
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(timerLabel);
        infoPanel.add(rightPanel, BorderLayout.EAST);

        this.add(infoPanel, BorderLayout.NORTH);

        memory = new Memory();
        cards = memory.getCards();
        buttons = new JButton[cards.size()];
        initializeButtons(username);

        // Créer et démarrer le timer pour mettre à jour le temps écoulé
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsElapsed++;
                timerLabel.setText("Temps écoulé : " + secondsElapsed + " seconde(s)");
            }
        });
        timer.start();
    }

    private void initializeButtons(String username) {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 6));
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(100, 100));
            buttons[i].addActionListener(e -> {
                if (!isPair) {
                    firstButton.setIcon(null);
                    secondButton.setIcon(null);
                    firstButton.setEnabled(true);
                    secondButton.setEnabled(true);
                }
                isPair = true;
                JButton clickedButton = buttons[index];
                if (!clickedButton.isEnabled()) return; // Ne rien faire si le bouton est déjà désactivé
                String imagePath = cards.get(index).getImagePath();
                if (imagePath != null) {
                    // Charger l'image à partir du chemin du fichier
                    try {
                        BufferedImage img = ImageIO.read(new File(imagePath));
                        // Créer une ImageIcon à partir de l'image
                        ImageIcon icon = new ImageIcon(img);
                        // Redimensionner l'icône pour qu'elle s'adapte au bouton
                        Image scaledImage = icon.getImage().getScaledInstance(clickedButton.getWidth(), clickedButton.getHeight(), Image.SCALE_SMOOTH);
                        // Assigner l'icône redimensionnée au bouton
                        clickedButton.setIcon(new ImageIcon(scaledImage));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if (selectedIndex == -1) {
                    selectedIndex = index;
                    firstButton = clickedButton; // Sauvegarder la première carte sélectionnée
                } else {
                    if (index != selectedIndex) {
                        secondButton = clickedButton; // Sauvegarder la deuxième carte sélectionnée
                        if (cards.get(index).getName().equals(cards.get(selectedIndex).getName())) {
                            // Pair found
                            isPair = true;
                            firstButton.setEnabled(false);
                            secondButton.setEnabled(false);
                            pairsFound++;
                            if (pairsFound == cards.size() / 2) {
                                timer.stop(); // Arrêter le timer lorsque toutes les paires sont trouvées
                                Database.addScore(username,1000 - (tentative * 50), secondsElapsed, "memory");
                                JOptionPane.showMessageDialog(null, "Bravo ! Vous avez trouvé les paires en " + secondsElapsed + " seconde(s)!", "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
                            }
                            // Réinitialiser les boutons uniquement si une paire a été trouvée
                            firstButton = null; // Réinitialiser firstButton
                            secondButton = null; // Réinitialiser secondButton
                        } else {
                            isPair = false;
                        }
                        tentative++;
                        attemptsLabel.setText("Tentatives : " + tentative); // Mettre à jour le nombre de tentatives affiché
                        selectedIndex = -1;
                    }
                }
            });
            buttonPanel.add(buttons[i]);
        }
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void play(String username) {
        JFrame frame = new JFrame("Jeu du Memory");
        frame.setLocationRelativeTo(null);
        PanelMemory panelMemory = new PanelMemory(username);
        frame.getContentPane().add(panelMemory);
        frame.pack();
        frame.setVisible(true);
    }
}
