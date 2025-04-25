package objects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import main.Game;

public class HeroDisplay {
    private Rectangle bounds;
    private Game game;
    private Hero hero;
    private boolean isVisable = false;
    private int x = 0;
    private int y = 500;
    private int width;
    private int height;
    private int distanceBetweenButtons = 50;
    private ArrayList<HeroDisplayActions> actions = new ArrayList<HeroDisplayActions>();
    private ArrayList<ActionButton> actionButtons;
    Image clearForsetImg = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Clear forest.png");
    Image growForestImg = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Grow forest.png");

    public HeroDisplay(Game game){
        this.game = game;
        this.initBounds();
    }
    public void draw(Graphics g){
        if (isVisable) {
            g.setColor(Color.black);
            g.fillRect(this.x, this.y, this.width, this.height);
            for (ActionButton actionButton : actionButtons) {
                actionButton.draw(g);
            }
        }
    }
    private void initBounds(){
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
    public void setActions(ArrayList<HeroDisplayActions> actions){
        this.actions = actions;
        this.createButtonsBasedOnActions();
    }
    public void setHero(Hero hero){
        this.hero = hero;
    }
    private void createButtonsBasedOnActions(){
        if (this.actions != null) {
            this.actionButtons = new ArrayList<ActionButton>();
            int currX = (this.width - ((this.actions.size() - 1) * x + this.actions.size() * this.height))/2;
            for (HeroDisplayActions action : this.actions) {
                switch (action) {
                    case clearForest:
                        ActionButton cutForest = new ActionButton(this.x + currX, this.y, this.height, clearForsetImg, HeroDisplayActions.clearForest);
                        this.actionButtons.add(cutForest);
                        break;
                
                    case growForest:
                        ActionButton growForest = new ActionButton(this.x + currX, this.y, this.height, growForestImg, HeroDisplayActions.growForest);
                        this.actionButtons.add(growForest);
                        break;
                }
                currX += this.height + this.distanceBetweenButtons;
            }
        }

    }
    public boolean isMouseClicked(int x, int y){
        return this.isVisable() && bounds.contains(x, y);
    }

    public void mouseClick(int x, int y){
        HeroDisplayActions action = null;
        for (ActionButton actionButton : actionButtons) {
            if (actionButton.isClicked(x, y) != null) {
                action = actionButton.isClicked(x, y);
            }
        }
        if (action == HeroDisplayActions.clearForest) {
            this.hero.getField().clearForest();
        }
        if (action == HeroDisplayActions.growForest) {
            this.hero.getField().createForest();
        }
    }
}

