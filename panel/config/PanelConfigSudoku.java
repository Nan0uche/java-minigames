package panel.config;

import list.sudoku.SudokuPuzzleType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelConfigSudoku {

    private static SudokuPuzzleType selectedPuzzleType = SudokuPuzzleType.NINEBYNINE;

    // Méthode pour obtenir le type de puzzle sélectionné
    public static SudokuPuzzleType getSelectedPuzzleType() {
        return selectedPuzzleType;
    }

    // Méthode pour définir le type de puzzle sélectionné
    public static void setSelectedPuzzleType(SudokuPuzzleType puzzleType) {
        selectedPuzzleType = puzzleType;
    }
    public static void configSudoku(String username) {
        JFrame fSudokuConfig = new JFrame("Configuration Sudoku");
        fSudokuConfig.setSize(300, 200);

        JPanel panelSudokuConfig = new JPanel();
        fSudokuConfig.getContentPane().add(panelSudokuConfig, BorderLayout.CENTER);
        panelSudokuConfig.setLayout(new FlowLayout());

        // Création d'un groupe de boutons radio pour les choix de taille de Sudoku
        ButtonGroup group = new ButtonGroup();

        JRadioButton radioButton6x6 = new JRadioButton("6x6");
        JRadioButton radioButton9x9 = new JRadioButton("9x9");

        group.add(radioButton6x6);
        group.add(radioButton9x9);

        panelSudokuConfig.add(radioButton6x6);
        panelSudokuConfig.add(radioButton9x9);

        JButton validateButton = new JButton("Valider");

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SudokuPuzzleType puzzleType = SudokuPuzzleType.NINEBYNINE;
                if (radioButton6x6.isSelected()) {
                    puzzleType = SudokuPuzzleType.SIXBYSIX;
                } else if (radioButton9x9.isSelected()) {
                    puzzleType = SudokuPuzzleType.NINEBYNINE;
                }
                setSelectedPuzzleType(puzzleType); // Stocker le type de puzzle sélectionné
                fSudokuConfig.dispose();
                PanelConfig.configGame(username);
            }
        });

        panelSudokuConfig.add(validateButton);

        fSudokuConfig.setVisible(true);
    }
}