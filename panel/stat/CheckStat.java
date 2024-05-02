package panel.stat;

import db.Database;

import java.sql.*;

public class CheckStat {
    public static boolean hasPlay(String username, String table) {
        int totalPlayTimeSeconds = 0;
        try (Connection connection = DriverManager.getConnection(Database.getURL(), Database.getUser(), Database.getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT SUM(timeplayed) AS total_play_time FROM " + table + " WHERE username = ?")) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalPlayTimeSeconds = resultSet.getInt("total_play_time");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPlayTimeSeconds > 0;
    }

    public static String getTotalPlayTime(String username, String table) {
        int totalPlayTimeSeconds = 0;
        try (Connection connection = DriverManager.getConnection(Database.getURL(), Database.getUser(), Database.getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT SUM(timeplayed) AS total_play_time FROM " + table + " WHERE username = ?")) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalPlayTimeSeconds = resultSet.getInt("total_play_time");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Convertir les secondes en heures, minutes et secondes
        int hours = totalPlayTimeSeconds / 3600;
        int minutes = (totalPlayTimeSeconds % 3600) / 60;
        int seconds = totalPlayTimeSeconds % 60;

        // Construire la chaîne de caractères formatée
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    public static int getGamesPlayed(String username, String table) {
        int gamesPlayed = 0;
        try (Connection connection = DriverManager.getConnection(Database.getURL(), Database.getUser(), Database.getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT score FROM " + table + " WHERE username = ?")) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String scoreJSON = resultSet.getString("score");
                // Vérifier si le score est non nul et non vide
                if (scoreJSON != null && !scoreJSON.isEmpty()) {
                    // Supprimer les crochets pour obtenir les éléments individuels
                    scoreJSON = scoreJSON.substring(1, scoreJSON.length() - 1);
                    // Compter le nombre de virgules pour déterminer le nombre de jeux joués
                    gamesPlayed += scoreJSON.split(",").length;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gamesPlayed;
    }

    public static int getBestScore(String username, String table) {
        int bestScore = 0;
        try (Connection connection = DriverManager.getConnection(Database.getURL(), Database.getUser(), Database.getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT score FROM " + table + " WHERE username = ?")) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String scoreJSON = resultSet.getString("score");
                if (scoreJSON != null && !scoreJSON.isEmpty()) {
                    // Supprimer les crochets pour obtenir les éléments individuels
                    scoreJSON = scoreJSON.substring(1, scoreJSON.length() - 1);
                    // Diviser la chaîne en tableau en utilisant la virgule comme délimiteur
                    String[] scoreArray = scoreJSON.split(",");
                    // Convertir chaque élément en entier et trouver le maximum
                    for (String scoreStr : scoreArray) {
                        int score = Integer.parseInt(scoreStr.trim());
                        if (score > bestScore) {
                            bestScore = score;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bestScore;
    }

    public static int getWorstScore(String username, String table) {
        int worstScore = Integer.MAX_VALUE; // Initialiser avec une valeur maximale pour trouver le minimum

        try (Connection connection = DriverManager.getConnection(Database.getURL(), Database.getUser(), Database.getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT score FROM " + table + " WHERE username = ?")) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String scoreJSON = resultSet.getString("score");
                if (scoreJSON != null && !scoreJSON.isEmpty()) {
                    // Supprimer les crochets pour obtenir les éléments individuels
                    scoreJSON = scoreJSON.substring(1, scoreJSON.length() - 1);
                    // Diviser la chaîne en tableau en utilisant la virgule comme délimiteur
                    String[] scoreArray = scoreJSON.split(",");
                    // Convertir chaque élément en entier et trouver le minimum
                    for (String scoreStr : scoreArray) {
                        int score = Integer.parseInt(scoreStr.trim());
                        if (score < worstScore) {
                            worstScore = score;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return worstScore;
    }

    public static int getAverageScore(String username, String table) {
        int totalScore = 0;
        int count = 0;

        try (Connection connection = DriverManager.getConnection(Database.getURL(), Database.getUser(), Database.getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT score FROM " + table + " WHERE username = ?")) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String scoreJSON = resultSet.getString("score");
                if (scoreJSON != null && !scoreJSON.isEmpty()) {
                    // Supprimer les crochets pour obtenir les éléments individuels
                    scoreJSON = scoreJSON.substring(1, scoreJSON.length() - 1);
                    // Diviser la chaîne en tableau en utilisant la virgule comme délimiteur
                    String[] scoreArray = scoreJSON.split(",");
                    // Convertir chaque élément en entier et ajouter à la somme totale
                    for (String scoreStr : scoreArray) {
                        int score = Integer.parseInt(scoreStr.trim());
                        totalScore += score;
                        count++;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calculer la moyenne si des scores ont été trouvés
        if (count > 0) {
            // Calculer la moyenne arrondie
            return Math.round((float) totalScore / count);
        } else {
            return 0; // Retourner 0 si aucun score trouvé pour éviter une division par zéro
        }
    }
}
