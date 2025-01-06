package scenes;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import objects.Board;
import objects.Field;
import java.awt.Image;
import java.awt.Toolkit;

public class Play extends GameScene implements scenesMethods {
    private int xFieldDimensions = 640; 
    private int yFieldDimensions = 640;
    private int xScreenDimensions = 640;
    private int ySreenDimensions = 640;
    private Board myBoard;
    private Image img;

    public Play(Game game){
        super(game);
        System.out.println("play");
        initBoard();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 640, 640);
        myBoard.draw(g);
    }

    private void initBoard(){
        myBoard = new Board(40, 40, getGame());
    }
}
