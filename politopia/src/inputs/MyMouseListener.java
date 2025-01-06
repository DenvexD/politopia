package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import main.GameStates;
import main.Game;



public class MyMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {
    private Game game;

    public MyMouseListener(Game game) {
		this.game = game;
	}

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                break;
            case SETTINGS:
                break;
            case PLAYING:
                game.getPlay().mouseDragged(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.gameState) {
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
        switch (GameStates.gameState) {
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
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mousePressed(e.getX(), e.getY());;
            case SETTINGS:
                break;
            case PLAYING:
                game.getPlay().mousePressed(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.gameState) {
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        switch (GameStates.gameState) {
            case MENU:
            System.out.println(e);
            case SETTINGS:
                break;
            case PLAYING:
                System.out.println(e);
        }
    }

}
