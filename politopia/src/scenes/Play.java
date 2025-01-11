package scenes;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import objects.Board;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;

public class Play extends GameScene implements scenesMethods {
    private Board myBoard;
    private Image img;
    private int mousePositionX;
    private int mousePositionY;
    private PointerInfo pointerInfo = MouseInfo.getPointerInfo();
    private int centreX = 0;
    private int centreY = 0;
    private float X;
    private float Y;
    private int widthX;
    private int heightX;

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
        myBoard = new Board(20, 20, getGame());
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
    public void mouseWheelMoved(double rotation, int x, int y){
        int change = myBoard.getWidth() / 40;
        this.widthX = myBoard.getWidth();
        this.heightX = myBoard.getHeight(); 
        if (rotation > 0){
            myBoard.adjustBoardSize(-change, -change);

        }
        if (rotation < 0) {
            myBoard.adjustBoardSize(change, change);
        }
        int newWidth = myBoard.getWidth();
        int newHeight = myBoard.getHeight();
        float widthRatio = (float)newWidth / this.widthX;
        float heightRatio = (float)newHeight / this.heightX;
        int oldDistanceX = x - myBoard.getX();
        int oldDistanceY = y - myBoard.getY();
        int adjustDistanceX = Math.round((widthRatio - 1) * oldDistanceX);
        int adjustDistanceY = Math.round((heightRatio - 1) * oldDistanceY);
        float adjustDistanceXX = (widthRatio - 1) * oldDistanceX;
        float adjustDistanceYY = (heightRatio - 1) * oldDistanceY;
        X += adjustDistanceXX - adjustDistanceX;
        Y += adjustDistanceYY - adjustDistanceY;
        int realX = (int)X;
        int realY = (int)Y;

        System.out.println("X: " + realX + " Y: " + X);
        X -= realX;
        Y -= realY;
        myBoard.adjustBoardCoordinates(adjustDistanceX + realX, adjustDistanceY + realY);

            
        

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
        myBoard.adjustBoardCoordinates(-adjusmentX, -adjustmentY);
    }
}
