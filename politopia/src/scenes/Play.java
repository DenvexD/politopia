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
    public void mouseWheelMoved(double rotation){
        if (rotation > 0){
            myBoard.adjustBoardSize(1, 1);
        }
        if (rotation < 0) {
            myBoard.adjustBoardSize(-1, -1);
        }

    }
    private void handleIntersection(){
        int adjusmentX = 0;
        int adjustmentY = 0;
        int rightBoarder = myBoard.getX() - myBoard.getWidth()/3;
        int leftBoarder = myBoard.getX() + myBoard.getWidth()/3;
        int highBoarder = myBoard.getY() - myBoard.getHeight()/3;
        int bottomBoarder = myBoard.getY() + myBoard.getHeight()/3;
        if (rightBoarder > 640) {
            adjusmentX = 640 - rightBoarder;  
        }
        if (leftBoarder < 0) {
            adjusmentX = -leftBoarder;
        }
        if (highBoarder > 640) {
            adjustmentY = 640 -highBoarder;
        }
        if (bottomBoarder < 0) {
            adjustmentY = -bottomBoarder;
        }
        System.out.println("x: " + myBoard.getX() + " y: " + myBoard.getY());
        myBoard.adjustBoardCoordinates(-adjusmentX, -adjustmentY);
    }
}
