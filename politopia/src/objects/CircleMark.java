package objects;

import java.awt.Graphics;

public class CircleMark {
    int distanseSteps;
    Field prevField;
    Field nextField;
    Field currField;
    public CircleMark(int distanseSteps, Field prevField, Field nextField, Field currField){
        this.distanseSteps = distanseSteps;
        this.prevField = prevField;
        this.nextField = nextField;
        this.currField = currField;
    }
    public void draw(Graphics g){
        g.drawOval(this.currField.getX() - this.currField.getWidth() / 8, this.currField.getY() - this.currField.getHeight() / 8, this.currField.getWidth() / 4, this.currField.getHeight() / 4);
        String number = String.valueOf(distanseSteps);
        g.drawString(number, this.currField.getX() + 20, this.currField.getY() + 20);

    }
}
