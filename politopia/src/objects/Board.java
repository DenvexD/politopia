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
    private int widthInFields;
    private int heightInFields;
    private Game game;
    private int x;
    private int y;
    public Board(int widthInFields, int heightInFields, Game game){
        super(null, widthInFields * game.getFieldWidth(), heightInFields * game.getFieldHeight());
        this.game = game;
        this.widthInFields = widthInFields;
        this.heightInFields = heightInFields;
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
    private Boolean isLastRaw(){
        int rawsAmount = this.heightInFields;
        return this.fields.size() == rawsAmount;
    }
    private Field initFieldBoarders(int x, int y, int i){
        field = new Field(game.getFieldWidth(), game.getFieldHeight(), FieldTypes.DEEP_WATER);
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

    public void draw(Graphics g){
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;
        g.setColor(Color.BLACK);

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
                    field.draw(g, field.getBound().x, field.getBound().y);
                }

            }
        }
    }

    private boolean isVisable(Field field){
        return field.getBound().intersects(0, 0, game.getWindowWidth(), game.getWindowHeight());
    }
    public void adjustBoardCoordinates(int adjustX, int adjustY){
        this.adjustFieldsCoordinates(adjustX, adjustY);
        this.x -= adjustX;
        this.y -= adjustY;
        System.out.println(adjustX);
    }
    private void adjustFieldsCoordinates(int adjustX, int adjustY){
       for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
                field.getBound().x -= adjustX;
                field.getBound().y -= adjustY;
            }
            
        }
    }
    public void adjustBoardSize(int adjustHeight, int adjustWidth){
        this.setWidth(this.getWidth() + adjustWidth * this.fields.get(0).size());
        this.setHeght(this.getHeight() + adjustHeight * this.fields.size());
        this.adjustFieldsSize(adjustHeight, adjustWidth);
    }
    private void adjustFieldsSize(int adjustHeight, int adjustWidth){
        for (int y = this.y - this.getHeight()/2; y < this.y + this.getHeight()/2; y += game.getFieldHeight()) {
            for(int x = this.x - this.getWidth()/2; x < this.x + this.getWidth()/2; x += game.getFieldWidth()){

            }
        }
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    


}
