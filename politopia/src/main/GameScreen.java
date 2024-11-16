package main;

import javax.swing.JPanel;
import java.awt.Graphics;

public class GameScreen extends JPanel {
    public gameScreen(){

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(50,50,100,100);
    }
}
