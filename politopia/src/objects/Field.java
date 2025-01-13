package objects;

import ui.Button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;

public class Field extends Button {
    private FieldTypes fieldType;
    public Field left;
    public Field right;
    public Field top;
    public Field bottom;
    public int number;
    private Polygon polygonBound;
    private int x;
    private int y;

    public Field(int width, int height, FieldTypes fieldType){
        super(null, width, height);
        this.fieldType = fieldType;
        this.setImageBasedOnType();
        this.left = null;
        this.right = null;
        this.top = null;
        this.bottom = null;
    }
    @Override
    public void draw(Graphics g, int x, int y){
        this.initBounds(x, y);
        if(this.getImage() == null){
            g.fillPolygon(this.polygonBound);
        }else{
            g.drawImage(this.getImage(), x, y, this.getWidth(), this.getHeight(), null);
        }
        if (this.text != null) {
            this.drawText(g, x, y);
        }
        
    }
    @Override
    public void initBounds(int x, int y){
        this.x = x;
        this.y = y;
        int[] xPoints = {x, x + this.getWidth() / 2, x, x - this.getWidth() / 2};
        int[] yPoints = {y - this.getHeight() / 2, y, y + this.getHeight() / 2, y};
        this.polygonBound = new Polygon(xPoints, yPoints, 4);
    }
    public void setType(FieldTypes fieldType){
        this.fieldType = fieldType;
        this.setImageBasedOnType();
    }
    public Polygon getPolygonBound(){
        return this.polygonBound;
    }
    private void setImageBasedOnType(){
        Image img;
        if (this.fieldType != null) {
            switch (this.fieldType) {
                case DEEP_WATER:
                    img = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/deep.png");  
                    break; 
                default:
                    img = null;
            }
        }else{
            img = null;
        }
        

        this.setImage(img);
    }

    public int getNumberOfNeighbours(){
        int i = 0;
        if (this.left != null) {
            i++;
        }
        if (this.right != null) {
            i++;
        }
        if (this.bottom != null) {
            i++;
        }
        if (this.top != null) {
            i++;
        }
        return i;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getY(){
        return this.y;
    }
    public int getX(){
        return this.x;
    }
    
}
