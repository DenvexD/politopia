package objects.fieldObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import objects.Field;
import objects.objectsMethods;

public class Snow implements objectsMethods {
    private int animationTick = 0;
    private int animationDuration = 3;
    private int animationStage = 0;
    private Field field;
    private boolean hoveringAnimationIsInProgress = false;
    private int animationStagesCount = 4;
    private Image img;
    private int adjustAnimationY;
    public Snow(Field field){
        this.field = field;
        this.img = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/clouds.png");
    }

    public void mouseClicked(){
        this.animationTick = 0;
        this.animationStage = 0;
        this.hoveringAnimationIsInProgress = true;
    }
    public void update(){
        if (this.hoveringAnimationIsInProgress) {
            updateHoveringAnimation();
        }
    }
    public void draw(Graphics2D g2d, int x, int y, int width, int height){
        if (this.animationStage > animationStagesCount / 2) {
            adjustAnimationY += 5;
        }else if (animationStage != 0) {
            adjustAnimationY -= 5;
        }
        g2d.drawImage(img, x, y + adjustAnimationY, width, height, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(field.getPolygonBound());
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));


    }
    private void updateHoveringAnimation(){
        if (this.animationTick < this.animationDuration) {
            this.animationTick ++;
        }else{
            if (this.animationStage < this.animationStagesCount) {
                this.animationStage ++;
            }else{
                this.animationStage = 0;
                this.hoveringAnimationIsInProgress = false;
            }
            this.animationTick = 0;

        }
    }
}
