package panel.game;

import db.Database;
import list.trueorfalse.Question;
import list.trueorfalse.TrueFalseGameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PanelVraiouFaux {
    private JFrame frame;
    private JLabel questionLabel;
    private JButton trueButton;
    private JButton falseButton;
    private JLabel scoreLabel;
    private JLabel statusLabel;

    private int idquestion;
    private int score = 0;

    private List<Question> remainingQuestions;
    private int totalquestion;
    long startTime;
    long endTime;
    private static String username;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/minigames";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void play(String usernamed) {
        username = usernamed;
        PanelVraiouFaux interfaceInstance = new PanelVraiouFaux();
        interfaceInstance.createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Vrai ou Faux ?");

        JPanel mainPanel = new JPanel(new BorderLayout());

        questionLabel = new JLabel("", JLabel.CENTER);
        mainPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        trueButton = new JButton("Vrai");
        trueButton.setPreferredSize(new Dimension(150, 50));
        trueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAnswer(true);
            }
        });
        buttonsPanel.add(trueButton);

        falseButton = new JButton("Faux");
        falseButton.setPreferredSize(new Dimension(150, 50));
        falseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAnswer(false);
            }
        });
        buttonsPanel.add(falseButton);

        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        scorePanel.add(scoreLabel);

        statusLabel = new JLabel("", JLabel.CENTER);
        scorePanel.add(statusLabel);

        mainPanel.add(scorePanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setPreferredSize(new Dimension(400, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        startTime = System.currentTimeMillis();
        loadQuestions();
        displayNextQuestion();
    }

    private void loadQuestions() {
        remainingQuestions = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, reponse, question FROM vraioufauxquestion")) {
            while (resultSet.next()) {
                totalquestion++;
                int id = resultSet.getInt("id");
                String questionText = resultSet.getString("question");
                boolean answer = resultSet.getBoolean("reponse");
                Question question = new Question(id, questionText, answer);
                remainingQuestions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void handleAnswer(boolean answer) {
        boolean result = TrueFalseGameLogic.checkAnswer(answer, idquestion);
        if (result) {
            score++;
            scoreLabel.setText("Score : " + score);
        }
        displayNextQuestion();
    }

    private void displayNextQuestion() {
        Question question = getNextQuestion(remainingQuestions);
        if (question != null) {
            questionLabel.setText("<html><div style='text-align: center;'>" + question.getText() + "</div></html>");
            statusLabel.setText("");
            idquestion = question.getId();

            // Supprimer la question affichée de la liste remainingQuestions
            remainingQuestions.remove(question);
        } else {
            endGame();
        }
    }


    public static Question getNextQuestion(List<Question> remainingQuestions) {
        if (!remainingQuestions.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(remainingQuestions.size());
            return remainingQuestions.get(randomIndex); // Retourne une question aléatoire sans la retirer de la liste
        }
        return null;
    }

    private void endGame() {
        if(score >= totalquestion/2) {
            questionLabel.setText("<html><div style='text-align: center;'>Gagné !</div></html>");
        } else {
            questionLabel.setText("<html><div style='text-align: center;'>Perdu !</div></html>");
        }
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
        endTime = System.currentTimeMillis();
        int totalTimeInMillis = (int) (endTime - startTime);
        totalTimeInMillis = totalTimeInMillis / 1000;
        Database.addScore(username, score, totalTimeInMillis, "vraioufaux");
    }
}
