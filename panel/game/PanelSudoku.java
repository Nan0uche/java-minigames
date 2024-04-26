package panel.game;

import db.Database;
import list.sudoku.*;
import panel.config.PanelConfigSudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class PanelSudoku extends JFrame {

	private JPanel buttonSelectionPanel;
	private SudokuPanel sPanel;
	private Timer timer;
	private int secondsPassed = 0;
	private JLabel timeLabel;
	private SudokuGame sudokuGame;
	private static String username;

	public PanelSudoku() {
		this.setTitle("Sudoku");
		this.setSize(1200, 750);
		this.setLocationRelativeTo(null);

		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(90, 500));

		sPanel = new SudokuPanel();

		timeLabel = new JLabel("Temps écoulé: 0 secondes", SwingConstants.CENTER);
		timeLabel.setPreferredSize(new Dimension(800, 30));

		JButton validerButton = new JButton("Valider");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(validerButton);

		JPanel windowPanel = new JPanel(new BorderLayout());
		windowPanel.add(sPanel, BorderLayout.CENTER);
		windowPanel.add(buttonSelectionPanel, BorderLayout.WEST);
		windowPanel.add(timeLabel, BorderLayout.NORTH);
		windowPanel.add(buttonPanel, BorderLayout.SOUTH);
		validerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateAndCheckSolution();
			}
		});
		this.add(windowPanel);

		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				secondsPassed++;
				timeLabel.setText("Temps écoulé: " + secondsPassed + " secondes");
			}
		});

		sudokuGame = new SudokuGame(new SudokuGenerator().generateRandomSudoku(PanelConfigSudoku.getSelectedPuzzleType()));
		sPanel.newSudokuPuzzle(sudokuGame.getPuzzle());
		rebuildInterface(26);
		timer.start();
	}

	private void validateAndCheckSolution() {
		sudokuGame.setPuzzle(sPanel.getPuzzle());
		if (checkSolution()) {
			timer.stop(); // Arrêter le timer
			if(PanelConfigSudoku.getSelectedPuzzleType() == SudokuPuzzleType.NINEBYNINE) {
				Database.addScore(username, 1000-secondsPassed/3, secondsPassed, "sudoku");
			} else if (PanelConfigSudoku.getSelectedPuzzleType() == SudokuPuzzleType.SIXBYSIX) {
				Database.addScore(username, 600-secondsPassed/2, secondsPassed, "sudoku");
			}
			JOptionPane.showMessageDialog(this, "Félicitations! Vous avez résolu le Sudoku avec succès.", "Sudoku Résolu", JOptionPane.INFORMATION_MESSAGE);
			dispose(); // Fermer la fenêtre
		} else {
			JOptionPane.showMessageDialog(this, "Il semble y avoir des erreurs dans votre solution. Veuillez vérifier à nouveau.", "Sudoku Incorrect", JOptionPane.ERROR_MESSAGE);
		}
	}



	public void rebuildInterface(int fontSize) {
		sPanel.setFontSize(fontSize);
		buttonSelectionPanel.removeAll();
		for (String value : sudokuGame.getPuzzle().getValidValues()) {
			JButton b = new JButton(value);
			b.setPreferredSize(new Dimension(40, 40));
			b.addActionListener(sPanel.new NumActionListener());
			buttonSelectionPanel.add(b);
		}
		sPanel.repaint();
		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();
	}

	public boolean checkSolution() {
		SudokuPuzzle puzzle = sudokuGame.getPuzzle();
		if (puzzle == null) {
			return false; // Retourner false si le puzzle est null
		}

		int numRows = puzzle.getNumRows();
		int numCols = puzzle.getNumColumns();
		int boxWidth = puzzle.getBoxWidth();
		int boxHeight = puzzle.getBoxHeight();

		// Vérifier les lignes
		for (int row = 0; row < numRows; row++) {
			if (!checkGroup(row, 0, 0, 1, numRows, numCols, boxWidth, boxHeight)) {
				return false;
			}
		}

		// Vérifier les colonnes
		for (int col = 0; col < numCols; col++) {
			if (!checkGroup(0, col, 1, 0, numRows, numCols, boxWidth, boxHeight)) {
				return false;
			}
		}

		// Vérifier les boîtes
		for (int startRow = 0; startRow < numRows; startRow += boxHeight) {
			for (int startCol = 0; startCol < numCols; startCol += boxWidth) {
				if (!checkGroup(startRow, startCol, 1, 1, numRows, numCols, boxWidth, boxHeight)) {
					return false;
				}
			}
		}

		// Vérifier les cases vides
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				String value = puzzle.getValue(row, col);
				if (value.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}


	private boolean checkGroup(int startRow, int startCol, int rowIncrement, int colIncrement, int numRows, int numCols, int boxWidth, int boxHeight) {
		SudokuPuzzle puzzle = sudokuGame.getPuzzle();
		if (puzzle == null) {
			return false;
		}

		for (int row = startRow; row < Math.min(startRow + boxHeight, numRows); row++) {
			Set<String> foundValues = new HashSet<>();
			for (int col = startCol; col < Math.min(startCol + boxWidth, numCols); col++) {
				String value = puzzle.getValue(row, col);
				if (!value.isEmpty()) {
					if (foundValues.contains(value)) {
						return false; // Retourner false si la valeur est déjà trouvée dans le groupe
					}
					foundValues.add(value);
				}
			}
		}

		return true; // Retourner true si aucune erreur n'a été trouvée dans le groupe
	}


	private int getIndex(String value) {
		SudokuPuzzle puzzle = sudokuGame.getPuzzle();
		if (puzzle == null) {
			return -1;
		}

		String[] validValues = puzzle.getValidValues();
		for (int i = 0; i < validValues.length; i++) {
			if (validValues[i].equals(value)) {
				return i;
			}
		}
		return -1; // Valeur non trouvée
	}


	public static void play(String usernamed) {
		SwingUtilities.invokeLater(() -> {
			PanelSudoku frame = new PanelSudoku();
			frame.setVisible(true);
			username = usernamed;
		});
	}
}
