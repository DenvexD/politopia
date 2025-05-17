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
        System.out.println(Game.getPlay());
	}

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.listenerReadyGameState) {
            case MENU:
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mouseDragged(e.getX(), e.getY());
                break;
            default:
                break;

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.listenerReadyGameState) {
            case MENU:
                Game.getMenu().mouseMoved(e.getX(), e.getY());
                break;
            case SETTINGS:
                break;
            case PLAYING:
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameStates.listenerReadyGameState) {
            case MENU:
                Game.getMenu().mouseClicked(e.getX(), e.getY());
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mouseClicked(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.listenerReadyGameState) {
            case MENU:
                Game.getMenu().mousePressed(e.getX(), e.getY());
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mousePressed(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.listenerReadyGameState) {
            case MENU:
                Game.getMenu().mouseReleased(e.getX(), e.getY());
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mouseReleased(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        switch (GameStates.listenerReadyGameState) {
            case MENU:
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().setMouseExited(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        switch (GameStates.listenerReadyGameState) {
            case MENU:
                break;
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().setMouseExited(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        switch (GameStates.listenerReadyGameState) {
            case MENU:
            System.out.println(e);
            case SETTINGS:
                break;
            case PLAYING:
                Game.getPlay().mouseWheelMoved(e.getPreciseWheelRotation(), e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

}
