package scenes;

import java.awt.Color;
import java.awt.Graphics;
import objects.Field;
import objects.Hero;

import main.Game;
import objects.Board;

public class Play extends GameScene implements scenesMethods {
    private Board myBoard;
    private Hero myHero;

    public Play(Game game){
        super(game);
        initBoard();
        initHero(15);
    }

    @Override
    public void render(Graphics g) {

        myBoard.moveBoardVelocity();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 640, 640);
        myBoard.draw(g);
    }
    public void update(){
        myBoard.update();
    }

    private void initBoard(){
        myBoard = new Board(getGame().getBoardWidthInFields(), getGame().getBoardHeightInFields(), getGame());
    }
    private void initHero(int fieldNumber){
        Field field = myBoard.getFieldBasedOnId(fieldNumber);
        myHero = new Hero(field, 2);
        myHero.meltSnowInRange(0, myHero.getField(), null);
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

    public void mouseClicked(int x, int y) {
        myBoard.mouseClicked(x, y);
    }
}
