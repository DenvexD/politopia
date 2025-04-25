package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

import main.Game;
import ui.Button;

public class Board extends Button{
    private ArrayList<ArrayList<Field>> fields = new ArrayList<>();
    private ArrayList<Field> rawOFields;
    private Field field;


    private Game game;
    private int x;
    private int y;
    private Polygon polygonBound;
    private Random random = new Random();

    private int mousePositionX;
    private int mousePositionY;
    private float cumulativeDeviationX;
    private float cumulativeDeviationY;

    private int mouseLastPressedPositionX;
    private int mouseLastPressedPositionY;
    private long timeMousePressed;
    private float velocityX = 0;
    private float velocityY = 0;
    private float accelerationX = 0;
    private float accelerationY = 0;
    private boolean velocityGreaterZeroX;
    private boolean velocityGreaterZeroY;
    
    private boolean mouseExited = false;

    public Board(int widthInFields, int heightInFields, Game game){
        super(null, widthInFields * game.getFieldWidth(), heightInFields * game.getFieldHeight());
        this.game = game;
        this.x = game.getWindowWidth() / 2;
        this.y = game.getWindowHeight() / 2;
        this.initBounds(this.x, this.y);
        initFields();
    }


    public void draw(Graphics g){
        for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
                if (this.isVisable(field)){
                    g.setColor(Color.gray);
                    field.draw(g, field.getX(), field.getY());
                }
            }
        }    
    }

    public void update(){
        for (ArrayList<Field> arrayList : fields) {
            for (Field field : arrayList) {
                field.update();
            }
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
    private void initFields(){
        int i = 0;
        Field prevField = null;
        Field currField = null;
        Field topRowField = null;
        int currRow = 0;
        int rows = (int)(Math.sqrt(Math.pow(this.getWidth() / 2, 2) + Math.pow(this.getHeight()/2, 2)) / (Math.sqrt(Math.pow(game.getFieldWidth() / 2, 2) + Math.pow(game.getFieldHeight() / 2, 2))));
        while (currRow < rows) {
            this.rawOFields = new ArrayList<>();
            int currX = this.polygonBound.xpoints[0] - currRow * game.getFieldWidth()/2;
            int currY = this.polygonBound.ypoints[0] + currRow * game.getFieldHeight()/2;
            int boarderX = this.polygonBound.xpoints[1] - currRow * game.getFieldWidth()/2;
            int boarderY = this.polygonBound.ypoints[1] + currRow * game.getFieldHeight()/2;
            while (currX < boarderX && currY < boarderY) {
                currField = this.initFieldBounds(currX, currY, i);
                currField.number = i;
                this.initFieldLeftRightNeighbours(prevField, currField);
                prevField = currField;
                currX += game.getFieldWidth()/2;
                currY += game.getFieldHeight()/2;
                i++;
            }
            this.fields.add(this.rawOFields);
            this.conectTopAndBottomFields(topRowField, currField);
            topRowField = currField;
            prevField = null;
            currRow++;
        }
    }
    private Field initFieldBounds(int x, int y, int i){
        field = new Field(game.getFieldWidth(), game.getFieldHeight(), getRandomType());
        field.initBounds(x, y + game.getFieldHeight()/2);
        field.setText(Integer.toString(i));
        this.rawOFields.add(field);
        return field;
    }
    private FieldTypes getRandomType(){
        int pick = random.nextInt(FieldTypes.values().length);
        return FieldTypes.values()[pick];
    }
    private void conectTopAndBottomFields(Field topRowField, Field currField){
        if (topRowField != null) {
            while (currField != null) {
                currField.top = topRowField;
                topRowField.bottom = currField;
                currField = currField.left;
                topRowField = topRowField.left;
            }
        }
    }
    private void initFieldLeftRightNeighbours(Field prevField, Field field){
        if (prevField != null){
            field.left = prevField;
            prevField.right = field;
        }
    }
    private void reInitFieldsBounds(int adjustHeight, int adjustWidth){
        Field currField = null;
        int leftBoarderX = this.polygonBound.xpoints[3];
        int currRow = 0;
        int currRowX = this.polygonBound.xpoints[0] - currRow * game.getFieldWidth()/2;
        while (currRowX > leftBoarderX) {
            int currX = this.polygonBound.xpoints[0] - currRow * game.getFieldWidth()/2;
            int currY = this.polygonBound.ypoints[0] + currRow * game.getFieldHeight()/2;
            int rightBoarderX = this.polygonBound.xpoints[1] - currRow * game.getFieldWidth()/2;
            int rightBoarderY = this.polygonBound.ypoints[1] + currRow * game.getFieldHeight()/2;
            while (currX < rightBoarderX && currY < rightBoarderY) {
                currField = this.getNextField(currField);
                currField.setHeight(currField.getHeight() + adjustHeight);
                currField.setWidth(currField.getWidth() + adjustWidth);
                currField.initBounds(currX, currY + currField.getHeight()/2);
                
                currX += game.getFieldWidth()/2;
                currY += game.getFieldHeight()/2;
            }
            currRow++;
            currRowX = this.polygonBound.xpoints[0] - currRow * game.getFieldWidth()/2;
        }
    }
    private Field getNextField(Field currField){
        if (currField == null) {
            return this.fields.getFirst().getFirst();
        }
        int row = (currField.number + 1) / game.getBoardWidthInFields();
        int column = (currField.number + 1) - row * game.getBoardWidthInFields();

        if (row <= game.getBoardWidthInFields()-1 && column <= game.getBoardHeightInFields()-1) {
            return this.fields.get(row).get(column);
        }else{
            return null;
        }

    }



    private boolean isVisable(Field field){
        return field.getPolygonBound().intersects(0, 0, game.getWindowWidth(), game.getWindowHeight());
    }







    public void mouseDragged(int newPositionX, int newPositionY){
        if (!this.mouseExited) {
            resetVelocity();
        int adjustX =  this.mousePositionX - newPositionX;
        int adjustY = this.mousePositionY - newPositionY;
        this.adjustBoardCoordinates(adjustX, adjustY);
        this.mousePositionX = newPositionX;
        this.mousePositionY = newPositionY;
        if (System.currentTimeMillis() - this.timeMousePressed > 1000) {
            this.mouseLastPressedPositionX = newPositionX;
            this.mouseLastPressedPositionY = newPositionY;
            this.timeMousePressed = System.currentTimeMillis();
        }
        }
        
    }


    public void mousePressed(int x, int y){
        this.timeMousePressed = System.currentTimeMillis();
        this.mouseLastPressedPositionX = x;
        this.mouseLastPressedPositionY = y;
        this.mousePositionX = x;
        this.mousePositionY = y;
    }
    public void mouseReleased(int x, int y){
        addVelocityToTheBoard(x, y);
        handleIntersection();

    }
    private void addVelocityToTheBoard(int x, int y){
        int durationMousePressed = (int)( System.currentTimeMillis() - this.timeMousePressed);
        this.velocityX = (float)(x - this.mouseLastPressedPositionX) / (durationMousePressed / 6);
        this.velocityY = (float)(y - this.mouseLastPressedPositionY) / (durationMousePressed / 6);
        this.velocityGreaterZeroX = velocityX > 0;
        this.velocityGreaterZeroY = velocityY > 0;
        this.accelerationX = (float)-velocityX / game.getvelocityMovementFramesDuration();
        this.accelerationY = (float)-velocityY / game.getvelocityMovementFramesDuration();
    }
    public void moveBoardVelocity(){
        if (this.velocityX > 0 == this.velocityGreaterZeroX && this.velocityY > 0 == velocityGreaterZeroY && (this.velocityX != 0 || this.velocityY != 0)) {
            this.adjustBoardCoordinates((int)-this.velocityX, (int)-this.velocityY);
            this.velocityX += this.accelerationX;
            this.velocityY += this.accelerationY;
            handleIntersection();
        }else{
            resetVelocity();
        }
    }
    private void resetVelocity(){
        this.velocityX = 0;
        this.velocityY = 0;
        this.accelerationX = 0;
        this.accelerationY = 0;
    }


    public void mouseWheelMoved(double rotation, int x, int y){
        int sizeChangeX = (this.getWidth() / 80) * 2;
        int sizeChangeY = (this.getHeight() / 80) * 2;
        if (rotation > 0){
            sizeChangeX = -sizeChangeX;
            sizeChangeY = -sizeChangeY;
        }
        if ((!isMaxSize() && rotation < 0) || (!isMinSize(sizeChangeX, sizeChangeY) && rotation > 0)) {
            this.adjustBoardSize(sizeChangeX, sizeChangeY);
            this.adjustBoardCoordinatesOnZoom(sizeChangeX, sizeChangeY, x, y);
            handleIntersection();
        } else{
            if (isMinSize(sizeChangeX, sizeChangeY) && rotation > 0) {
                sizeChangeX = getRestSizeChangeX();
                sizeChangeY = getRestSizeChangeY();
                this.adjustBoardSize(sizeChangeX, sizeChangeY);
                this.adjustBoardCoordinatesOnZoom(sizeChangeX, sizeChangeY, x, y);
                handleIntersection();
            }
        }


    }
    private boolean isMaxSize(){
        return game.getFieldWidth() * 5 > game.getWindowWidth() && game.getFieldHeight() * 5 > game.getWindowHeight();
    }
    private boolean isMinSize(int sizeChangeX, int sizeChangeY){
        return (this.getWidth() + sizeChangeX * game.getBoardWidthInFields())  * game.getMaxBoardWindowSizeRatio() < game.getWindowWidth() && (this.getHeight() + sizeChangeY * game.getBoardHeightInFields()) * game.getMaxBoardWindowSizeRatio() < game.getWindowHeight();
    }
    private int getRestSizeChangeX(){
        int sizeChangeX = (int)(this.getWidth() * game.getMaxBoardWindowSizeRatio() - game.getWindowWidth()) / game.getBoardWidthInFields();
        if (Math.ceilMod(sizeChangeX, 2) == 0) {
            return -sizeChangeX;
        }else{
            return -(sizeChangeX + 1);
        }
    }
    private int getRestSizeChangeY(){
        int sizeChangeY = (int)(this.getHeight() * game.getMaxBoardWindowSizeRatio() - game.getWindowHeight()) / game.getBoardHeightInFields();
        if (Math.ceilMod(sizeChangeY, 2) == 0) {
            return -sizeChangeY;
        }else{
            return -(sizeChangeY + 1);
        }
    }

    public void handleIntersection(){
        int adjustmentX = 0;
        int adjustmentY = 0;
        int topCornerY = this.getPolygonBound().ypoints[0];
        int downCornerY = this.getPolygonBound().ypoints[2];
        int leftCornerX = this.getPolygonBound().xpoints[3];
        int rightCornerX = this.getPolygonBound().xpoints[1];

        if (topCornerY > game.getWindowHeight()/2) {
            adjustmentY = topCornerY - game.getWindowHeight()/2;  
        }
        if (downCornerY < game.getWindowHeight()/2) {
            adjustmentY = downCornerY - game.getWindowHeight()/2; 
        }
        if (rightCornerX < game.getWindowWidth()/2) {
            adjustmentX = rightCornerX - game.getWindowWidth()/2;
        }
        if (leftCornerX > game.getWindowWidth()/2) {
            adjustmentX = leftCornerX - game.getWindowWidth()/2;
        }
        this.adjustBoardCoordinates(adjustmentX, adjustmentY);
    }




    public void adjustBoardCoordinates(int adjustX, int adjustY){
        this.adjustFieldsCoordinates(adjustX, adjustY);
        this.x -= adjustX;
        this.y -= adjustY;
        this.adjustBounds(this.x, this.y);
    }
    public void moveCoordinatesToCentre(int x, int y){
        int distanceY = game.getWindowHeight()/2 - y;
        int distanceX = game.getWindowWidth()/2 - x;
        this.adjustBoardCoordinates(distanceX, distanceY);
    }
    private void adjustFieldsCoordinates(int adjustX, int adjustY){
       for (ArrayList<Field> rawOFields : this.fields) {
            for (Field field : rawOFields) {
                field.adjustBounds(field.getX() - adjustX, field.getY() - adjustY);
            }
        }
    }

    public void adjustBoardCoordinatesOnZoom(int sizeChangeX, int sizeChangeY, int x, int y){
        float widthRatio = this.getWidth() / ((float)this.getWidth() - sizeChangeX * game.getBoardWidthInFields());
        float heightRatio = this.getHeight() / ((float)this.getHeight() - sizeChangeY * game.getBoardHeightInFields());
        int oldDistanceX = x - this.getX();
        int oldDistanceY = y - this.getY();
        float adjustDistanceX = (widthRatio - 1) * oldDistanceX;
        float adjustDistanceY = (heightRatio - 1) * oldDistanceY;

        cumulativeDeviationX += adjustDistanceX - Math.round(adjustDistanceX);
        cumulativeDeviationY += adjustDistanceY - Math.round(adjustDistanceY);
        int intPartDeviationX = (int)cumulativeDeviationX;
        int intPartDeviationY = (int)cumulativeDeviationY;

        this.adjustBoardCoordinates(Math.round(adjustDistanceX) + intPartDeviationX, Math.round(adjustDistanceY) + intPartDeviationY);
        cumulativeDeviationX -= intPartDeviationX;
        cumulativeDeviationY -= intPartDeviationY;
    }
    public void adjustBoardSize(int adjustHeight, int adjustWidth){
        this.setWidth(this.getWidth() + adjustWidth * game.getBoardWidthInFields());
        this.setHeight(this.getHeight() + adjustHeight * game.getBoardHeightInFields());
        this.adjustBounds(this.x, this.y);
        this.adjustFieldsSize(adjustHeight, adjustWidth);
        
    }
    private void adjustFieldsSize(int adjustHeight, int adjustWidth){
        game.setFieldHeight(game.getFieldHeight() + adjustHeight);
        game.setFieldWidth(game.getFieldWidth() + adjustWidth);
        this.reInitFieldsBounds(adjustHeight, adjustWidth);
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Polygon getPolygonBound(){
        return polygonBound;
    }

    public void setMouseExited(boolean status){
        this.mouseExited = status;
        this.handleIntersection();
    }
    public Field getClickedField(int x, int y) {
        for (ArrayList<Field> arrayList : fields) {
            for (Field field : arrayList) {
                if (field.getPolygonBound().contains(x, y)) {
                    return field;
                }
            }
        }
        return null;
    }
    
    public Field getFieldBasedOnId(int id){
        int row = id / this.fields.size();
        int column = id % this.fields.getFirst().size();
        return this.fields.get(row).get(column);
    }
    


}
