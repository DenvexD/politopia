package scenes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import ui.Button;
import main.GameWindow;

import main.Game;



public class Menu extends GameScene implements scenesMethods{
    private Button startButton;
    public Menu(Game game){
        super(game);
        startButton = new Button("start", 100, 50);
    }

    @Override
    public void render(Graphics g) {
        startButton.draw(g, getCentreX(), getCentreY());
    }
    public void mouseClicked(int X, int Y){
        System.out.println("mouse clicked at:" + X + " " +  Y);
    }

    private int getCentreX(){
        return GameWindow.getWindowWidth() / 2 - startButton.getWidth() / 2;
    }
    private int getCentreY(){
        return GameWindow.getWindowHeight() / 2 - startButton.getHeight() / 2;
    }
    
}
