package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;

import main.Game;
import ui.Button;

public class Board extends Button{
    private ArrayList<ArrayList<Field>> fields = new ArrayList<>();
    private ArrayList<Field> rawOFields;
    private Field field;
    private Game game;
    private int x;
    private int y;
    private Polygon polygonBound;
    public Board(int widthInFields, int heightInFields, Game game){
        super(null, widthInFields * game.getFieldWidth(), heightInFields * game.getFieldHeight());
        this.game = game;
        this.x = game.getWindowWidth() / 2;
        this.y = game.getWindowHeight() / 2;
        this.initBounds(this.x, this.y);
        initFields();
    }
    @Override
    public void initBounds(int x, int y){
        this.x = x;
        this.y = y;
        int[] xPoints = {x, x + this.getWidth() / 2, x, x - this.getWidth() / 2};
        int[] yPoints = {y - this.getHeight() / 2, y, y + this.getHeight() / 2, y};
        this.polygonBound = new Polygon(xPoints, yPoints, 4);
    }
    private void initFields(){
        int i = 0;
        Field prevField = null;
        Field currField = null;
        Field topRowField = null;
        int currRow = 0;
        int rows = (int)(Math.sqrt(Math.pow(this.getWidth() / 2, 2) + Math.pow(this.getHeight()/2, 2)) / (Math.sqrt(Math.pow(game.getFieldWidth() / 2, 2) + Math.pow(game.getFieldHeight() / 2, 2))));
        while (currRow < rows) {
            this.rawOFields = new ArrayList<>();
            int currX = this.polygonBound.xpoints[0] - currRow * game.getFieldWidth()/2;
            int currY = this.polygonBound.ypoints[0] + currRow * game.getFieldHeight()/2;
            int boarderX = this.polygonBound.xpoints[1] - currRow * game.getFieldWidth()/2;
            int boarderY = this.polygonBound.ypoints[1] + currRow * game.getFieldHeight()/2;
            while (currX < boarderX && currY < boarderY) {
                currField = this.initFieldBoarders(currX, currY, i);
                currField.number = i;
                this.initFieldLeftRightNeighbours(prevField, currField);
                i++;
                prevField = currField;
                currX += game.getFieldWidth()/2;
                currY += game.getFieldHeight()/2;
            }
            currRow++;
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
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;
        for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
                if (field.left != null) {
                    left = field.left.number;
                }else{
                    left = 0;
                }
                if (field.right != null) {
                    right = field.right.number;
                }else{
                    right = 0;
                }
                if (field.top != null) {
                    top = field.top.number;
                }else{
                    top = 0;
                }
                if (field.bottom != null) {
                    bottom = field.bottom.number;
                }else{
                    bottom = 0;
                }
                field.setText("" + field.number + " L" +  left +  " R" + right + " T" + top + " B" + bottom);
                if (this.isVisable(field)){
                    g.setColor(Color.gray);
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
