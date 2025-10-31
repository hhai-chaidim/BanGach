package MAIN;

import GAMESTATE.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean leftPressed, rightPressed, spacePressed, pButtonPressed;
    public boolean highPressed, lowPressed;
    public boolean pauseState;
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
                pauseState = true;
            } else if (gp.gameState.equals(GameState.PAUSE)) {
                gp.gameState = GameState.PLAYING;
                pauseState = false;
            }
        }
        if (gp.gameState == GameState.PAUSE || gp.gameState == GameState.GAME_OVER) {
            pauseState = true;
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                gp.playSE(3);
                gp.pause.selectUpButton();
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                gp.playSE(3);
                gp.pause.selectDownButton();
            }
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                gp.playSE(3);
                gp.pause.selectLeftButton();
            }
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                gp.playSE(3);
                gp.pause.selectRightButton();
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gp.pause.activateSelectedButton();
                gp.playSE(1);
            }
            if (code == KeyEvent.VK_H) {
                gp.playSE(4);
                highPressed = true;
                gp.increaseMusicVolume();
                gp.increaseSoundEffectVolume();
            }
            if (code == KeyEvent.VK_L) {
                gp.playSE(4);
                lowPressed = true;
                gp.decreaseMusicVolume();
                gp.decreaseSoundEffectVolume();
            }
        } else if (gp.gameState == GameState.MENU) {
            pauseState = false;
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                gp.playSE(3);
                gp.menu.selectUpButton();
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                gp.playSE(3);
                gp.menu.selectDownButton();
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gp.menu.activateSelectedButton();
                gp.playSE(1);
            }
        } else if (gp.gameState == GameState.LEVEL) {
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                gp.playSE(3);
                gp.level.selectUpButton();
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                gp.playSE(3);
                gp.level.selectDownButton();
            }
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                gp.playSE(3);
                gp.level.selectLeftButton();
            }
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                gp.playSE(3);
                gp.level.selectRightButton();
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gp.level.activateSelectedButton();
                gp.playSE(1);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                if (pauseState) {
                    gp.gameState = GameState.PAUSE;
                } else {
                    gp.gameState = GameState.MENU;
                    pauseState = false;
                }
            }
        } else if (gp.gameState == GameState.INFOR) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = GameState.PAUSE;
                pauseState = true;
            }
        } else if (gp.gameState == GameState.SCORE) {
            if (code == KeyEvent.VK_ESCAPE) {
                if (pauseState) {
                    gp.gameState = GameState.PAUSE;
                } else {
                    gp.gameState = GameState.MENU;
                    pauseState = false;
                }
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
