package main;
import javax.swing.JPanel;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

public class GameScreen extends JPanel {
    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;


    


    public GameScreen(Game game){

    }
    

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        switch (GameStates.GameState) {
            case MENU:
                Game.getMenu().render(g2d);
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().render(g2d);
                break;
            default:
                throw new IllegalArgumentException("game state is undifined");
        }
    }

        public void initInputs(){
        myMouseListener = new MyMouseListener();
        keyboardListener = new KeyboardListener();
        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addMouseWheelListener(myMouseListener);
        addKeyListener(keyboardListener);
        requestFocus();

        
    }


}
