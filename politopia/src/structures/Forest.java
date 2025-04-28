package structures;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import objects.Field;

public class Forest extends Structure{
    private Field field;
    private Image img = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Imperius forest.png");
    public Forest(Field field){
        super(field);
    }
    public void render(Graphics2D g2d){
        int cornerX = this.field.getX() - this.field.getWidth()/2;
        int cornerY = this.field.getY() - this.field.getWidth()/2;
        g2d.drawImage(img, cornerX, cornerY, this.field.getWidth(), this.field.getHeight(), null);
    }
    @Override
    public void mouseClicked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }
    @Override
    public void unclick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unclick'");
    }
    @Override
    public void isClicked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isClicked'");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}