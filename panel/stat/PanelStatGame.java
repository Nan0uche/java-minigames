package panel.stat;

import javax.swing.*;
import java.awt.*;

public class PanelStatGame extends JPanel {
    private JLabel totalPlayTimeLabel;
    private JLabel gamesPlayedLabel;
    private JLabel bestScoreLabel;
    private JLabel worstScoreLabel;
    private JLabel averageScoreLabel;
    private static String table;

    public PanelStatGame(String username) {
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

    public static void check(String username, String name) {
        table = name;
        JFrame frame = new JFrame("Statistiques du jeu " + name.substring(0, 1).toUpperCase() + name.substring(1));
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 300);

        PanelStatGame statsPanel = new PanelStatGame(username);
        frame.add(statsPanel);

        frame.setVisible(true);
    }
}
