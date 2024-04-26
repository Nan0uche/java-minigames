package panel.stat;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PanelStatPlusouMoins extends JPanel {
    private JLabel totalPlayTimeLabel;
    private JLabel gamesPlayedLabel;
    private JLabel bestScoreLabel;
    private JLabel worstScoreLabel;
    private JLabel averageScoreLabel;
    private String table = "plusoumoins";

    public PanelStatPlusouMoins(String username) {
        setLayout(new GridLayout(5, 1));

        totalPlayTimeLabel = new JLabel("Temps de jeu total : " + CheckStat.getTotalPlayTime(username, table));
        gamesPlayedLabel = new JLabel("Nombre de parties jouées : " + CheckStat.getGamesPlayed(username, table));
        bestScoreLabel = new JLabel("Meilleur score : " + CheckStat.getBestScore(username, table));
        worstScoreLabel = new JLabel("Pire score : " + CheckStat.getWorstScore(username, table));
        averageScoreLabel = new JLabel("Moyenne de score : " + CheckStat.getAverageScore(username, table));

        add(totalPlayTimeLabel);
        add(gamesPlayedLabel);
        add(bestScoreLabel);
        add(worstScoreLabel);
        add(averageScoreLabel);

    }

    public static void check(String username) {
        JFrame frame = new JFrame("Statistiques du jeu Plus ou Moins");
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 300);

        PanelStatPlusouMoins statsPanel = new PanelStatPlusouMoins(username);
        frame.add(statsPanel);

        frame.setVisible(true);
    }
}