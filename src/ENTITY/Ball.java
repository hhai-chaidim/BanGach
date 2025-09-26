package ENTITY;

import MAIN.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class Ball {
    GamePanel gp;
    Player player;
    ArrayList<Brick> bricks;

    int ballX;
    int ballY;
    public int diameter = 20;
    int speedX;
    int speedY;

    public boolean ballActived = false;

    public Ball(GamePanel gp, Player player, ArrayList<Brick> bricks) {
        this.gp = gp;
        this.player = player;
        this.bricks = bricks;
        resetBall();
    }

    public void setSpeed(int x) {
        speedX = x;
        speedY = x;
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
            player.playerLives--;

            if (player.playerLives <= 0) {
                speedX = 0;
                speedY = 0;
            } else {
                resetBall();
            }
        }

        for (Brick brick : bricks) {
            Rectangle ballRect = new Rectangle(ballX, ballY, diameter, diameter);
            Rectangle brickRect = brick.getBounds();

            if (ballRect.intersects(brickRect)) {
                int ballCenterX = ballX + diameter / 2;
                int ballCenterY = ballY + diameter / 2;

                boolean hitFromLeft = ballCenterX < brick.x;
                boolean hitFromRight = ballCenterX > brick.x + brick.width;
                boolean hitFromTop = ballCenterY < brick.y;
                boolean hitFromBottom = ballCenterY > brick.y + brick.height;

                if (hitFromTop || hitFromBottom) {
                    speedY = -speedY;
                } else if (hitFromLeft || hitFromRight) {
                    speedX = -speedX;
                } else {
                    speedX = -speedX;
                    speedY = -speedY;
                }
                brick.setVisible(false);
            }
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
