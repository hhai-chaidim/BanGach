package ENTITY;

import MAIN.GamePanel;
import MAIN.KeyHandler;

import java.awt.*;

public class Player {
    public int playerX;
    public int playerY;
    public int width;
    public int height;
    public int speed;
    public int playerLives;

    GamePanel gp;
    KeyHandler keyHandler;


    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDefaultValues() {
        this.playerX = 100;
        this.playerY = 600;
        this.width = 64;
        this.height = 8;
        this.speed = 4;
        this.playerLives = 3;
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

    public void enlargePlayer() {
        int originalWidth = this.width;
        this.width += 50; // Tăng thêm 50px

        System.out.println("Player to ra!");

        // Sau 10 giây quay về kích thước ban đầu
        new Thread(() -> {
            try {
                Thread.sleep(10000); // 10 giây
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.width = originalWidth;
            System.out.println("Player trở lại bình thường!");
        }).start();
    }

    public void addExtraLife() {
        this.playerLives += 1;
        System.out.println("Thêm 1 mạng sống. Tổng mạng: " + this.playerLives);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);

        g2.fillRect(playerX, playerY, width, height);
    }
}
