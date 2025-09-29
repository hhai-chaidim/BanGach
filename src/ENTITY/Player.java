package ENTITY;

import MAIN.GamePanel;
import MAIN.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player {
    public int playerX;
    public int playerY;
    public int width;
    public int height;
    public int speed;
    public int playerLives;

    GamePanel gp;
    KeyHandler keyHandler;

    public BufferedImage[] stableFrames;
    public BufferedImage[] turnLeftFrames;
    public BufferedImage[] turnRightFrames;

    public String currentState = "stable";
    public int spriteCounter = 0;
    public int currentFrame = 0;
    public int totalFrames = 4;


    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
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
        this.height = 32;
        this.speed = 4;
        this.playerLives = 3;
    }

    public void getPlayerImage() {
        try {
            BufferedImage stableSpriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stable.png")));
            BufferedImage turnLeftSpriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/TurnLeft.png")));
            BufferedImage turnRightSpriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/TurnRight.png")));

            int frameWidth = stableSpriteSheet.getWidth();
            int frameHeight = stableSpriteSheet.getHeight() / totalFrames;

            stableFrames = new BufferedImage[totalFrames];
            turnLeftFrames = new BufferedImage[totalFrames];
            turnRightFrames = new BufferedImage[totalFrames];

            // Extract frames from each sprite sheet
            for (int i = 0; i < totalFrames; i++) {
                stableFrames[i] = stableSpriteSheet.getSubimage(0, i * frameHeight, frameWidth, frameHeight);
                turnLeftFrames[i] = turnLeftSpriteSheet.getSubimage(0, i * frameHeight, frameWidth, frameHeight);
                turnRightFrames[i] = turnRightSpriteSheet.getSubimage(0, i * frameHeight, frameWidth, frameHeight);

        }
            System.out.println("Player images loaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not load player images!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing sprite sheets!");
        }
    }

    public void update() {
        if (keyHandler.leftPressed == true) {
            playerX -= speed;
            currentState = "left";
        } else if (keyHandler.rightPressed == true) {
            playerX += speed;
            currentState = "right";
        } else {
            currentState = "stable";
        }
        if (playerX < 0) {
            playerX = 0;
        }
        if (playerX > 576 - width) {
            playerX = 576 - width;
        }

        spriteCounter++;
        if (spriteCounter > 8) {
            currentFrame++;
            if (currentFrame >= totalFrames) {
                currentFrame = 0;
            }
            spriteCounter = 0;
        }
    }

    public void enlargePlayer() {
        int originalWidth = this.width;
        this.width += 50; // Tăng thêm 50px

        System.out.println("Player to ra!");

        new Thread(() -> {
            try {
                Thread.sleep(10000);
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
        BufferedImage image = null;

        if (stableFrames != null && turnLeftFrames != null && turnRightFrames != null) {
            switch (currentState) {
                case "stable":
                    image = stableFrames[currentFrame];
                    break;
                case "left":
                    image = turnLeftFrames[currentFrame];
                    break;
                case "right":
                    image = turnRightFrames[currentFrame];
                    break;
            }
        }

        if (image != null) {
            g2.drawImage(image, playerX, playerY, width, height, null);
        } else {
            g2.setColor(Color.black);
            g2.fillRect(playerX, playerY, width, height);
        }
    }
}
