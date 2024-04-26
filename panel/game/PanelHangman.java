package panel.game;

import db.Database;
import panel.stat.CheckStat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PanelHangman extends JFrame {
    private List<String> mots = new ArrayList<>();
    private String motADeviner;
    private String motEnCours;
    private JLabel motLabel;
    private JTextField lettreField;
    private JPanel hangmanPanel;
    private JButton guessButton;
    private int attempt = 7;
    private JLabel healthLabel;
    private JLabel badlettersLabel;
    private JLabel timeLabel;
    private Timer timer;
    private int secondsPassed = 0;
    private static String username;

    private ImageIcon[] hangmanImages = {
            new ImageIcon("img/hangmanstate/6.png"),
            new ImageIcon("img/hangmanstate/5.png"),
            new ImageIcon("img/hangmanstate/4.png"),
            new ImageIcon("img/hangmanstate/3.png"),
            new ImageIcon("img/hangmanstate/2.png"),
            new ImageIcon("img/hangmanstate/1.png"),
            new ImageIcon("img/hangmanstate/0.png")
    };

    private ArrayList<String> badletters = new ArrayList<>();
    private ArrayList<String> triedLetters = new ArrayList<>();

    public PanelHangman() {
        setTitle("Jeu du Pendu");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        healthLabel = new JLabel("Tentatives restantes: " + attempt, SwingConstants.LEFT);
        topPanel.add(healthLabel, BorderLayout.WEST);

        timeLabel = new JLabel("Temps écoulé: 0 secondes", SwingConstants.RIGHT);
        topPanel.add(timeLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        motLabel = new JLabel("", SwingConstants.CENTER);
        add(motLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        badlettersLabel = new JLabel("Lettres incorrectes: ", SwingConstants.CENTER);
        bottomPanel.add(badlettersLabel, BorderLayout.NORTH);

        hangmanPanel = new JPanel(new FlowLayout());
        add(hangmanPanel, BorderLayout.WEST);
        afficherImagePendu();

        lettreField = new JTextField();
        bottomPanel.add(lettreField, BorderLayout.CENTER);
        lettreField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    devinerLettre();
                }
            }
        });

        guessButton = new JButton("Devinez");
        bottomPanel.add(guessButton, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        lireMotsDepuisFichier();
        choisirMotAleatoire();

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devinerLettre();
            }
        });
    }

    private void afficherImagePendu() {
        if (attempt < 7) { // Ajustez la condition
            hangmanPanel.removeAll();
            int indexImage = hangmanImages.length - attempt-1;

            if (indexImage >= 0 && indexImage < hangmanImages.length) {
                hangmanPanel.add(new JLabel(hangmanImages[indexImage]));
            }
            hangmanPanel.revalidate();
            hangmanPanel.repaint();
        }
    }



    private void lireMotsDepuisFichier() {
        try (BufferedReader br = new BufferedReader(new FileReader("list/hangman/words.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                mots.add(ligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la lecture du fichier de mots.");
            System.exit(1);
        }
    }

    private void choisirMotAleatoire() {
        Random random = new Random();
        int indexMot = random.nextInt(mots.size());
        motADeviner = mots.get(indexMot);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < motADeviner.length(); i++) {
            char c = motADeviner.charAt(i);
            if (Character.isWhitespace(c)) {
                sb.append(' ');
            } else {
                sb.append('_');
            }
        }
        motEnCours = sb.toString();

        motLabel.setText(motEnCours);
    }

    private String supprimerAccents(String texte) {
        return java.text.Normalizer.normalize(texte, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private void devinerLettre() {
        String lettre = lettreField.getText().toLowerCase();
        lettreField.setText("");

        if (lettre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vous n'avez rien saisi.");
            return;
        }

        if (!lettre.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(null, "Veuillez saisir uniquement des lettres.");
            return;
        }

        if (!triedLetters.isEmpty() && triedLetters.contains(lettre)) {
            JOptionPane.showMessageDialog(null, "Vous avez déjà saisi cette lettre.");
            return;
        }

        String motSansAccents = supprimerAccents(motADeviner);

        boolean loseAttempt = true;
        if (lettre.length() == 1) {
            triedLetters.add(lettre);
            for (int i = 0; i < motSansAccents.length(); i++) {
                if (motSansAccents.toLowerCase().charAt(i) == lettre.charAt(0)) {
                    motEnCours = motEnCours.substring(0, i) + motADeviner.charAt(i) + motEnCours.substring(i + 1);
                    loseAttempt = false;
                }
            }
        } else if (lettre.length() > 1) {
            if (lettre.equalsIgnoreCase(motSansAccents)) {
                timer.stop();
                calculateScore(secondsPassed, attempt);
                JOptionPane.showMessageDialog(null, "Bravo! Vous avez deviné le mot : " + motADeviner);
                dispose();
            }
        }

        if (loseAttempt) {
            badletters.add(lettre);
            attempt--;
            afficherImagePendu();
            badlettersLabel.setText("Lettres incorrectes: " + String.join(", ", badletters));
            healthLabel.setText("Tentatives restantes: " + attempt);
        }

        if (attempt <= 0) {
            stopTimer();
            calculateScore(secondsPassed, attempt);
            JOptionPane.showMessageDialog(null, "Perdu ! Le mot à deviner était : " + motADeviner);
            dispose();
        }

        motLabel.setText(motEnCours);
        if (motADeviner.equalsIgnoreCase(motEnCours)) {
            stopTimer();
            calculateScore(secondsPassed, attempt);
            JOptionPane.showMessageDialog(null, "Bravo! Vous avez deviné le mot : " + motADeviner);
            dispose();
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;
                timeLabel.setText("Temps écoulé: " + secondsPassed + " secondes");
            }
        });
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    public static void play(String usernamed) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PanelHangman game = new PanelHangman();
                game.setVisible(true);
                username = usernamed;
            }
        });
    }

    public static void addWord(String mot) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("list/hangman/words.txt", true))) {
            writer.write(mot);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du mot au fichier.");
        }
    }

    private void calculateScore(int secondsPassed, int tentative) {
        if(tentative > 0) {
            // Score basé sur le temps écoulé (plus le temps est court, plus le score est élevé)
            double timeScore = 1000 * (1 - ((double) secondsPassed / (2 * 60))); // 2 minutes

            // Score basé sur le nombre de tentatives (moins de tentatives, plus le score est élevé)
            double tentativesScore = 1000 * (1 - ((double) tentative / 10)); // 10 tentatives

            // Calculer le score final en prenant la moyenne des deux scores
            int score = (int) ((timeScore + tentativesScore) / 2);
            Database.addScore(username, score, secondsPassed, "hangman");
        } else {
            Database.addScore(username, 0, secondsPassed, "hangman");
        }

    }
}
