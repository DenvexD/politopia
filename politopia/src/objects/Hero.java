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
    private int xToCentre = 0;
    private int yToCentre = 0;
    private int x;
    private int y;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private boolean isMovingForward = false;
    private boolean isMovingDown = false;
    
    public Hero(Field field, int range){
        this.field = field;
        this.range = range;
        this.display = field.getPlay().getHeroDisplay();
        field.setHero(this);
        exclusionsTypesWalking.add(FieldTypes.DEEP_WATER);
    }

    public void draw(Graphics2D g2d){
        x = this.field.getX() + xToCentre;
        y = this.field.getY() + yToCentre;
        int leftCornerX = x - this.getWidth()/2;
        int leftCornerY = y - this.getHeight() / 2;
            g2d.drawImage(img, leftCornerX, leftCornerY, this.getWidth(), this.getHeight(), null);
        // g2d.drawString("left", field.left.getX(), field.left.getY());
        // g2d.drawString("right", field.right.getX(), field.right.getY());
        // g2d.drawString("top", field.top.getX(), field.top.getY());
        // g2d.drawString("bottom", field.bottom.getX(), field.bottom.getY());

        g2d.drawLine(x, y, field.left.getX(), field.left.getY());
        g2d.drawLine(x, y, field.right.getX(), field.right.getY());
        g2d.drawLine(x, y, field.top.getX(), field.top.getY());
        g2d.drawLine(x, y, field.bottom.getX(), field.bottom.getY());
        g2d.drawLine(x, y, field.top.left.getX(), field.top.left.getY());
        g2d.drawLine(x, y, field.top.right.getX(), field.top.right.getY());
        g2d.drawLine(x, y, field.bottom.left.getX(), field.bottom.left.getY());
        g2d.drawLine(x, y, field.bottom.right.getX(), field.bottom.right.getY());
    }

    public void update() {
        if (BoardClickedStates.boardClickedState == BoardClickedStates.HERO) {
            if (isMovingForward) {
                yToCentre --;
                
            }
            if (isMovingDown) {
                yToCentre ++;
            }
            if (isMovingLeft) {
                xToCentre --;
            }
            if (isMovingRight) {
                xToCentre ++;
            }
        }

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
        x = this.field.getX();
        y = this.field.getY();
        xToCentre = 0;
        yToCentre = 0;
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


    public void setIsMovingForward(boolean status){
        isMovingForward = status;
    }
        public void setIsMovingDown(boolean status){
        isMovingDown = status;
    }
        public void setIsMovingLeft(boolean status){
        isMovingLeft = status;
    }
        public void setIsMovingRight(boolean status){
        isMovingRight = status;
    }


}
