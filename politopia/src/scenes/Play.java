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
        myBoard = new Board(getGame().getBoardWidthInFields(), getGame().getBoardHeightInFields(), getGame());
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
        int oldWidthX = myBoard.getWidth();
        int oldHeightX = myBoard.getHeight(); 
        if (rotation > 0){
            myBoard.adjustBoardSize(-change, -change);
        }
        if (rotation < 0) {
            myBoard.adjustBoardSize(change, change);
        }
        float widthRatio = (float)myBoard.getWidth() / oldWidthX;
        float heightRatio = (float)myBoard.getHeight() / oldHeightX;
        adjustBoardCoordinatesOnZoom(widthRatio, heightRatio, x, y);
    }
    private void adjustBoardCoordinatesOnZoom(float widthRatio, float heightRatio, int x, int y){
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
