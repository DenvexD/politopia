package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Game;
import ui.Button;

public class Board extends Button{
    private ArrayList<ArrayList<Field>> fields = new ArrayList<>();
    private ArrayList<Field> rawOFields;
    private Field field;
    private Game game;
    private int x;
    private int y;
    public Board(int widthInFields, int heightInFields, Game game){
        super(null, widthInFields * game.getFieldWidth(), heightInFields * game.getFieldHeight());
        this.game = game;
        this.x = game.getWindowWidth() / 2;
        this.y = game.getWindowHeight() / 2;
        initFields();
    }
    private void initFields(){
        int i = 0;
        Field prevField = null;
        Field currField = null;
        Field topRowField = null;
        for (int y = this.y - this.getHeight()/2; y < this.y + this.getHeight()/2; y += game.getFieldHeight()) {
            this.rawOFields = new ArrayList<>();
            for(int x = this.x - this.getWidth()/2; x < this.x + this.getWidth()/2; x += game.getFieldWidth()){
                currField = this.initFieldBoarders(x, y, i);
                currField.number = i;
                this.initFieldLeftRightNeighbours(prevField, currField);
                i++;
                prevField = currField;
            }
            this.fields.add(this.rawOFields);
            this.conectTopAndBottomFields(topRowField, currField);
            topRowField = currField;
            prevField = null;

            
            

        }
    }
    private Field initFieldBoarders(int x, int y, int i){
        field = new Field(game.getFieldWidth(), game.getFieldHeight(), null);
        field.initBounds(x, y);
        field.setText(Integer.toString(i));
        this.rawOFields.add(field);
        return field;
    }
    private void conectTopAndBottomFields(Field topRowField, Field currField){
        if (topRowField != null) {
            while (currField != null) {
                currField.top = topRowField;
                topRowField.bottom = currField;
                currField = currField.left;
                topRowField = topRowField.left;
            }
        }
    }
    private void initFieldLeftRightNeighbours(Field prevField, Field field){
        if (prevField != null){
            field.left = prevField;
            prevField.right = field;
        }
    }
    private void reInitFieldsCoordinates(int adjustHeight, int adjustWidth){
        Field currField = this.fields.getFirst().getFirst();
        for (int y = this.y - this.getHeight()/2; y < this.y + this.getHeight()/2; y += game.getFieldHeight()) {
            for(int x = this.x - this.getWidth()/2; x < this.x + this.getWidth()/2; x += game.getFieldWidth()){
                currField.setHeight(field.getHeight() + adjustHeight);
                currField.setWidth(field.getWidth() + adjustWidth);
                currField.initBounds(x, y);
                
                currField = this.getNextField(currField);
            }
        }
    }
    private Field getNextField(Field currField){
        if (currField.right != null){
            currField = currField.right;
        }else{
            if (currField.bottom == null) {
                return null;
            }
            while (currField.left != null) {
                currField = currField.left;
            }
            currField = currField.bottom;
        }
        return currField;
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
                if (this.isVisable(field)){
                    field.draw(g, field.getX(), field.getY());
                }

            }
        }
    }

    private boolean isVisable(Field field){
        return field.getPolygonBound().intersects(0, 0, game.getWindowWidth(), game.getWindowHeight());
    }
    public void adjustBoardCoordinates(int adjustX, int adjustY){
        this.adjustFieldsCoordinates(adjustX, adjustY);
        this.x -= adjustX;
        this.y -= adjustY;
    }
    public void moveCoordinatesToCentre(int x, int y){
        int distanceY = game.getWindowHeight()/2 - y;
        int distanceX = game.getWindowWidth()/2 - x;
        this.adjustBoardCoordinates(distanceX, distanceY);
    }
    public void removeCoordinatesToCentre(int x, int y){
        int distanceY = game.getWindowHeight()/2 -y;
        int distanceX = game.getWindowWidth()/2 - x;
        this.adjustBoardCoordinates(-distanceX, -distanceY);
    }
    private void adjustFieldsCoordinates(int adjustX, int adjustY){
       for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
                field.initBounds(field.getX() - adjustX, field.getY() - adjustY);
            }
            
        }
    }
    public void adjustBoardSize(int adjustHeight, int adjustWidth){
        this.setWidth(this.getWidth() + adjustWidth * game.getBoardWidthInFields());
        this.setHeight(this.getHeight() + adjustHeight * game.getBoardHeightInFields());
        this.adjustFieldsSize(adjustHeight, adjustWidth);
    }
    private void adjustFieldsSize(int adjustHeight, int adjustWidth){
        game.setFieldHeight(game.getFieldHeight() + adjustHeight);
        game.setFieldWidth(game.getFieldWidth() + adjustWidth);
        this.reInitFieldsCoordinates(adjustHeight, adjustWidth);
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    


}
