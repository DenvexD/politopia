package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Hero{
    private Field field;
    private int range;
    private Image img = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Nature bunny.png");
    private boolean isClicked = false;
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
            fieldNeightbours.add(currField.left);
            fieldNeightbours = this.setCornerNeighboursInTheList(currField.left, prevField, fieldNeightbours);
        }
        if (currField.right != prevField && currField.right != null) {
            fieldNeightbours.add(currField.right);
            fieldNeightbours = this.setCornerNeighboursInTheList(currField.right, prevField, fieldNeightbours);
        }
        if (currField.top != prevField && currField.top != null) {
            fieldNeightbours.add(currField.top);
        }
        if (currField.bottom != prevField && currField.bottom != null) {
            fieldNeightbours.add(currField.bottom);
        }
        return fieldNeightbours;
    }
    private ArrayList<Field> setCornerNeighboursInTheList(Field currField, Field prevField, ArrayList<Field> fieldNeightbours){
        if (currField.bottom != prevField && currField.bottom != null) {
            fieldNeightbours.add(currField.bottom);
        }
        if (currField.top != prevField && currField.top != null) {
            fieldNeightbours.add(currField.top);
        }
        return fieldNeightbours;
    }
    public Field getField(){
        return this.field;
    }



    public void mouseClicked(){
        isClicked = !isClicked;
        if (isClicked) {
            System.out.println("clicked");
        }else{
            System.out.println("unckicked");
        }
    }
    public boolean isClicked(){
        return this.isClicked;
    }
    public void setClicked(Boolean status){
        this.isClicked = status;
    }

        

}

