package main;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
    private GameScreen gameScreen;
    public GameWindow(){
        
        setSize(200,200);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gameScreen = new GameScreen();
        add(gameScreen);
    }

}

