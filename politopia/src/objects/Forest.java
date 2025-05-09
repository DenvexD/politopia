package objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Forest {
    private Field field;
    private Image img = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Imperius forest.png");
    public Forest(Field field){
        this.field = field;
    }
    public void draw(Graphics2D g2d){
        int cornerX = this.field.getX() - this.field.getWidth()/2;
        int cornerY = this.field.getY() - this.field.getWidth()/2;
        g2d.drawImage(img, cornerX, cornerY, this.field.getWidth(), this.field.getHeight(), null);
    }
}
