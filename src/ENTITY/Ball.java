package ENTITY;

import MAIN.GamePanel;

import java.awt.*;

public class Ball {
    GamePanel gp;
    Player player;

    int ballX;
    int ballY;
    public int diameter = 20;
    int speedX;
    int speedY;

    public boolean ballActived = false;

    public Ball(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
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
            ballY = player.playerY - diameter;
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

            speedY = -Math.abs(speedY);

            int playerCenter = player.playerX + player.width / 2;
            int ballCenter = ballX + diameter / 2;

            if (ballCenter < playerCenter) {
                speedX = -Math.abs(speedX);
            } else {
                speedX = Math.abs(speedX);
            }
        }
        if (ballX <= 0 || ballX >= gp.screenWidth - diameter) {
            speedX = -speedX;
        }
        if (ballY <= 0) {
            speedY = -speedY;
        }
        if (ballY >= gp.screenHeight - diameter) {
            System.out.println("Ball fell down! Resetting ball...");
            resetBall();
        }
    }

    public void activeBall() {
        if (!ballActived) {
            ballActived = true;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fillOval(ballX, ballY, diameter, diameter);
    }
}
