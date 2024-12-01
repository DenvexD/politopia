package main;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

public class GameWindow extends JFrame{
    private GameScreen gameScreen;
    private BufferedImage img;
    private Dimension windowSize;

    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    public GameWindow(){
        importImage();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gameScreen = new GameScreen(img);
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
        windowSize = new Dimension(640, 640);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);
        setPreferredSize(windowSize);

    }

    public void initInputs(){
        myMouseListener = new MyMouseListener();
        keyboardListener = new KeyboardListener();
        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);
        requestFocus();
        
    }
}
