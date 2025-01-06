package main;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import java.awt.Graphics;

public class GameScreen extends JPanel {
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private BufferedImage img;
    private Random random;
    private Game game;
    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;


    


    public GameScreen(BufferedImage img, Game game){
        random = new Random();
        this.img = img;
        this.game = game;
        sprites = getSprites(img);


        
    }
    

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        switch (GameStates.gameState) {
            case MENU:
                this.game.getMenu().render(g);
        
            case SETTINGS:
                break;
            case PLAYING:
                this.game.getPlay().render(g);
        }
    }




    public ArrayList<BufferedImage> getSprites(BufferedImage img){
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


        public void initInputs(){
        myMouseListener = new MyMouseListener(game);
        keyboardListener = new KeyboardListener();
        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addMouseWheelListener(myMouseListener);
        addKeyListener(keyboardListener);
        requestFocus();
        System.out.println("sded");
        
    }


}
