package db;

import list.trueorfalse.TrueFalseGameLogic;

import java.sql.*;

public class Database {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/minigames";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    public static void connectToDB() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS plusoumoins (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS flappybird (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS vraioufaux (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS hangman (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS sudoku (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS memory (username VARCHAR(255) PRIMARY KEY, score JSON, timeplayed int)");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS vraioufauxquestion (id INT AUTO_INCREMENT PRIMARY KEY,reponse BOOLEAN, question VARCHAR(255))");

            if(isTableEmpty("vraioufauxquestion")){
                TrueFalseGameLogic.createQuestion("La Cité du Vatican est un pays.", true);
                TrueFalseGameLogic.createQuestion("Melbourne est la capitale de l'Australie.", false);
                TrueFalseGameLogic.createQuestion("Le mont Fuji est la plus haute montagne du Japon.", true);
                TrueFalseGameLogic.createQuestion("Le crâne est l'os le plus solide du corps humain.", false);
                TrueFalseGameLogic.createQuestion("Les ampoules électriques ont été inventées par Thomas Edison.", false);
                TrueFalseGameLogic.createQuestion("Google s'appelait initialement BackRub.", true);
                TrueFalseGameLogic.createQuestion("La boîte noire dans un avion est noire..", false);
                TrueFalseGameLogic.createQuestion("Cléopâtre était d'origine égyptienne..", false);
                TrueFalseGameLogic.createQuestion("Les bananes sont des baies.", true);
                TrueFalseGameLogic.createQuestion("Votre nez produit près d'un litre de mucus par jour.", true);
                TrueFalseGameLogic.createQuestion("Une noix de coco est une noix.", false);
                TrueFalseGameLogic.createQuestion("Dans l'Ohio, aux États-Unis, il est illégal de saouler un poisson.", false);
            }

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

                PreparedStatement insertMemoryStatement = connection.prepareStatement("INSERT INTO memory (username, score, timeplayed) VALUES (?,?, ?)");
                insertMemoryStatement.setString(1, username);
                insertMemoryStatement.setString(2, "[]");
                insertMemoryStatement.setString(3, "0");
                insertMemoryStatement.executeUpdate();
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

    public static boolean isTableEmpty(String tableName) {
        boolean isEmpty = true;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/minigames", "root", "root")) {
            String query = "SELECT COUNT(*) AS count FROM " + tableName;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    int rowCount = resultSet.getInt("count");
                    isEmpty = (rowCount == 0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEmpty;
    }

    public static String getURL(){
        return URL;
    }
    public static String getUser(){
        return USERNAME;
    }
    public static String getPassword(){
        return PASSWORD;
    }

}
