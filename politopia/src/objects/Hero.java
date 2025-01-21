package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import objects.Field;
public class Hero{
    private Field field;
    private int range;
    private Image img = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Nature bunny.png");
    public Hero(Field field, int range){
        this.field = field;
        this.range = range;
        this.field.setHero(this);
    }

    public void draw(Graphics g){
        int leftCornerX = this.field.getX() - this.getWidth()/2;
        int leftCornerY = this.field.getY() - this.getHeight() / 2;
        g.drawImage(img, leftCornerX, leftCornerY, this.getWidth(), this.getHeight(), null);
    }

    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    private int getWidth(){
        return this.field.getWidth() / 2;
    }
    private int getHeight(){
        return this.field.getHeight() / 3;
    }
    public void meltSnowInRange(int currRange, Field currField, Field prevField){
        if (currRange < this.range) {
            ArrayList<Field> fieldNeightbours = this.getActiveNeighbourList(currField, prevField);
            prevField = currField;
            for (Field field : fieldNeightbours) {
                if (field == this.field) {
                    System.out.println("s");
                }
                currField = field;
                currField.setIsSnowCovered(false);
                this.meltSnowInRange(currRange + 1, currField, prevField);
            }
        }
        this.field.setIsSnowCovered(false);
    }
    private ArrayList<Field> getActiveNeighbourList(Field currField, Field prevField){
        ArrayList<Field> fieldNeightbours = new ArrayList<Field>();
        if (currField.left != prevField && currField.left != null) {
            if (currField.left.top != prevField && currField.left.top != null) {
                fieldNeightbours.add(currField.left.top);
            }
            fieldNeightbours.add(currField.left);
            if (currField.left.bottom != prevField && currField.left.bottom != null) {
                fieldNeightbours.add(currField.left.bottom);
            }
        }
        if (currField.right != prevField && currField.right != null) {
            if (currField.right.top != prevField && currField.right.top != null) {
                fieldNeightbours.add(currField.right.top);
            }
            fieldNeightbours.add(currField.right);
            if (currField.right.bottom != prevField && currField.right.bottom != null) {
                fieldNeightbours.add(currField.right.bottom);
            }
        }
        if (currField.top != prevField && currField.top != null) {
            fieldNeightbours.add(currField.top);
        }
        if (currField.bottom != prevField && currField.bottom != null) {
            fieldNeightbours.add(currField.bottom);
        }
        return fieldNeightbours;
    }
    public Field getField(){
        return this.field;
    }
        

}

