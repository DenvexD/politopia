package main;
import javax.swing.JPanel;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import java.awt.Graphics;

public class GameScreen extends JPanel {
    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;


    


    public GameScreen(Game game){

    }
    

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        switch (GameStates.gameState) {
            case MENU:
                Game.getMenu().render(g);
        
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().render(g);
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
