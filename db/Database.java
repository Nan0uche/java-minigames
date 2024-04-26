package db;

import java.sql.*;

public class Database {
    public static void connectToDB() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/minigames", "root", "root"
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS plusoumoins (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS flappybird (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS vraioufaux (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS vraioufauxquestion (id INT AUTO_INCREMENT PRIMARY KEY,reponse BOOLEAN, question VARCHAR(255))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS hangman (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS sudoku (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            connection.close();
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }
    public static void registerUser(String username) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/minigames", "root", "root"
            );

            // Vérifier si l'utilisateur existe déjà dans la table users
            PreparedStatement checkUserStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkUserStatement.setString(1, username);
            ResultSet resultSet = checkUserStatement.executeQuery();

            if (!resultSet.next()) {
                // Si l'utilisateur n'existe pas, l'insérer dans les tables users et plusoumoins
                PreparedStatement insertUserStatement = connection.prepareStatement("INSERT INTO users (username) VALUES (?)");
                insertUserStatement.setString(1, username);
                insertUserStatement.executeUpdate();

                PreparedStatement insertPlusouMoinsStatement = connection.prepareStatement("INSERT INTO plusoumoins (username, score, timeplayed) VALUES (?,?, ?)");
                insertPlusouMoinsStatement.setString(1, username);
                insertPlusouMoinsStatement.setString(2, "[]");
                insertPlusouMoinsStatement.setString(3, "0");
                insertPlusouMoinsStatement.executeUpdate();

                PreparedStatement insertFlappyBirdStatement = connection.prepareStatement("INSERT INTO flappybird (username, score, timeplayed) VALUES (?,?, ?)");
                insertFlappyBirdStatement.setString(1, username);
                insertFlappyBirdStatement.setString(2, "[]");
                insertFlappyBirdStatement.setString(3, "0");
                insertFlappyBirdStatement.executeUpdate();

                PreparedStatement insertVraiouFauxStatement = connection.prepareStatement("INSERT INTO vraioufaux (username, score, timeplayed) VALUES (?,?, ?)");
                insertVraiouFauxStatement.setString(1, username);
                insertVraiouFauxStatement.setString(2, "[]");
                insertVraiouFauxStatement.setString(3, "0");
                insertVraiouFauxStatement.executeUpdate();

                PreparedStatement insertHangmanStatement = connection.prepareStatement("INSERT INTO hangman (username, score, timeplayed) VALUES (?,?, ?)");
                insertHangmanStatement.setString(1, username);
                insertHangmanStatement.setString(2, "[]");
                insertHangmanStatement.setString(3, "0");
                insertHangmanStatement.executeUpdate();

                PreparedStatement insertSudokuStatement = connection.prepareStatement("INSERT INTO sudoku (username, score, timeplayed) VALUES (?,?, ?)");
                insertSudokuStatement.setString(1, username);
                insertSudokuStatement.setString(2, "[]");
                insertSudokuStatement.setString(3, "0");
                insertSudokuStatement.executeUpdate();
            }

            connection.close();
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();
        } catch (Exception e) {
            // Gérer les autres exceptions
            e.printStackTrace();
        }
    }

    public static void addScore(String username, int score, int timePlayed, String table) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/minigames", "root", "root"
            );

            // Récupérer le temps de jeu actuel
            PreparedStatement selectTimeStatement = connection.prepareStatement("SELECT timeplayed FROM " + table + " WHERE username = ?");
            selectTimeStatement.setString(1, username);
            ResultSet timeResultSet = selectTimeStatement.executeQuery();
            timeResultSet.next();
            int currentTimePlayed = timeResultSet.getInt("timeplayed");

            // Mettre à jour le score et ajouter le nouveau temps de jeu à celui existant
            String query = "UPDATE " + table + " SET score = JSON_ARRAY_APPEND(score, '$', ?), timeplayed = ? WHERE username = ?";
            PreparedStatement updateScoreStatement = connection.prepareStatement(query);
            updateScoreStatement.setInt(1, score);
            updateScoreStatement.setInt(2, currentTimePlayed + timePlayed);
            updateScoreStatement.setString(3, username);
            updateScoreStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
