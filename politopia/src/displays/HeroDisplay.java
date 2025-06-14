package displays;

import java.util.ArrayList;

import main.Game;
import objects.Hero;

public class HeroDisplay extends Display {

    public HeroDisplay(Game game) {
        super(game);
    }

    
    public void setHero(Hero hero){
        this.hero = hero;
    }


    public void mouseClick(int x, int y){
        Actions action = null;
        for (ActionButton actionButton : actionButtons) {
            if (actionButton.isClicked(x, y) != null) {
                action = actionButton.isClicked(x, y);
            }
        }
        if (action == Actions.clearForest) {
            this.hero.getField().clearForest();
        }
        if (action == Actions.growForest) {
            this.hero.getField().createForest();
        }
    }
    protected void createButtonsBasedOnActions(){
    if (this.actions != null) {
        this.actionButtons = new ArrayList<ActionButton>();
        int currX = (this.width - ((this.actions.size() - 1) * x + this.actions.size() * this.height))/2;
        for (Actions action : this.actions) {
            switch (action) {
                case clearForest:
                    ActionButton cutForest = new ActionButton(this.x + currX, this.y, this.height, clearForsetImg, Actions.clearForest);
                    this.actionButtons.add(cutForest);
                    break;
            
                case growForest:
                    ActionButton growForest = new ActionButton(this.x + currX, this.y, this.height, growForestImg, Actions.growForest);
                    this.actionButtons.add(growForest);
                    break;
                default:
                    break;
            }
            currX += this.height + this.distanceBetweenButtons;
            }
        }
    }


}
