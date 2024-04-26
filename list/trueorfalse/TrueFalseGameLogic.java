package list.trueorfalse;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class TrueFalseGameLogic {
    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/minigames";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static boolean checkAnswer(boolean answer, int questionId) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT reponse FROM vraioufauxquestion WHERE id = ?")) {
            statement.setInt(1, questionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean correctAnswer = resultSet.getBoolean("reponse");
                    return answer == correctAnswer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean hasMoreQuestions(List<Question> remainingQuestions, int score, int totalQuestions) {
        return !remainingQuestions.isEmpty() && score < totalQuestions;
    }

    public static void createQuestion(String question, boolean reponse) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO vraioufauxquestion (reponse, question) VALUES (?, ?)")) {
            statement.setString(2, question);
            statement.setBoolean(1, reponse);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
