package objects;

import ui.Button;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.util.ArrayList;
import objects.fieldObjects.Snow;

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
    private boolean isSnowCovered = true;
    private Snow snow;
    private final float imageRatio = (float)1909 / 1208;
    private Hero hero = null;
    private CircleMark circleMark = null;

    public Field(int width, int height, FieldTypes fieldType){
        super(null, width, height);
        this.fieldType = fieldType;
        this.setImageBasedOnType();
        this.left = null;
        this.right = null;
        this.top = null;
        this.bottom = null;
        this.snow = new Snow(this);
    }
    @Override
    public void draw(Graphics g, int x, int y){
        this.initBounds(x, y);
        int cornerX = x - this.getWidth()/2;
        int cornerY = y - this.getWidth()/2;
        if (isSnowCovered) {
            this.snow.draw(g, cornerX, cornerY, this.getWidth(), (int)(this.getHeight() * imageRatio));
        }else{
            for (Image image : this.fieldComponents) {
                g.drawImage(image, cornerX, cornerY, this.getWidth(), (int)((float)this.getHeight() * imageRatio), null);
            }
        }

        if (this.text != null) {
            this.drawText(g, x, y);
        }
        if (this.hero != null) {
            this.hero.draw(g);
        }
        if (this.circleMark != null) {
            this.circleMark.draw(g);
        }

        
    }
    public void update(){
        snow.update();
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
    public void mouseClicked(){
        if (isSnowCovered) {
            this.snow.mouseClicked();
        }
    }
    public void setIsSnowCovered(boolean status){
        this.isSnowCovered = status;
    }
    public void setHero(Hero hero){
        this.hero = hero;
    }
    public Hero getHero(){
        return this.hero;
    }
    public CircleMark getCircleMark(){
        return this.circleMark;
    }
    public void createCircleMark(int distanseSteps, Field prevField){
        this.circleMark = new CircleMark(distanseSteps, prevField, null, this);
    }
    public void setCircleMark(CircleMark circleMark){
        this.circleMark = circleMark;
    }
}
