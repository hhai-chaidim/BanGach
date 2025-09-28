package ENTITY;

import MAIN.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Brick {
    public int x, y;
    public int width;
    public int height;
    public boolean visible;
    private Color color;
    private int health;

    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visible = true;
        this.health = 1;
    }

    public void draw(Graphics2D g2) {
        if (!visible) return;

        g2.setColor(color);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static ArrayList<Brick> createBricks() {
        ArrayList<Brick> brickList = new ArrayList<>();

        int rows = 5;
        int cols = 8;
        int brickWidth = 60;
        int brickHeight = 20;
        int startX = 30;
        int startY = 50;
        int spacing = 5;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = startX + col * (brickWidth + spacing);
                int y = startY + row * (brickHeight + spacing);

                Color color;
                switch (row) {
                    case 0: color = Color.RED; break;
                    case 1: color = Color.ORANGE; break;
                    case 2: color = Color.YELLOW; break;
                    case 3: color = Color.GREEN; break;
                    default: color = Color.CYAN; break;
                }

                brickList.add(new Brick(x, y, brickWidth, brickHeight, color));
            }
        }

        return brickList;
    }


    public void update() {

    }
}

