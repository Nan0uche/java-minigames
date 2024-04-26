package panel.game;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class PanelSnake extends JFrame {

    public PanelSnake() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new list.snake.Snake());
               
        setResizable(false);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
    }
    

    public static void play(String username) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new PanelSnake();
            ex.setVisible(true);
        });
    }
}