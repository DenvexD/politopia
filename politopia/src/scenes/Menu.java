package scenes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Game;

public class Menu extends GameScene implements scenesMethods{
    public Menu(Game game){
        super(game);
    }

    @Override
    public void render(Graphics g) {
        g.drawRect(20, 20, 20, 20);
    }
    public void mouseClicked(int X, int Y){
        System.out.println("mouse clicked at:" + X + " " +  Y);
    }
    
}
