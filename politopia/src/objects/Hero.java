package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import objects.Field;
public class Hero{
    private Field field;
    private int range;
    private Image img = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Nature bunny.png");
    public Hero(Field field, int range){
        this.field = field;
        this.range = range;
        this.field.setHero(this);
    }

    public void draw(Graphics g){
        int leftCornerX = this.field.getX() - this.getWidth()/2;
        int leftCornerY = this.field.getY() - this.getHeight() / 2;
        g.drawImage(img, leftCornerX, leftCornerY, this.getWidth(), this.getHeight(), null);
    }

    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    private int getWidth(){
        return this.field.getWidth() / 2;
    }
    private int getHeight(){
        return this.field.getHeight() / 3;
    }

}

