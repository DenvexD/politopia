package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import main.GameStates;
import main.Game;



public class MyMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

    public MyMouseListener() {
        super();
	}

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mouseDragged(e.getX(), e.getY());

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                Game.getMenu().mouseMoved(e.getX(), e.getY());
                break;
            case SETTINGS:
                break;
            case PLAYING:
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                Game.getMenu().mouseClicked(e.getX(), e.getY());
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mouseClicked(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                Game.getMenu().mousePressed(e.getX(), e.getY());
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mousePressed(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                Game.getMenu().mouseReleased(e.getX(), e.getY());
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mouseReleased(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().setMouseExited(false);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().setMouseExited(true);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        switch (GameStates.gameState) {
            case MENU:
            System.out.println(e);
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mouseWheelMoved(e.getPreciseWheelRotation(), e.getX(), e.getY());
        }
    }

}
