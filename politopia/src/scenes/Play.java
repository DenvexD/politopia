package scenes;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import objects.Board;
import java.awt.Image;

public class Play extends GameScene implements scenesMethods {
    private Board myBoard;
    private Image img;
    private int mousePositionX;
    private int mousePositionY;

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
        myBoard = new Board(5, 5, getGame());
    }
    public void mouseDragged(int newPositionX, int newPositionY){
        int adjustX =  this.mousePositionX - newPositionX;
        int adjustY = this.mousePositionY - newPositionY;
        myBoard.adjustBoardCoordinates(adjustX, adjustY);
        this.mousePositionX = newPositionX;
        this.mousePositionY = newPositionY;
        this.handleIntersection();
    }

    public void mousePressed(int x, int y){
        this.mousePositionX = x;
        this.mousePositionY = y;
    }
    private void handleIntersection(){
        int adjusmentX = 0;
        int adjustmentY = 0;
        if (myBoard.getX() > 640) {
            adjusmentX = 640 - myBoard.getX();
        }
        if (myBoard.getX() < 0) {
            adjusmentX = -myBoard.getX();
        }
        if (myBoard.getY() > 640) {
            adjustmentY = 640 - myBoard.getY();
        }
        if (myBoard.getY() < 0) {
            adjustmentY = -myBoard.getY();
        }
        System.out.println("x: " + myBoard.getX() + " y: " + myBoard.getY());
        myBoard.adjustBoardCoordinates(adjusmentX, adjustmentY);
    }
}
