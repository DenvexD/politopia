package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import ui.Button;
import main.GameWindow;

import main.Game;
import main.GameStates;



public class Menu extends GameScene implements scenesMethods{
    private Button playButton;
    private Button settingsButton;
    private Button quitButton;
    private int buttonWidth = 150;
    private int buttonHeight = 75;
    private int yOffset = 50;
    private ArrayList<Button> buttonDiv = new ArrayList<>();
    public Menu(Game game){
        super(game);
        playButton = new Button("play", buttonWidth, buttonHeight);
        settingsButton = new Button("settings", buttonWidth, buttonHeight);
        quitButton = new Button("quit", buttonWidth, buttonHeight);
        buttonDiv.add(settingsButton);
        buttonDiv.add(playButton);
        buttonDiv.add(quitButton);
    }

    @Override
    public void render(Graphics g) {
        drawButtonDiv(g, buttonDiv);
    }


    private int getButtonDivCentreX(Button button){
        return GameWindow.getWindowWidth() / 2;
    }
    private int getButtonDivCentreY(int divHight){
        return GameWindow.getWindowHeight() / 2;
    }
    private void drawButtonDiv(Graphics g, ArrayList<Button> buttonDiv){
        int divHight = getButtonDivHight(buttonDiv);
        int i = 0;
        int compoundHight = 0;
        for (Button button : buttonDiv) {
            int yCoordinates = getButtonDivCentreY(divHight) - divHight/2 + button.getHeight()/2 + i * yOffset + compoundHight;
            button.draw(g, getButtonDivCentreX(button), yCoordinates);
            i ++;
            compoundHight += button.getHeight();
        }
    }

    private int getButtonDivHight(ArrayList<Button> buttonDiv){
        int divHight = 0;
        for (Button button : buttonDiv) {
            divHight += button.getHeight();
        }
        divHight += yOffset * buttonDiv.size();
        return divHight;
    }

    public void mouseClicked(int x, int y){
        if(playButton.getBound().contains(x, y)){
            GameStates.gameState = GameStates.PLAYING;
            System.out.println("played got clicked");
        }
        if(settingsButton.getBound().contains(x, y)){
            System.out.println("settings got clicked");
        }
        if(quitButton.getBound().contains(x, y)){
            System.exit(0);
        }
    }

    public void mousePressed(int x, int y) {
        if(playButton.getBound().contains(x, y)){
            playButton.setMousePressed();
        }
        if(settingsButton.getBound().contains(x, y)){
            settingsButton.setMousePressed();
        }
        if(quitButton.getBound().contains(x, y)){
            quitButton.setMousePressed();
        }
    }

    public void mouseReleased(int x, int y) {
        playButton.resetMousePressed();
        quitButton.resetMousePressed();
        settingsButton.resetMousePressed();
    }

    public void mouseMoved(int x, int y) {
        playButton.resetMouseOver();
        quitButton.resetMouseOver();
        settingsButton.resetMouseOver();

        if(playButton.getBound().contains(x, y)){
            playButton.setMouseOver();
        }
        if(settingsButton.getBound().contains(x, y)){
            settingsButton.setMouseOver();
        }
        if(quitButton.getBound().contains(x, y)){
            quitButton.setMouseOver();
    }
}
}
