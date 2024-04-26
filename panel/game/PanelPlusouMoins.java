package panel.game;

import db.Database;
import list.masomenos.PlusouMoins;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelPlusouMoins {
    private JFrame frame;
    private JTextField textField;
    private JLabel timeLabel;
    private Timer timer;
    private JLabel tentativeLabel;
    private int tentative = 0;
    private int secondsPassed = 0;
    private int NOMBRE_SECRET;
    private static String username;

    public PanelPlusouMoins() {
        NOMBRE_SECRET = PlusouMoins.genererNombreSecret();
        frame = new JFrame("Jeu du Plus ou Moins");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre au démarrage

        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        // Ajout de l'étiquette de tentative en haut à gauche
        tentativeLabel = new JLabel("Tentative : 0");
        topPanel.add(tentativeLabel, BorderLayout.WEST);

        // Ajout de l'étiquette de temps écoulé en haut à droite
        timeLabel = new JLabel("Temps écoulé: 0 seconde");
        topPanel.add(timeLabel, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel("Devinez le nombre secret entre 1 et 100 :");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Si la touche Entrée est enfoncée et le champ de texte n'est pas vide
                    if (!textField.getText().isEmpty()) {
                        guessNumber();
                    }
                }
            }
        });

        JButton guessButton = new JButton("Devinez !");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guessNumber();
            }
        });

        centerPanel.add(label);
        centerPanel.add(textField);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(guessButton, BorderLayout.SOUTH);
        frame.add(panel);
        startTimer();
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                stopTimer(); // Arrêter le timer lorsque la fenêtre est fermée
            }
        });
    }

    private void guessNumber() {
        try {
            int guess = Integer.parseInt(textField.getText());
            tentative++;
            tentativeLabel.setText("Tentative : " + tentative);
            if (textField.getText().isEmpty()) {
                // Si le champ de texte est vide, afficher un message d'erreur
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un nombre !");
            } else {
                if (guess == NOMBRE_SECRET) { // Exemple de condition fictive
                    // Si le nombre deviné est correct
                    timer.stop();
                    calculateScore(secondsPassed, tentative);
                    JOptionPane.showMessageDialog(frame, "Bravo ! Vous avez trouvé le nombre secret !");
                    frame.dispose();
                } else {
                    if (guess < NOMBRE_SECRET) { // Exemple de condition fictive
                        // Si le nombre deviné est plus petit
                        JOptionPane.showMessageDialog(frame, "Le nombre secret est plus grand !");
                    } else {
                        // Si le nombre deviné est plus grand
                        JOptionPane.showMessageDialog(frame, "Le nombre secret est plus petit !");
                    }
                }
                textField.setText(""); // Réinitialiser le champ de texte après chaque devinette
            }
        } catch (NumberFormatException ex) {
            // Si une exception NumberFormatException est levée, afficher un message d'erreur
            JOptionPane.showMessageDialog(frame, "Veuillez entrer un nombre valide !");
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Incrémenter le nombre de secondes écoulées à chaque action du timer
                secondsPassed++;
                timeLabel.setText("Temps écoulé: " + secondsPassed + " secondes");
            }
        });
        timer.start(); // Démarrer le timer
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop(); // Arrêter le timer s'il est en cours
            secondsPassed = 0; // Réinitialiser le compteur de secondes
            timeLabel.setText("Temps écoulé: 0 seconde"); // Réinitialiser l'étiquette du temps écoulé
        }
    }

    public static void play(String usernamed) {
        username = usernamed;
        PanelPlusouMoins frame = new PanelPlusouMoins();
        frame.frame.setVisible(true); // Rendre la fenêtre visible
    }

    private void calculateScore(int secondsPassed, int tentative) {
        // Score basé sur le temps écoulé (plus le temps est court, plus le score est élevé)
        double timeScore = 1000 * (1 - ((double) secondsPassed / (2 * 60))); // 2 minutes

        // Score basé sur le nombre de tentatives (moins de tentatives, plus le score est élevé)
        double tentativesScore = 1000 * (1 - ((double) tentative / 10)); // 10 tentatives

        // Calculer le score final en prenant la moyenne des deux scores
        int score = (int) ((timeScore + tentativesScore) / 2);
        Database.addScore(username, score, secondsPassed, "plusoumoins");
    }

}
