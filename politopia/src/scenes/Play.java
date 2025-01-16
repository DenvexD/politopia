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
    private float cumulativeDeviationX;
    private float cumulativeDeviationY;
    private int mouseLastPressedPositionX;
    private int mouseLastPressedPositionY;
    private long timeMousePressed;
    private float velocityX = 0;
    private float velocityY = 0;
    private float accelerationX = 0;
    private float accelerationY = 0;
    private boolean velocityGreaterZeroX;
    private boolean velocityGreaterZeroY;

    public Play(Game game){
        super(game);
        initBoard();
    }

    @Override
    public void render(Graphics g) {
        this.moveBoardVelocity();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 640, 640);
        myBoard.draw(g);
    }

    private void initBoard(){
        myBoard = new Board(getGame().getBoardWidthInFields(), getGame().getBoardHeightInFields(), getGame());
    }
    public void mouseDragged(int newPositionX, int newPositionY){
        int adjustX =  this.mousePositionX - newPositionX;
        int adjustY = this.mousePositionY - newPositionY;
        myBoard.adjustBoardCoordinates(adjustX, adjustY);
        this.mousePositionX = newPositionX;
        this.mousePositionY = newPositionY;
        if (System.currentTimeMillis() - this.timeMousePressed > 1000) {
            this.mouseLastPressedPositionX = newPositionX;
            this.mouseLastPressedPositionY = newPositionY;
            this.timeMousePressed = System.currentTimeMillis();
        }
    }


    public void mousePressed(int x, int y){
        this.timeMousePressed = System.currentTimeMillis();
        this.mouseLastPressedPositionX = x;
        this.mouseLastPressedPositionY = y;
        this.mousePositionX = x;
        this.mousePositionY = y;
    }
    public void mouseReleased(int x, int y){
        addVelocityToTheBoard(x, y);

        handleIntersection();
    }
    private void addVelocityToTheBoard(int x, int y){
        int durationMousePressed = (int)( System.currentTimeMillis() - this.timeMousePressed);
        this.velocityX = (float)(x - this.mouseLastPressedPositionX) / (durationMousePressed / 5);
        this.velocityY = (float)(y - this.mouseLastPressedPositionY) / (durationMousePressed / 5);
        this.velocityGreaterZeroX = velocityX > 0;
        this.velocityGreaterZeroY = velocityY > 0;
        this.accelerationX = (float)-velocityX / getGame().getvelocityMovementFramesDuration();
        this.accelerationY = (float)-velocityY / getGame().getvelocityMovementFramesDuration();
    }
    private void moveBoardVelocity(){
        if (this.velocityX > 0 == this.velocityGreaterZeroX && this.velocityY > 0 == velocityGreaterZeroY) {
            myBoard.adjustBoardCoordinates((int)-this.velocityX, (int)-this.velocityY);
            this.velocityX += this.accelerationX;
            this.velocityY += this.accelerationY;
        }else{
            this.velocityX = 0;
            this.velocityY = 0;
            this.accelerationX = 0;
            this.accelerationY = 0;
        }

    }

    public void mouseWheelMoved(double rotation, int x, int y){
        int sizeChangeX = (myBoard.getWidth() / 80) * 2;
        int sizeChangeY = (myBoard.getHeight() / 80) * 2;
        if (rotation > 0){
            sizeChangeX = -sizeChangeX;
            sizeChangeY = -sizeChangeY;
        }
        myBoard.adjustBoardSize(sizeChangeX, sizeChangeY);
        adjustBoardCoordinatesOnZoom(sizeChangeX, sizeChangeY, x, y);
    }
    private void adjustBoardCoordinatesOnZoom(int sizeChangeX, int sizeChangeY, int x, int y){
        float widthRatio = myBoard.getWidth() / ((float)myBoard.getWidth() - sizeChangeX * getGame().getBoardWidthInFields());
        float heightRatio = myBoard.getHeight() / ((float)myBoard.getHeight() - sizeChangeY * getGame().getBoardHeightInFields());
        int oldDistanceX = x - myBoard.getX();
        int oldDistanceY = y - myBoard.getY();
        float adjustDistanceX = (widthRatio - 1) * oldDistanceX;
        float adjustDistanceY = (heightRatio - 1) * oldDistanceY;

        cumulativeDeviationX += adjustDistanceX - Math.round(adjustDistanceX);
        cumulativeDeviationY += adjustDistanceY - Math.round(adjustDistanceY);
        int intPartDeviationX = (int)cumulativeDeviationX;
        int intPartDeviationY = (int)cumulativeDeviationY;

        myBoard.adjustBoardCoordinates(Math.round(adjustDistanceX) + intPartDeviationX, Math.round(adjustDistanceY) + intPartDeviationY);
        cumulativeDeviationX -= intPartDeviationX;
        cumulativeDeviationY -= intPartDeviationY;
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
