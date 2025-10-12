package ENTITY;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

import static GAMESTATE.MapLevel.LEVEL_MAPS;

public class Brick {
    public int x, y;
    public int width;
    public int height;
    public boolean visible;
    private int imageIndex; // index để chọn ảnh gạch
    public int bricksLife;
    public static int brickLevel = 7;

    private static BufferedImage[] brickImages = new BufferedImage[8];

    static {
        try {
            for (int i = 0; i < 8; i++) {
                brickImages[i] = ImageIO.read(new File("res/Brick/Brick" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Brick(int x, int y, int width, int height, int imageIndex, int life) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = true;
        this.bricksLife = 2;
        this.imageIndex = imageIndex % brickImages.length;
    }

    public void draw(Graphics2D g2) {
        if (!visible) return;

        BufferedImage img = brickImages[imageIndex];
        if (img != null && bricksLife == 2) {
            g2.drawImage(img, x, y, width, height, null);
        } else if (bricksLife < 2){
            g2.setColor(Color.GRAY);
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, width, height);
        }
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

    public void setBrickLevel(int i) {
        brickLevel = i;
    }

    public static ArrayList<Brick> createBricks() {
        ArrayList<Brick> brickList = new ArrayList<>();

        if (brickLevel < 1 || brickLevel > LEVEL_MAPS.length) {
            brickLevel = 1; // Default to level 1
        }

        int[][] currentMap = LEVEL_MAPS[brickLevel - 1];

        int brickWidth = 60;
        int brickHeight = 20;
        int startX = 30;
        int startY = 50;
        int spacing = 5;

        for (int row = 0; row < currentMap.length; row++) {
            for (int col = 0; col < currentMap[row].length; col++) {
                int brickType = currentMap[row][col];

                // 0 means no brick
                if (brickType == 0) continue;

                int x = startX + col * (brickWidth + spacing);
                int y = startY + row * (brickHeight + spacing);

                // 9 means indestructible brick (life = 999)
                int life = (brickType == 9) ? 999 : 2;
                int imageIndex = (brickType == 9) ? 0 : brickType - 1;

                brickList.add(new Brick(x, y, brickWidth, brickHeight, imageIndex, life));
            }
        }

        return brickList;

    }

    public void update() {
    }
}
