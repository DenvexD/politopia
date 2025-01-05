package scenes;

import java.awt.Graphics;

import main.Game;

public class Play extends GameScene implements scenesMethods {
    private int xFieldDimensions = 640; 
    private int yFieldDimensions = 640;
    private int xScreenDimensions = 640;
    private int ySreenDimensions = 640;

    public Play(Game game){
        super(game);
        initFields();
    }

    @Override
    public void render(Graphics g) {
        g.fillRect(0, 0, 640, 640);
    }
    private void initFields(){

    }

}
