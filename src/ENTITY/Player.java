package ENTITY;

import MAIN.GamePanel;
import MAIN.KeyHandler;

import java.awt.*;

public class Player {
    int playerX;
    int playerY;
    int width;
    int height;
    int speed;

    GamePanel gp;
    KeyHandler keyHandler;


    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
    }

    public void setDefaultValues() {
        this.playerX = 100;
        this.playerY = 600;
        this.width = 64;
        this.height = 8;
        this.speed = 4;
    }

    public void update() {
        if (keyHandler.leftPressed == true) {
            playerX -= speed;
        }
        if (keyHandler.rightPressed == true) {
            playerX += speed;
        }
        if (playerX < 0) {
            playerX = 0;
        }
        if (playerX > 576 - width) {
            playerX = 576 - width;
        }
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);

        g2.fillRect(playerX, playerY, width, height);
    }
}
