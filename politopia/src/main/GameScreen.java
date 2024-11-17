package main;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.Graphics;

public class GameScreen extends JPanel {
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private BufferedImage img;
    private Random random;

    public GameScreen(BufferedImage img){
        random = new Random();
        this.img = img;
        sprites = getSprites();
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paintMapRandomly(g);
    }

    public ArrayList<BufferedImage> getSprites(){
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){

                sprites.add(img.getSubimage(x*32, y*32, 32, 32));
            }
        }
        return sprites;
    }

    public void paintMapRandomly(Graphics g){
        for(int x = 0; x < 20; x++){
            for(int y = 0; y < 20; y++){
                int randomSpriteNumber = random.nextInt(100);
                BufferedImage randomSprite = sprites.get(randomSpriteNumber);
                g.drawImage(randomSprite, x*32, y*32, null);
            }
        }
    }
}
