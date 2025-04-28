package objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class CircleMark {
    public int distanseSteps;
    private Field prevField;
    private Field currField;
    private ArrayList<Field> fieldsPathFromHero;
    
    public CircleMark(int distanseSteps, Field prevField, Field currField){
        this.distanseSteps = distanseSteps;
        this.prevField = prevField;
        this.currField = currField;
    }
    public void draw(Graphics2D g2d){
        g2d.drawOval(this.currField.getX() - this.currField.getWidth() / 8, this.currField.getY() - this.currField.getHeight() / 8, this.currField.getWidth() / 4, this.currField.getHeight() / 4);
        String number = String.valueOf(this.prevField.number);
        g2d.drawString(number, this.currField.getX() + 20, this.currField.getY() + 20);

    }
    public void mouseClick() {
        this.fieldsPathFromHero = this.getFieldsPathFromHero();
        this.fieldsPathFromHero.getLast().getHero().activateMoving(fieldsPathFromHero);
    }

    private ArrayList<Field> getFieldsPathFromHero(){
        fieldsPathFromHero = new ArrayList<Field>();
        CircleMark currCircle = this;
        while (currCircle.prevField.getCircleMark() != null) {
            this.fieldsPathFromHero.add(currCircle.currField);
            currCircle = currCircle.prevField.getCircleMark();
        }
        this.fieldsPathFromHero.add(currCircle.currField);
        this.fieldsPathFromHero.add(currCircle.prevField);
        return fieldsPathFromHero;
    }

}
