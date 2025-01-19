package scenes;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import objects.Board;

public class Play extends GameScene implements scenesMethods {
    private Board myBoard;

    public Play(Game game){
        super(game);
        initBoard();
    }

    @Override
    public void render(Graphics g) {

        myBoard.moveBoardVelocity();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 640, 640);
        myBoard.draw(g);
    }
    public void update(){

    }

    private void initBoard(){
        myBoard = new Board(getGame().getBoardWidthInFields(), getGame().getBoardHeightInFields(), getGame());
    }
    public void mouseDragged(int newPositionX, int newPositionY){

        myBoard.mouseDragged(newPositionX, newPositionY);
        
    }


    public void mousePressed(int x, int y){
        myBoard.mousePressed(x, y);
    }
    public void mouseReleased(int x, int y){
        myBoard.mouseReleased(x, y);

    }

    public void mouseWheelMoved(double rotation, int x, int y){
        myBoard.mouseWheelMoved(rotation, x, y);
    }
   
    public void setMouseExited(boolean status){
        myBoard.setMouseExited(status);
    }
}
