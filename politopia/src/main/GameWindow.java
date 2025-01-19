package main;
import java.awt.Dimension;
import javax.swing.JFrame;



public class GameWindow extends JFrame{
    private GameScreen gameScreen;
    private Dimension windowSize;

    private static int height = 640;
    private static int width = 640;

    public GameWindow(Game game){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gameScreen = new GameScreen(game);
        add(gameScreen);
        setVisible(true);
        setTheWindowSize();
    }
    private void setTheWindowSize(){
        windowSize = new Dimension(width, height);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);
        setPreferredSize(windowSize);

    }

    public void initInputs(){
        gameScreen.initInputs();
    }

    public static int getWindowWidth(){
        return width;
    }
    public static int getWindowHeight(){
        return height;
    }
}
