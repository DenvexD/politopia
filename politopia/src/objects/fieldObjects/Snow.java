package objects.fieldObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import objects.Field;
import objects.objectsMethods;

import java.util.ArrayList;

public class Snow implements objectsMethods {
    private int animationTick = 0;
    private int animationDuration = 4;
    private int animationStage = 0;
    private Field field;
    private boolean animationIsInProgress = false;
    private ArrayList<Image> animationStages = new ArrayList<Image>();
    public Snow(Field field){
        this.field = field;
        this.animationStages.add(Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/clouds.png"));
        this.animationStages.add(Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Cloud puff.png"));
    }

    public void mouseClicked(){
        System.out.println("click");
        this.animationTick = 0;
        this.animationStage = 0;
        this.animationIsInProgress = true;
        this.field.setIsSnowCovered(false);
    }
    public void update(){
        if (this.animationIsInProgress) {
            if (this.animationTick < this.animationDuration) {
                this.animationTick ++;
            }else{
                if (this.animationStage + 1 < this.animationStages.size()) {
                    this.animationStage ++;
                }else{
                    this.animationStage = 0;
                    this.animationIsInProgress = false;
                }
                this.animationTick = 0;

            }
        }
    }
    public void draw(Graphics g, int x, int y, int width, int height){
        Image img = this.animationStages.get(0);
        g.drawImage(img, x, y, width, height, null);
        if (this.animationStage == 1) {
            Image imga = this.animationStages.get(this.animationStage);
            g.drawImage(imga, x, y, width, height, null);
        }

    }
}
