package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
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
    public void adjustBounds(int x, int y){
        this.x = x;
        this.y = y;
        this.polygonBound.xpoints = new int[]{x, x + this.getWidth() / 2, x, x - this.getWidth() / 2};
        this.polygonBound.ypoints = new int[]{y - this.getHeight() / 2, y, y + this.getHeight() / 2, y};
        this.polygonBound.invalidate();
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
                currField = this.initFieldBounds(currX, currY, i);
                currField.number = i;
                this.initFieldLeftRightNeighbours(prevField, currField);
                prevField = currField;
                currX += game.getFieldWidth()/2;
                currY += game.getFieldHeight()/2;
                i++;
            }
            this.fields.add(this.rawOFields);
            this.conectTopAndBottomFields(topRowField, currField);
            topRowField = currField;
            prevField = null;
            currRow++;
        }
    }
    private Field initFieldBounds(int x, int y, int i){
        field = new Field(game.getFieldWidth(), game.getFieldHeight(), FieldTypes.CLOUDS);
        field.initBounds(x, y + game.getFieldHeight()/2);
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
    private void reInitFieldsBounds(int adjustHeight, int adjustWidth){
        Field currField = null;
        int leftBoarderX = this.polygonBound.xpoints[3];
        int currRow = 0;
        int currRowX = this.polygonBound.xpoints[0] - currRow * game.getFieldWidth()/2;
        while (currRowX > leftBoarderX) {
            int currX = this.polygonBound.xpoints[0] - currRow * game.getFieldWidth()/2;
            int currY = this.polygonBound.ypoints[0] + currRow * game.getFieldHeight()/2;
            int rightBoarderX = this.polygonBound.xpoints[1] - currRow * game.getFieldWidth()/2;
            int rightBoarderY = this.polygonBound.ypoints[1] + currRow * game.getFieldHeight()/2;
            while (currX < rightBoarderX && currY < rightBoarderY) {
                currField = this.getNextField(currField);
                currField.setHeight(currField.getHeight() + adjustHeight);
                currField.setWidth(currField.getWidth() + adjustWidth);
                currField.initBounds(currX, currY + currField.getHeight()/2);
                
                currX += game.getFieldWidth()/2;
                currY += game.getFieldHeight()/2;
            }
            currRow++;
            currRowX = this.polygonBound.xpoints[0] - currRow * game.getFieldWidth()/2;
        }
    }
    private Field getNextField(Field currField){
        if (currField == null) {
            return this.fields.getFirst().getFirst();
        }
        int row = (currField.number + 1) / game.getBoardWidthInFields();
        int column = (currField.number + 1) - row * game.getBoardWidthInFields();

        if (row <= game.getBoardWidthInFields()-1 && column <= game.getBoardHeightInFields()-1) {
            return this.fields.get(row).get(column);
        }else{
            return null;
        }

    }

    public void draw(Graphics g){
        for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
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
        this.adjustBounds(this.x, this.y);
    }
    public void moveCoordinatesToCentre(int x, int y){
        int distanceY = game.getWindowHeight()/2 - y;
        int distanceX = game.getWindowWidth()/2 - x;
        this.adjustBoardCoordinates(distanceX, distanceY);
    }
    private void adjustFieldsCoordinates(int adjustX, int adjustY){
       for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
                field.adjustBounds(field.getX() - adjustX, field.getY() - adjustY);
            }
        }
    }
    public void adjustBoardSize(int adjustHeight, int adjustWidth){
        this.setWidth(this.getWidth() + adjustWidth * game.getBoardWidthInFields());
        this.setHeight(this.getHeight() + adjustHeight * game.getBoardHeightInFields());
        this.adjustBounds(this.x, this.y);
        this.adjustFieldsSize(adjustHeight, adjustWidth);
        
    }
    private void adjustFieldsSize(int adjustHeight, int adjustWidth){
        game.setFieldHeight(game.getFieldHeight() + adjustHeight);
        game.setFieldWidth(game.getFieldWidth() + adjustWidth);
        this.reInitFieldsBounds(adjustHeight, adjustWidth);
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Polygon getPolygonBound(){
        return polygonBound;
    }
    


}
