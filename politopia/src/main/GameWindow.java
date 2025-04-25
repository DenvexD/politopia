package main;
import java.awt.Dimension;
import javax.swing.JFrame;



public class GameWindow extends JFrame{
    private GameScreen gameScreen;
    private Dimension windowSize;


    public GameWindow(Game game){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTheWindowSize(game.getWindowWidth(), game.getWindowHeight());
        setLocationRelativeTo(null);
        gameScreen = new GameScreen(game);
        add(gameScreen);
        setVisible(true);

    }
    private void setTheWindowSize(int width, int height){
        windowSize = new Dimension(width, height);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);
        setPreferredSize(windowSize);

    }

    public void initInputs(){
        gameScreen.initInputs();
    }
}
