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
    private boolean isMoving = false;
    private ArrayList<Field> markedFields = new ArrayList<Field>();
    private ArrayList<Field> fieldsPathFromHero;
    private int tick = 0;
    private int currOrderNumber = 0;
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
        if (this.isMoving) {
            if (tick == 120) {
                Field nextField = this.getNextPathField(currOrderNumber);

                if (nextField == null) {
                    tick = 0;
                    currOrderNumber = 0;
                    this.isMoving = false;
                }else{
                    this.changeField(nextField);
                    tick = 0;
                    currOrderNumber ++;
                }
            }else{
                tick ++;
            }

        }
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
    public boolean isClicked(){
        return this.isClicked;
    }


    public void mouseClicked(){
        isClicked = true;
        this.setMarksToTheFields(0, this.field, null);
    }
    public void unclick(){
        this.isClicked = false;
        this.resetAllMarkedFields(markedFields);
    }

    private void setMarksToTheFields(int currRange, Field currField, Field prevField){
        if (currRange < this.range) {
            ArrayList<Field> fieldNeightbours = this.getActiveNeighbourList(currField, prevField);
            prevField = currField;
            for (Field field : fieldNeightbours) {
                if (field.getCircleMark() != null) {
                    if (field.getCircleMark().distanseSteps > currRange) {
                        field.createCircleMark(currRange, prevField);
                        this.markedFields.add(field);
                    }
                    
                }else{
                    field.createCircleMark(currRange, prevField);
                    this.markedFields.add(field);
                }
                this.setMarksToTheFields(currRange + 1, field, prevField);
            }
        }
    }

    private void resetAllMarkedFields(ArrayList<Field> markedFields){
        for (Field field : markedFields) {
            field.setCircleMark(null);
        }
        this.markedFields = new ArrayList<Field>();
    }

    public void changeField(Field field){
        this.field.setHero(null);
        this.field = field;
        this.field.setHero(this);
        this.meltSnowInRange(0, this.field, null);
    }
    public void activateMoving(ArrayList<Field> fieldsPathFromHero){
        this.isMoving = true;
        this.fieldsPathFromHero = fieldsPathFromHero;
    }

    private Field getNextPathField(int currOrderNumber){
        for (Field field : fieldsPathFromHero) {
            System.out.println(field.number);
        }
        if (currOrderNumber < this.fieldsPathFromHero.size()) {
            return this.fieldsPathFromHero.get(this.fieldsPathFromHero.size() - currOrderNumber - 1);
        }else{
            return null;
        }
    }

}
