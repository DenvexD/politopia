package structures;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import displays.Actions;
import displays.Display;
import displays.StructureDispaly;
import main.Game;
import objects.Field;

public abstract class Structure {
    protected Field field;
    protected Display display;
    private ArrayList<Actions> actions = new ArrayList<>();
    protected boolean dostroyable = true;
    protected boolean leveluppable = true;
    protected int levelOfset = 0;
    protected Image img;

    public Structure(Field field){
        this.field = field;
        this.display = field.getPlay().getStructureDisplay();
    }

    public Field getField(){
        return field;
    }
    public void setField(Field field){
        this.field = field;
    }
    protected void setDisplaysActions(){
        if (dostroyable) {
            this.actions.add(Actions.destroy);
        }
        if (leveluppable) {
            this.actions.add(Actions.levelup);
        }

        activateDisplay(this.actions);
        this.actions.clear();
    }

    private void activateDisplay(ArrayList<Actions> actions){
        display.setActions(actions);
        ((StructureDispaly) display).setStructure(this);
        display.setVisable(true);
    }
    public abstract void mouseClicked();
    public abstract void unclick();
    public abstract void isClicked();
    public abstract void update();
    public abstract void destroy();
    public abstract void levelup();


    public void render(Graphics2D g2d){
        int cornerX = this.field.getX() - this.field.getWidth()/2;
        int cornerY = this.field.getY() + this.levelOfset - this.field.getWidth()/2;
        g2d.drawImage(img, cornerX, cornerY, this.field.getWidth(), this.field.getHeight(), null);
    }
}
