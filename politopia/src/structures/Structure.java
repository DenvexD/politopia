package structures;

import java.awt.Graphics2D;
import java.awt.Image;

import objects.Field;

public abstract class Structure {
    private Field field;
    private Image img;

    public Structure(Field field){
        this.field = field;
    }

    public Field getField(){
        return field;
    }
    public void setField(Field field){
        this.field = field;
    }
    public abstract void mouseClicked();
    public abstract void unclick();
    public abstract void isClicked();
    public abstract void render(Graphics2D g2d);
    public abstract void update();

}
