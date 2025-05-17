package objects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import main.Game;

public abstract class Display {
    protected Rectangle bounds;
    protected Game game;
    protected Hero hero;
    protected boolean isVisable = false;
    protected int x = 0;
    protected int y = 500;
    protected int width;
    protected int height;
    protected int distanceBetweenButtons = 50;
    protected ArrayList<DisplayActions> actions = new ArrayList<DisplayActions>();
    protected ArrayList<ActionButton> actionButtons;
    Image clearForsetImg = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Clear forest.png");
    Image growForestImg = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Grow forest.png");

    public Display(Game game){
        this.game = game;
        this.initBounds();
    }
    public void draw(Graphics2D g2d){
        if (isVisable) {
            g2d.setColor(Color.black);
            g2d.fillRect(this.x, this.y, this.width, this.height);
            for (ActionButton actionButton : actionButtons) {
                actionButton.draw(g2d);
            }
        }
    }
    protected void initBounds(){
        this.width = this.game.getWindowWidth();
        this.height = this.game.getWindowHeight() - this.y - 20;
        this.bounds = new Rectangle(this.x, this.y, this.width, this.height);
    }
    public void setVisable(boolean status){
        this.isVisable = status;
    }
    public boolean isVisable(){
        return this.isVisable;
    }
    public void setActions(ArrayList<DisplayActions> actions){
        this.actions = actions;
        this.createButtonsBasedOnActions();
    }
    protected abstract void createButtonsBasedOnActions();

    public boolean isMouseClicked(int x, int y){
        return this.isVisable() && bounds.contains(x, y);
    }

    public abstract void mouseClick(int x, int y);
}

