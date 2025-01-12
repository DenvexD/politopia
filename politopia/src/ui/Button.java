package ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

public class Button {
    protected String text;
    private int height;
    private int width;
    private int textWidth;
    private int textHeight;
    private int textX;
    private int textY;
    private Rectangle bound;
    private boolean mousePressed, mouseOver;
    private Image img;
    
    public Button(String text, int width, int height){
        this.text = text;
        this.width = width;
        this.height = height;
    }
    public void draw(Graphics g, int x, int y){
        initBounds(x, y);
        if(this.img == null){
            this.drawBody(g, x, y);
            this.drawBorders(g, x, y);
        }else{
            g.drawImage(this.img, x, y, this.width, this.height, null);
        }

        if(this.text != null){
            this.drawText(g, x, y);
        }
        
    }


    private void drawBody(Graphics g, int x, int y){
        if (this.mouseOver){
            g.setColor(Color.DARK_GRAY);
        }else{
            g.setColor(Color.GRAY);
        }
        g.fillRect(x, y, this.width, this.height);
  
    }
    private void drawBorders(Graphics g, int x, int y){
        g.setColor(Color.BLACK);
        g.drawRect(x, y, this.width, this.height);
        if (this.mousePressed) {
            g.drawRect(x+1, y+1, this.width-2, this.height-2);
            g.drawRect(x+2, y+2, this.width-4, this.height-4);
        }

    }
    protected void drawText(Graphics g, int x, int y){
        g.setColor(Color.WHITE);
        textWidth = g.getFontMetrics().stringWidth(text);
        textHeight = g.getFontMetrics().getHeight();
        
        textX = x + this.width / 2 - textWidth / 2;
        textY = y + this.height / 2 + textHeight / 4;
        g.drawString(text, textX, textY);
    }
    public void initBounds(int x, int y){
        this.bound = new Rectangle(x, y, this.width, this.height);
    }

    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public Rectangle getBound(){
        return this.bound;
    }

    public void setMousePressed(){
        this.mousePressed = true;
    }
    public void setMouseOver(){
        this.mouseOver = true;
    }
    public void setText(String text){
        this.text = text;
    }

    public boolean isMousePressed(){
        return this.mouseOver;
    }
    public boolean isMouseOver(){
        return this.mouseOver;
    }
    public void resetMousePressed(){
        this.mousePressed = false;
    }
    public void resetMouseOver(){
        this.mouseOver = false;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public void setImage(Image img){
        this.img = img;
    }
    public Image getImage(){
        return this.img;
    }

}
