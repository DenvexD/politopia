package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Game;

public class KeyboardListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_I) {
            System.out.println("I is pressed");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                Game.getPlay().WGotPressed();
                break;
            case KeyEvent.VK_A:
                Game.getPlay().AGotPressed();
                break;
            case KeyEvent.VK_S:
                Game.getPlay().SGotPressed();
                break;
            case KeyEvent.VK_D:
                Game.getPlay().DGotPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                Game.getPlay().WGotReleased();
                break;
            case KeyEvent.VK_A:
                Game.getPlay().AGotReleased();
                break;
            case KeyEvent.VK_S:
                Game.getPlay().SGotReleased();
                break;
            case KeyEvent.VK_D:
                Game.getPlay().DGotReleased();
                break;
            default:
                break;
        }
    }
    

}
