package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import main.GameStates;
import main.Game;



public class MyMouseListener implements MouseListener, MouseMotionListener {
    private Game game;

    public MyMouseListener(Game game) {
		this.game = game;
	}

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.gameStates) {
            case MENU:
                game.getMenu().mouseMoved(e.getX(), e.getY());;
            case SETTINGS:
                break;
            case PLAYING:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameStates.gameStates) {
            case MENU:
                game.getMenu().mouseClicked(e.getX(), e.getY());;
            case SETTINGS:
                break;
            case PLAYING:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.gameStates) {
            case MENU:
                game.getMenu().mousePressed(e.getX(), e.getY());;
            case SETTINGS:
                break;
            case PLAYING:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.gameStates) {
            case MENU:
                game.getMenu().mouseReleased(e.getX(), e.getY());;
            case SETTINGS:
                break;
            case PLAYING:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
