package objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;

public class ActionButton {
    private int x;
    private int y;
    private int width;
    private Image img;
    private HeroDisplayActions action;
    private Ellipse2D.Double bound;
    public ActionButton(int x, int y, int width, Image img, HeroDisplayActions action){
        this.x = x;
        this.y = y;
        this.width = width;
        this.img = img;
        this.action = action;
        this.initBounds();
    }
    public void draw(Graphics2D g2d){
        g2d.drawImage(img, x, y, width, width, null);
    }
    private void initBounds(){
        this.bound = new Ellipse2D.Double(this.x, this.y, this.width, this.width);
    }
    public HeroDisplayActions isClicked(int x, int y){
        if(this.bound.contains(x, y)){
            return this.action;
        }else{
            return null;
        }
    }
}
