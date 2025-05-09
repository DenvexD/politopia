package scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Game;
import main.GameStates;
import objects.Board;
import objects.BoardClickedStates;
import objects.Field;
import objects.Hero;
import objects.HeroDisplay;
import structures.Structure;

public class Play extends GameScene implements scenesMethods {
    private Board myBoard;
    private ArrayList<Hero> heros = new ArrayList<Hero>();
    private HeroDisplay heroDisplay;
    private Hero clickedHero = null;
    private Field clickedField = null;
    private Structure clickedStructure = null;


    public Play(Game game){
        super(game);
        initBoard();
        initHero(15);
        initForest(13);
        initHeroDisplay();
    }

    @Override
    public void render(Graphics2D g2d) {

        myBoard.moveBoardVelocity();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 640, 640);
        myBoard.draw(g2d);
        heroDisplay.draw(g2d);
        GameStates.listenerReadyGameState = GameStates.PLAYING;

    }
    public void update(){
        myBoard.update();
        updateMyHeros();
    }

    private void initBoard(){
        myBoard = new Board(getGame().getBoardWidthInFields(), getGame().getBoardHeightInFields(), getGame());
    }

    private void initHero(int fieldNumber){
        Field field = myBoard.getFieldBasedOnId(fieldNumber);
        Hero myHero = new Hero(field, 3);
        this.heros.add(myHero);
        for (Hero hero : this.heros) {
            hero.meltSnowInRange(0, hero.getField(), null);
        }
    }

    private void initForest(int fieldNumber){
        Field field = myBoard.getFieldBasedOnId(fieldNumber);
        field.createForest();
    }

    private void initHeroDisplay(){
        this.heroDisplay = new HeroDisplay(getGame());
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
        if (heroDisplay.isMouseClicked(x, y)) {
            heroDisplay.mouseClick(x, y);
        }else if (myBoard.isMouseClicked(x, y)){
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
            BoardClickedStates.resetState();
            clickedHero.unclick();
            clickedHero = null;
        }else if (clickedField.getHero() != clickedHero) {
            clickedHero.unclick();
            clickedField.getHero().mouseClicked();
            clickedHero = clickedField.getHero();
        }else{
            BoardClickedStates.boardClickedState = BoardClickedStates.STRUCTURE;
            clickedField.getStructure().mouseClicked();
            clickedStructure = clickedField.getStructure();
        }
    }
    private void handleStructureStateClicked(Field clickedField){
        if (clickedField.getStructure() == null) {
            BoardClickedStates.resetState();
            clickedField.getStructure().unclick();
            clickedStructure = null;
        }else if(clickedField.getStructure() != this.clickedField.getStructure()){
            this.clickedField.getStructure().unclick();
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
        }else if (clickedField.isClickable()){
            BoardClickedStates.boardClickedState = BoardClickedStates.FIELD;
            this.clickedField = clickedField;
            clickedField.mouseClicked();
        }else{
            clickedField.getSnow().mouseClicked();
        }
    }

}
