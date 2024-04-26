package panel.config;

import list.trueorfalse.TrueFalseGameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelConfigVraiouFaux extends JPanel implements ActionListener {
    private JTextField questionField;
    private JRadioButton trueButton;
    private JRadioButton falseButton;
    private JButton submitButton;

    public PanelConfigVraiouFaux() {
        setLayout(new GridLayout(4, 1));

        questionField = new JTextField();
        add(new JLabel("Question:"));
        add(questionField);

        ButtonGroup buttonGroup = new ButtonGroup();
        trueButton = new JRadioButton("Vrai");
        falseButton = new JRadioButton("Faux");
        buttonGroup.add(trueButton);
        buttonGroup.add(falseButton);
        add(trueButton);
        add(falseButton);

        submitButton = new JButton("Ajouter Question");
        submitButton.addActionListener(this);
        add(submitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String questionText = questionField.getText();
            boolean answer = trueButton.isSelected();
            TrueFalseGameLogic.createQuestion(questionText, answer);
            JOptionPane.showMessageDialog(this, "Question ajoutée avec succès !");
            questionField.setText("");
            trueButton.setSelected(false);
            falseButton.setSelected(false);
        }
    }

    public static void configVraiouFaux() {
        JFrame frame = new JFrame("Configuration Vrai ou Faux");
        frame.getContentPane().add(new PanelConfigVraiouFaux());
        frame.pack();
        frame.setVisible(true);
    }
}
