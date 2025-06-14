package displays;

import java.util.ArrayList;

import main.Game;
import objects.Field;

public class FieldDisplay extends Display {
    private Field field;

    public FieldDisplay(Game game) {
        super(game);
    }

    public void setField(Field field){
        this.field = field;
    }

    @Override
    protected void createButtonsBasedOnActions() {
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
                case defrost:
                    ActionButton defrost = new ActionButton(this.x + currX, this.y, this.height, defrostImg, Actions.defrost);
                    this.actionButtons.add(defrost);
                    break;
                default:
                    break;
            }
            currX += this.height + this.distanceBetweenButtons;
            }
        }
    }

    @Override
    public void mouseClick(int x, int y) {
        Actions action = null;
        for (ActionButton actionButton : actionButtons) {
            if (actionButton.isClicked(x, y) != null) {
                action = actionButton.isClicked(x, y);
            }
        }
        if (action == Actions.clearForest) {
            field.clearForest();
        }
        if (action == Actions.growForest) {
            field.createForest();
        }
        if (action == Actions.defrost) {
            field.removeSnowCovered();
        }
    }

}
