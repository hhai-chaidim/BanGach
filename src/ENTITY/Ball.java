package ENTITY;

import GAMESTATE.GameState;
import MAIN.GamePanel;
import OBJECTS.OBJ_Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.max;

public class Ball {
    GamePanel gp;
    Player player;
    ArrayList<Brick> bricks;

    int ballX;
    int ballY;
    public int diameter = 20;
    int speedX;
    int speedY;

    private int originalSpeedX = 4;
    private int originalSpeedY = -4;
    private boolean isSlowed = false;


    public boolean ballActived = false;
    private static BufferedImage image;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    static {
        try {
            image = ImageIO.read(Objects.requireNonNull(OBJ_Item.class.getResourceAsStream("/Object/Player.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Ball(GamePanel gp, Player player, ArrayList<Brick> bricks) {
        this.gp = gp;
        this.player = player;
        this.bricks = bricks;
        resetBall();
    }

    public void resetBall() {
        ballActived = false;
        ballX = player.playerX + player.width / 2 - diameter / 2;
        ballY = player.playerY - diameter;
        speedX = 4;
        speedY = -4;
    }

    public void update() {
        if (ballActived == false) {
            ballX = player.playerX + player.width / 2 - diameter / 2;
            ballY = player.playerY - diameter / 2;
        } else {
            ballX += speedX;
            ballY += speedY;
        }
        if (
                ballX + diameter > player.playerX &&
                        ballX < player.playerX + player.width &&
                        ballY + diameter > player.playerY &&
                        ballY < player.playerY + player.height
        ) {
            if (ballActived) {
                gp.playSE(0);
            }
            speedY = -Math.abs(speedY);

            int playerCenter = player.playerX + player.width / 2;
            int ballCenter = ballX + diameter / 2;

            if (ballCenter < playerCenter) {
                speedX = -Math.abs(speedX);
            } else {
                speedX = Math.abs(speedX);
            }
            originalSpeedX = speedX;
            originalSpeedY = speedY;
        }
        if (ballX <= 0 || ballX >= gp.screenWidth - diameter) {
            gp.playSE(0);
            speedX = -speedX;
            originalSpeedX = -originalSpeedX;
        }
        if (ballY <= 0) {
            gp.playSE(0);
            speedY = -speedY;
            originalSpeedY = -originalSpeedY;
        }
        if (ballY >= gp.screenHeight - diameter) {
            long activeBalls = gp.balls.stream().filter(b -> b.ballActived).count();

            if (activeBalls <= 1) {
                player.playerLives--;
                if (player.playerLives <= 0) {
                    System.out.println("Game over!");
                    gp.setGameState(GameState.GAME_OVER);
                } else {
                    System.out.println("Resetting ball...");
                    resetBall();
                }
            } else {
                System.out.println("Deactivating ball...");
                ballActived = false;
                gp.balls.removeIf(b -> !b.ballActived);
            }
        }

        ArrayList<Brick> bricksCopy = new ArrayList<>(bricks);
        for (Brick brick : bricksCopy) {
            if (!brick.isVisible()) continue;

            Rectangle ballRect = new Rectangle(ballX, ballY, diameter, diameter);
            Rectangle brickRect = brick.getBounds();

            if (ballRect.intersects(brickRect)) {
                gp.playSE(0);

                int ballCenterX = ballX + diameter / 2;
                int ballCenterY = ballY + diameter / 2;

                boolean hitFromLeft = ballCenterX < brick.x;
                boolean hitFromRight = ballCenterX > brick.x + brick.width;
                boolean hitFromTop = ballCenterY < brick.y;
                boolean hitFromBottom = ballCenterY > brick.y + brick.height;

                if (hitFromTop || hitFromBottom) {
                    speedY = -speedY;
                    originalSpeedY = -originalSpeedY;
                } else if (hitFromLeft || hitFromRight) {
                    speedX = -speedX;
                    originalSpeedX = -originalSpeedX;
                } else {
                    speedX = -speedX;
                    speedY = -speedY;
                    originalSpeedX = -originalSpeedX;
                    originalSpeedY = -originalSpeedY;
                }
                brick.setVisible(false);
                break;
            }
        }
    }

    public void activeBall() {
        if (!ballActived) {
            ballActived = true;
        }
    }

    public void addExtraBall(Ball currentBall) {

        Ball newBall = new Ball(gp, player, bricks);
        newBall.ballX = currentBall.ballX;
        newBall.ballY = currentBall.ballY;
        newBall.ballActived = true;

        newBall.speedX = -currentBall.speedX;
        newBall.speedY = currentBall.speedY;
        newBall.originalSpeedX = newBall.speedX;
        newBall.originalSpeedY = newBall.speedY;

        gp.balls.add(newBall);

        System.out.println("Spawn thêm 1 bóng! Tổng bóng hiện tại: " + gp.balls.size());
    }

    public void slowDownBall() {
        if (isSlowed) return;

        isSlowed = true;
        speedX = speedX / 2;
        speedY = speedY / 2;

        Thread restoreThread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSlowed) {
                int normalSpeed = 4;

                speedX = (speedX > 0) ? normalSpeed : -normalSpeed;
                speedY = (speedY > 0) ? normalSpeed : -normalSpeed;

                originalSpeedX = speedX;
                originalSpeedY = speedY;

                isSlowed = false;
                System.out.println("Ball trở lại bình thường!");
            }
        });

        restoreThread.setDaemon(true);
        restoreThread.start();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, ballX - 7, ballY, diameter + 7, diameter + 7, null);
    }

    public static void shutdown() {
        scheduler.shutdown();
    }

}
