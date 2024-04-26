import list.memory.Memory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelMemory extends JPanel {
    private Memory memory;
    private List<String> cards;
    private JButton[] buttons;
    private int selectedIndex = -1;
    private JButton firstButton; // Pour stocker la première carte sélectionnée
    private JButton secondButton; // Pour stocker la deuxième carte sélectionnée
    private int pairsFound = 0;

    public PanelMemory() {
        setLayout(new GridLayout(4, 6));
        memory = new Memory();
        cards = memory.getCards();
        buttons = new JButton[cards.size()];
        initializeButtons();
    }

    private void initializeButtons() {
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(100, 100));
            buttons[i].addActionListener(e -> {
                JButton clickedButton = buttons[index];
                if (!clickedButton.isEnabled()) return; // Ne rien faire si le bouton est déjà désactivé
                clickedButton.setText(cards.get(index));
                if (selectedIndex == -1) {
                    selectedIndex = index;
                    firstButton = clickedButton; // Sauvegarder la première carte sélectionnée
                } else {
                    if (index != selectedIndex) {
                        secondButton = clickedButton; // Sauvegarder la deuxième carte sélectionnée
                        if (cards.get(index).equals(cards.get(selectedIndex))) {
                            // Pair found
                            firstButton.setEnabled(false);
                            secondButton.setEnabled(false);
                            pairsFound++;
                            if (pairsFound == cards.size() / 2) {
                                JOptionPane.showMessageDialog(null, "Congratulations! You found all pairs!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            // Cards don't match, keep them flipped until next selection
                            SwingUtilities.invokeLater(() -> {
                                Timer timer = new Timer(1000, event -> {
                                    firstButton.setText("");
                                    secondButton.setText("");
                                    firstButton.setEnabled(true);
                                    secondButton.setEnabled(true);
                                });
                                timer.setRepeats(false);
                                timer.start();
                            });
                        }
                        selectedIndex = -1;
                    }
                }
            });
            add(buttons[i]);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Memory Game");
        frame.getContentPane().add(new PanelMemory());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
