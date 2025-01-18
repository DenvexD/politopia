package objects;

import ui.Button;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Field extends Button {
    private FieldTypes fieldType;
    public Field left;
    public Field right;
    public Field top;
    public Field bottom;
    public int number;
    private Polygon polygonBound;
    private int x;
    private int y;
    private ArrayList<Image> fieldComponents = new ArrayList<Image>();

    public Field(int width, int height, FieldTypes fieldType){
        super(null, width, height);
        this.fieldType = fieldType;
        this.setImageBasedOnType();
        this.left = null;
        this.right = null;
        this.top = null;
        this.bottom = null;
    }
    @Override
    public void draw(Graphics g, int x, int y){
        this.initBounds(x, y);

        for (Image image : this.fieldComponents) {
            g.drawImage(image, x - this.getWidth()/2, y - this.getWidth()/2, this.getWidth(), this.getHeight(), null);
        }
        if (this.text != null) {
            this.drawText(g, x, y);
        }
        
    }
    @Override
    public void initBounds(int x, int y){
        this.x = x;
        this.y = y;
        int[] xPoints = {x, x + this.getWidth() / 2, x, x - this.getWidth() / 2};
        int[] yPoints = {y - this.getHeight() / 2, y, y + this.getHeight() / 2, y};
        this.polygonBound = new Polygon(xPoints, yPoints, 4);
    }
    public void adjustBounds(int x, int y){
        this.x = x;
        this.y = y;
        this.polygonBound.xpoints = new int[]{x, x + this.getWidth() / 2, x, x - this.getWidth() / 2};
        this.polygonBound.ypoints = new int[]{y - this.getHeight() / 2, y, y + this.getHeight() / 2, y};
        this.polygonBound.invalidate();
    }
    public void setType(FieldTypes fieldType){
        this.fieldType = fieldType;
        this.setImageBasedOnType();
    }
    public Polygon getPolygonBound(){
        return this.polygonBound;
    }
    private void setImageBasedOnType(){
        if (this.fieldType != null) {
            switch (this.fieldType) {
                case DEEP_WATER:
                    this.fieldComponents.add(Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/deep.png"));  
                    break;
                case CLOUDS:
                    this.fieldComponents.add(Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/clouds.png"));  
                    break;
                case Imperius:
                    this.fieldComponents.add(Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Imperius ground.png"));
                    break;
                case Vengir:
                    this.fieldComponents.add(Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Vengir ground.png"));
                    break;
                case Zebasi:
                    this.fieldComponents.add(Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Zebasi ground.png"));
                    break;
            }
        }
    }

    public int getNumberOfNeighbours(){
        int i = 0;
        if (this.left != null) {
            i++;
        }
        if (this.right != null) {
            i++;
        }
        if (this.bottom != null) {
            i++;
        }
        if (this.top != null) {
            i++;
        }
        return i;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getY(){
        return this.y;
    }
    public int getX(){
        return this.x;
    }
    
}
