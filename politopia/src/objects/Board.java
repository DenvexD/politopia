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
        this.rawOFields = new ArrayList<>();
        for (int y = this.y - this.getHeight()/2; y < this.y + this.getHeight()/2; y += game.getFieldHeight()) {
            for(int x = this.x - this.getWidth()/2; x < this.x + this.getWidth()/2; x += game.getFieldWidth()){
                field = new Field(game.getFieldWidth(), game.getFieldHeight(), FieldTypes.DEEP_WATER);
                field.initBounds(x, y);
                field.setText(Integer.toString(i));
                this.rawOFields.add(field);
                i++;
            }
            
            this.fields.add(this.rawOFields);

        }
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
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
        this.x += adjustX *5;
        this.y += adjustY *5;
        System.out.println(adjustX);
    }
    private void adjustFieldsCoordinates(int adjustX, int adjustY){
       for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
                field.getBound().x += adjustX;
                field.getBound().y += adjustY;
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
