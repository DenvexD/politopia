package displays;

import java.util.ArrayList;

import main.Game;
import structures.Structure;

public class StructureDispaly extends Display {
    private Structure structure;

    public StructureDispaly(Game game) {
        super(game);
    }
    public void setStructure(Structure structure){
        this.structure = structure;
    }

    @Override
    protected void createButtonsBasedOnActions() {
    if (this.actions != null) {
        this.actionButtons = new ArrayList<ActionButton>();
        int currX = (this.width - ((this.actions.size() - 1) * x + this.actions.size() * this.height))/2;
        for (Actions action : this.actions) {
            switch (action) {
                case destroy:
                    ActionButton destroyButton = new ActionButton(this.x + currX, this.y, this.height, destroyImg, Actions.destroy);
                    this.actionButtons.add(destroyButton);
                    break;
            
                case levelup:
                    ActionButton leveluppButton = new ActionButton(this.x + currX, this.y, this.height, levelupImg, Actions.levelup);
                    this.actionButtons.add(leveluppButton);
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
        if (action == Actions.destroy) {
            structure.destroy();
        }
        if (action == Actions.levelup) {
            structure.levelup();
        }
    }

}
