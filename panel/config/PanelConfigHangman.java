package panel.config;

import panel.game.PanelHangman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelConfigHangman {
    public static void configHangman() {
        JFrame fHangman = new JFrame("Configuration du Pendu");
        fHangman.setSize(300, 200);

        JPanel pannelHangMan = new JPanel();
        fHangman.getContentPane().add(pannelHangMan, BorderLayout.CENTER);
        pannelHangMan.setLayout(new FlowLayout()); // Utilisation de FlowLayout pour disposer les composants

        JTextField lettreField = new JTextField(20); // Création d'un JTextField avec une largeur initiale de 20 caractères
        pannelHangMan.add(lettreField); // Ajout du JTextField au panneau

        JButton addButton = new JButton("Ajouter le mot");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mot = lettreField.getText(); // Récupérer le texte saisi dans le JTextField
                PanelHangman.addWord(mot); // Ajouter le mot avec la méthode appropriée
                lettreField.setText("");
            }
        });
        pannelHangMan.add(addButton); // Ajouter le bouton au panneau
        fHangman.setVisible(true);
    }
}