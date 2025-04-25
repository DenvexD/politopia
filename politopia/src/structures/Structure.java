package structures;

import java.awt.Graphics;
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
    public abstract void render(Graphics g);
    public abstract void update();

}
