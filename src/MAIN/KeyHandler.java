package MAIN;

import GAMESTATE.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean leftPressed, rightPressed, spacePressed, pButtonPressed;
    public boolean highPressed, lowPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (code == KeyEvent.VK_P) {
            if(gp.gameState.equals(GameState.PLAYING)) {
                gp.gameState = GameState.PAUSE;
            } else if (gp.gameState.equals(GameState.PAUSE)) {
                gp.gameState = GameState.PLAYING;
            }
        }
        if (gp.gameState == GameState.PAUSE || gp.gameState == GameState.GAME_OVER) {
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                gp.ui.selectUpButton();
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                gp.ui.selectDownButton();
            }
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                gp.ui.selectLeftButton();
            }
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                gp.ui.selectRightButton();
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gp.ui.activateSelectedButton();
            }
            if (code == KeyEvent.VK_H) {
                highPressed = true;
            }
            if (code == KeyEvent.VK_L) {
                lowPressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (code == KeyEvent.VK_P) {
            pButtonPressed = false;
        }
        if (code == KeyEvent.VK_H) {
            highPressed = false;
        }
        if (code == KeyEvent.VK_L) {
            lowPressed = false;
        }
    }
}
