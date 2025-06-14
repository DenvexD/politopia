package objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import displays.Actions;
import displays.Display;
import displays.HeroDisplay;
import main.Game;


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
    private ArrayList<FieldTypes> exclusionTypesMelting = new ArrayList<>();
    private ArrayList<FieldTypes> exclusionsTypesWalking = new ArrayList<>();
    private ArrayList<Actions> actions = new ArrayList<Actions>();
    private Display display;
    
    public Hero(Field field, int range){
        this.field = field;
        this.range = range;
        this.display = field.getPlay().getHeroDisplay();
        field.setHero(this);
        exclusionsTypesWalking.add(FieldTypes.DEEP_WATER);
    }

    public void draw(Graphics2D g2d){
        int leftCornerX = this.field.getX() - this.getWidth()/2;
        int leftCornerY = this.field.getY() - this.getHeight() / 2;
        g2d.drawImage(img, leftCornerX, leftCornerY, this.getWidth(), this.getHeight(), null);
    }

    public void update() {
        if (this.isMoving) {
            if (tick == 10) {
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
            ArrayList<Field> fieldNeightbours = this.getActiveNeighbourList(currField, prevField, this.exclusionTypesMelting);
            prevField = currField;
            for (Field field : fieldNeightbours) {
                currField = field;
                currField.removeSnowCovered();
                this.meltSnowInRange(currRange + 1, currField, prevField);
            }
        }
        this.field.removeSnowCovered();
    }
    private void setActions(){
        actions.clear();
        if (field.getForest() != null) {
            actions.add(Actions.clearForest);
        }else{
            actions.add(Actions.growForest);
        }

    }
    private ArrayList<Field> getActiveNeighbourList(Field currField, Field prevField, ArrayList<FieldTypes> requierementTypes){
        ArrayList<Field> fieldNeightbours = new ArrayList<Field>();
        Field[] actualFieldNeighbours = {currField.left, currField.right, currField.top, currField.bottom};
        for (Field field : actualFieldNeighbours) {
            if (field != prevField && field != null && field != this.field){
                if (!requierementTypes.contains(field.getFieldType())) {
                    fieldNeightbours.add(field);
                } 
                if (field == currField.left || field == currField.right) {
                    fieldNeightbours = this.setCornerNeighboursInTheList(field, prevField, fieldNeightbours, requierementTypes);
                }
            }
        }
        return fieldNeightbours;
    }
    private ArrayList<Field> setCornerNeighboursInTheList(Field currField, Field prevField, ArrayList<Field> fieldNeightbours, ArrayList<FieldTypes> requierementTypes){
        if (currField.bottom != prevField && currField.bottom != null && currField.bottom != this.field) {
            if (!requierementTypes.contains(currField.bottom.getFieldType())){
                fieldNeightbours.add(currField.bottom);
            }

        }
        if (currField.top != prevField && currField.top != null && currField.top != this.field) {
            if (!requierementTypes.contains(currField.top.getFieldType())) {
                fieldNeightbours.add(currField.top);
            }

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
        setActions();
        isClicked = true;
        showDisplay();
        this.setMarksToTheFields(0, this.field, null);
    }
    public void unclick(){
        this.isClicked = false;
        display.setVisable(false);
        this.resetAllMarkedFields(markedFields);
    }
    private void showDisplay(){
        ((HeroDisplay)display).setHero(this);
        display.setActions(actions);
        display.setVisable(true);
    }

    private void setMarksToTheFields(int currRange, Field currField, Field prevField){
        if (currRange < this.range) {
            ArrayList<Field> fieldNeighbours = this.getActiveNeighbourList(currField, prevField, this.exclusionsTypesWalking);
            prevField = currField;
            for (Field field : fieldNeighbours) {
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
        if (currOrderNumber < this.fieldsPathFromHero.size()) {
            return this.fieldsPathFromHero.get(this.fieldsPathFromHero.size() - currOrderNumber - 1);
        }else{
            return null;
        }
    }

}
