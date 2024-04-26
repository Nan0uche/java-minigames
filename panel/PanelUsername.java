package panel;

import db.Database;
import panel.game.PanelGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PanelUsername extends JPanel {
    private JTextField usernameField;

    public PanelUsername() {
        setLayout(new BorderLayout());

        JLabel usernameLabel = new JLabel("Entrée votre pseudo :");
        usernameField = new JTextField(20);

        JButton submitButton = new JButton("Se connecter");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitUsername();
            }
        });

        // Ajout d'un écouteur de clavier au champ de texte
        usernameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                // Si la touche Entrée est enfoncée
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitUsername();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);

        add(inputPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }

    // Méthode pour soumettre le nom d'utilisateur
    private void submitUsername() {
        SwingUtilities.getWindowAncestor(this).dispose();
        Database.registerUser(usernameField.getText());
        PanelGame.chooseGame(usernameField.getText());
    }

    public static void enterUsername() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    // Méthode pour créer et afficher l'interface utilisateur
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Pseudo");
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);

        PanelUsername panel = new PanelUsername();
        frame.add(panel);

        frame.setVisible(true);
    }
}
