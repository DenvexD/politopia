package objects;

import ui.Button;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.util.ArrayList;

import displays.Actions;
import displays.Display;
import displays.FieldDisplay;
import main.Game;
import objects.fieldObjects.Snow;
import scenes.Play;
import structures.Structure;

public class Field extends Button {
    private Play play;
    private FieldTypes fieldType;
    public Field left;
    public Field right;
    public Field top;
    public Field bottom;
    public int number;
    private Polygon polygonBound;
    private int x;
    private int y;
    private Image fieldImage;
    private boolean isSnowCovered = true;
    private Snow snow;
    private final float imageRatio = (float)1909 / 1208;
    private Hero hero = null;
    private CircleMark circleMark = null;
    private structures.Forest forest;
    private Structure structure;
    private boolean isClickable;
    private boolean highlightingAnimationIsInProgress;
    private boolean isClicked = false;
    private int highlightingAnimationStage = 0;
    private int highlightingAnimationTick = 0;
    private int highlightingAnimationStagesAmount = 2;
    private int highlightingAnimationTicksAmount = 2;
    private Display display;
    private ArrayList<Actions> actions = new ArrayList<Actions>();

    public Field(int width, int height, FieldTypes fieldType, boolean isClickable, Play play){
        super(null, width, height);
        this.play = play;
        this.isClickable = isClickable;
        this.fieldType = fieldType;
        this.setImageBasedOnType();
        this.left = null;
        this.right = null;
        this.top = null;
        this.bottom = null;
        this.snow = new Snow(this);
        this.display = play.getFieldDisplay();
    }
    @Override
    public void draw(Graphics2D g2d, int x, int y){
        this.initBounds(x, y);
        int cornerX = x - this.getWidth()/2;
        int cornerY = y - this.getWidth()/2;
        if (isSnowCovered) {
            this.snow.draw(g2d, cornerX, cornerY, this.getWidth(), (int)(this.getHeight() * imageRatio));
        }else{
            if (isClicked) {
                drawClickedState(g2d);
            }else{
                g2d.drawImage(fieldImage, cornerX, cornerY, this.getWidth(), (int)((float)this.getHeight() * imageRatio), null);
            }

            drawFieldsObjects(g2d, x, y);
            

        }
    }

    private void drawClickedState(Graphics2D g2d){
        int cornerX = x - this.getWidth()/2;            //reusing what i already had above. Fix?
        int cornerY = y - this.getWidth()/2;
        g2d.drawImage(fieldImage, cornerX, cornerY, this.getWidth(), (int)((float)this.getHeight() * imageRatio), null);
        drawHighligtedState(g2d);
    }

    private void drawHighligtedState(Graphics2D g2d){
        float transparacy;
        g2d.setColor(Color.BLUE);
        if (highlightingAnimationIsInProgress) {
            transparacy = 0.3f * highlightingAnimationStage;
        }else{
            transparacy = 1.0f;
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparacy));
        g2d.setStroke(new BasicStroke(5.0f));
        g2d.drawPolygon(polygonBound);
        g2d.setColor(Color.BLACK);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.setStroke(new BasicStroke(1.0f));
    }

    private void drawFieldsObjects(Graphics2D g2d, int x, int y){
        if (this.text != null) {
            this.drawText(g2d, x, y);
        }
        if (this.hero != null) {
            this.hero.draw(g2d);
        }
        if (this.structure != null) {
            this.structure.render(g2d);
        }
        if (this.circleMark != null) {
            this.circleMark.draw(g2d);
        }
    }
    public void update(){
        if (isSnowCovered) {
            snow.update();
        }
        if (highlightingAnimationIsInProgress) {
            updateHighlightAnimation();
        }


    }

    public void mouseClicked(){
        setActions();
        showDisplay();
        isClicked = true;
        if (isSnowCovered) {
            this.snow.mouseClicked();
        }else{
            highlightingAnimationIsInProgress = true;
        }
    }
    private void showDisplay(){
        ((FieldDisplay)display).setField(this);
        display.setActions(actions);
        display.setVisable(true);
    }
    private void setActions(){
        actions.clear();
        if (isSnowCovered) {
            actions.add(Actions.defrost);
        }
        else if (getForest() == null) {
            actions.add(Actions.growForest);
        }
    }


    public void unclick(){
        isClicked = false;
        display.setVisable(false);
    }

    private void updateHighlightAnimation(){
        if (highlightingAnimationTick < highlightingAnimationTicksAmount) {
            highlightingAnimationTick++;
        }else{
            if (highlightingAnimationStage < highlightingAnimationStagesAmount) {
                highlightingAnimationStage++;
            }else{
                highlightingAnimationStage = 0;
                highlightingAnimationIsInProgress = false;
            }
            highlightingAnimationTick = 0;
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
                    fieldImage = (Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/deep.png"));  
                    break;
                case Imperius:
                    fieldImage = (Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Imperius ground.png"));
                    break;
                case Vengir:
                    fieldImage = (Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Vengir ground.png"));
                    break;
                case Zebasi:
                    fieldImage = (Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Zebasi ground.png"));
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

    public void removeSnowCovered(){
        this.isSnowCovered = false;
        isClickable = true;
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
        this.circleMark = new CircleMark(distanseSteps, prevField, this);
    }
    public void setCircleMark(CircleMark circleMark){
        this.circleMark = circleMark;
    }
    public FieldTypes getFieldType(){
        return this.fieldType;
    }
    public void createForest(){
        this.structure = new structures.Forest(this);
    }
    public structures.Forest getForest(){
        return this.forest;
    }
    public void clearForest(){
        this.structure = null;
    }
    public void setStructure(Structure structure){
        this.structure = structure;
    }
    public Structure getStructure(){
        return structure;
    }
    public boolean isClickable(){
        return isClickable;
    }
    public Snow getSnow(){
        return snow;
    }
    public Display getDisplay(){
        return display;
    }
    public Play getPlay(){
        return play;
    }

}
