package ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Button {
    private String text;
    private int height;
    private int width;
    private int textWidth;
    private int textHeight;
    private int textX;
    private int textY;
    private Rectangle bound;
    
    public Button(String text, int width, int height){
        this.text = text;
        this.width = width;
        this.height = height;
    }
    public void draw(Graphics g, int x, int y){
        initBounds(x, y);
        this.drawBody(g, x, y);
        this.drawBorders(g, x, y);
        this.drawText(g, x, y);
    }

    private void drawBody(Graphics g, int x, int y){
        g.setColor(Color.GRAY);
        g.fillRect(x, y, this.width, this.height);
    }
    private void drawBorders(Graphics g, int x, int y){
        g.setColor(Color.WHITE);
        g.drawRect(x, y, this.width, this.height);

    }
    private void drawText(Graphics g, int x, int y){
        textWidth = g.getFontMetrics().stringWidth(text);
        textHeight = g.getFontMetrics().getHeight();
        
        textX = x + this.width / 2 - textWidth / 2;
        textY = y + this.height / 2 + textHeight / 4;
        g.drawString(text, textX, textY);
    }
    private void initBounds(int x, int y){
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

}
