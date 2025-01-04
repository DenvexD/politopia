package main;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;



public class GameWindow extends JFrame{
    private GameScreen gameScreen;
    private BufferedImage img;
    private Dimension windowSize;


    private Game game;
    private static int height = 640;
    private static int width = 640;

    public GameWindow(Game game){
        this.game = game;
        importImage();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gameScreen = new GameScreen(img, this.game);
        add(gameScreen);
        setVisible(true);
        setTheWindowSize();
    }
    public void importImage(){
        InputStream is = getClass().getResourceAsStream("res\\spriteatlas.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
