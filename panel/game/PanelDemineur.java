package panel.game;

import list.demineur.Demineur;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Java Minesweeper Game
 *
 * Author: Jan Bodnar
 * Website: http://zetcode.com
 */

public class PanelDemineur extends JFrame {

    private JLabel statusbar;

    public PanelDemineur() {

        initUI();
    }

    private void initUI() {

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);

        add(new Demineur(statusbar));

        setResizable(false);
        pack();

        setTitle("Démineur");
        setLocationRelativeTo(null);
    }

    public static void play(String username) {

        EventQueue.invokeLater(() -> {

            var ex = new PanelDemineur();
            ex.setVisible(true);
        });
    }
}