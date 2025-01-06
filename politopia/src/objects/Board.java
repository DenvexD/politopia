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
    private int screenCentreX;
    private int screenCentreY;
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
    private int findCentreX(){
        return this.getWidth() / 2;
    }
    private int findCentreY(){
        return this.getHeight() / 2;
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
        this.x = this.x + adjustX;
        this.y = this.y + adjustY;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    private int findLeftTopCornerX(){
        int distanceToBoardCorner = this.getWidth() / 2;
        int distanceToScreenCorner = this.x;
        int shortestDistance = Math.min(distanceToScreenCorner, distanceToBoardCorner);
        return this.x - shortestDistance;
    }
    private int findLeftTopCornerY(){
        int distanceToBoardCorner = this.getHeight() / 2;
        int distanceToScreenCorner = this.y;
        int shortestDistance = Math.min(distanceToScreenCorner, distanceToBoardCorner);
        return this.y - shortestDistance;
    }
    private int findRightBottomCornerX(){
        int distanceToBoardCorner = this.getWidth() / 2;
        int distanceToScreenCorner = game.getWindowWidth() - this.x;
        int shortestDistance = Math.min(distanceToScreenCorner, distanceToBoardCorner);
        return this.x + shortestDistance;
    }
    private int findRightBottomCornerY(){
        int distanceToBoardCorner = this.getHeight() / 2;
        int distanceToScreenCorner = game.getWindowHeight() - this.y;
        int shortestDistance = Math.min(distanceToScreenCorner, distanceToBoardCorner);
        return this.y + shortestDistance;
    }



}
