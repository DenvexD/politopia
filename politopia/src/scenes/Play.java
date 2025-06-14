package scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import displays.Actions;
import displays.Display;
import displays.FieldDisplay;
import displays.HeroDisplay;
import displays.StructureDispaly;
import main.Game;
import main.GameStates;
import objects.Board;
import objects.BoardClickedStates;
import objects.Field;
import objects.Hero;
import structures.Structure;

public class Play extends GameScene implements scenesMethods {
    private Board myBoard;
    private ArrayList<Hero> heros = new ArrayList<Hero>();
    private ArrayList<Display> displays = new ArrayList<Display>();
    private Hero clickedHero = null;
    private Field clickedField = null;
    private Structure clickedStructure = null;


    public Play(Game game){
        super(game);
        initDisplays();
        initBoard();
        initHero(15);
        initForest(13);

    }

    @Override
    public void render(Graphics2D g2d) {

        myBoard.moveBoardVelocity();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 640, 640);
        myBoard.draw(g2d);
        drawDisplays(g2d);
        GameStates.listenerReadyGameState = GameStates.PLAYING;

    }
    private void drawDisplays(Graphics2D g2d){
        for (Display display : displays) {
            display.draw(g2d);
        }
    }
    public void update(){
        myBoard.update();
        updateMyHeros();
    }

    private void initBoard(){
        myBoard = new Board(getGame().getBoardWidthInFields(), getGame().getBoardHeightInFields(), getGame(), this);
    }

    private void initHero(int fieldNumber){
        Field field = myBoard.getFieldBasedOnId(fieldNumber);
        Hero myHero = new Hero(field, 2);
        this.heros.add(myHero);
        for (Hero hero : this.heros) {
            hero.meltSnowInRange(0, hero.getField(), null);
        }
    }

    private void initForest(int fieldNumber){
        Field field = myBoard.getFieldBasedOnId(fieldNumber);
        field.createForest();
    }

    private void initDisplays(){
        displays.add(new HeroDisplay(getGame()));
        displays.add(new FieldDisplay(getGame()));
        displays.add(new StructureDispaly(getGame()));
    }

    public Display getFieldDisplay(){
        return displays.get(1);
    }
    public Display getHeroDisplay(){
        return displays.get(0);
    }
    public Display getStructureDisplay(){
        return displays.get(2);
    }

    private void updateMyHeros(){
        for (Hero hero : heros) {
            hero.update();
        }
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
        boolean displayGotClicked = false;
        for (Display display : displays) {
            if (display.isMouseClicked(x, y)) {
                displayGotClicked = true;
                display.mouseClick(x, y);
                display.setVisable(false);
                BoardClickedStates.boardClickedState = BoardClickedStates.NULL;
                if (clickedField != null) {
                    clickedField.unclick();
                    clickedField = null;
                }else if(clickedHero != null){
                    clickedHero.unclick();
                    clickedHero = null;
                }else{
                    clickedStructure.unclick();
                    clickedStructure = null;
                }
            break;
            }
        }

        if (!displayGotClicked && myBoard.isMouseClicked(x, y)){
            clickFieldsObject(myBoard.getClickedField(x, y));
        }

    }
    private void clickFieldsObject(Field clickedField){
        switch (BoardClickedStates.boardClickedState) {
            case HERO:
                handleHeroStateClicked(clickedField);
                break;

            case STRUCTURE:
                handleStructureStateClicked(clickedField);
                break;

            case FIELD:
                handleFieldStateClicked(clickedField);
                break;
            
            case NULL:
                handleNoStateClicked(clickedField);
                break;
        }
    }
    private void handleHeroStateClicked(Field clickedField){     //TODO
        if (clickedField.getHero() == null){
            if (clickedField.getCircleMark() != null) {
                clickedField.getCircleMark().mouseClick();
            }
            BoardClickedStates.resetState();
            clickedHero.unclick();
            clickedHero = null;
        }else if (clickedField.getHero() != clickedHero) {
            clickedHero.unclick();
            clickedField.getHero().mouseClicked();
            clickedHero = clickedField.getHero();
        }else{
            clickedHero.unclick();
            clickedHero = null;
            if (clickedField.getStructure() != null) {
                BoardClickedStates.boardClickedState = BoardClickedStates.STRUCTURE;
                clickedField.getStructure().mouseClicked();
                clickedStructure = clickedField.getStructure();
            }else{
                BoardClickedStates.boardClickedState = BoardClickedStates.FIELD;
                clickedField.mouseClicked();
                this.clickedField = clickedField;
            }

        }
    }
    private void handleStructureStateClicked(Field clickedField){
        if (clickedField.getStructure() == null) {
            BoardClickedStates.resetState();
            clickedStructure.unclick();
            clickedStructure = null;
        }else if(clickedField.getStructure() != this.clickedStructure){
            clickedStructure.unclick();
            if (clickedField.getHero() != null) {
                BoardClickedStates.boardClickedState = BoardClickedStates.HERO;
                clickedHero = clickedField.getHero();
                clickedField = null;
                clickedHero.mouseClicked();
            }else{
                clickedField.getStructure().mouseClicked();
                clickedStructure = clickedField.getStructure();
            }
        }else{
            this.clickedStructure.unclick();
            this.clickedStructure = null;
            BoardClickedStates.boardClickedState = BoardClickedStates.FIELD;
            clickedField.mouseClicked();
            this.clickedField = clickedField;
        }
    }
    private void handleFieldStateClicked(Field clickedField){
        this.clickedField.unclick();
        this.clickedField = null;
        clickedField.unclick();
        BoardClickedStates.boardClickedState = BoardClickedStates.NULL;
    }

    private void handleNoStateClicked(Field clickedField){
        if (clickedField.getHero() != null) {
            BoardClickedStates.boardClickedState = BoardClickedStates.HERO;
            clickedHero = clickedField.getHero();
            clickedHero.mouseClicked();
        }else if (clickedField.getStructure() != null) {
            BoardClickedStates.boardClickedState = BoardClickedStates.STRUCTURE;
            clickedStructure = clickedField.getStructure();
            clickedStructure.mouseClicked();
        }else{
            BoardClickedStates.boardClickedState = BoardClickedStates.FIELD;
            this.clickedField = clickedField;
            clickedField.mouseClicked();
            if (clickedField.isClickable()) {
                clickedField.getSnow().mouseClicked();
            }

        }
    }

}
