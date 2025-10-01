package ENTITY;

import MAIN.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Brick {
    public int x, y;
    public int width;
    public int height;
    public boolean visible;
    private int imageIndex; // index để chọn ảnh gạch

    private static BufferedImage[] brickImages = new BufferedImage[8];

    static {
        try {
            for (int i = 0; i < 8; i++) {
                brickImages[i] = ImageIO.read(new File("res/brick/Brick" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Brick(int x, int y, int width, int height, int imageIndex) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = true;

        this.imageIndex = imageIndex % brickImages.length;
    }

    public void draw(Graphics2D g2) {
        if (!visible) return;

        BufferedImage img = brickImages[imageIndex];
        if (img != null) {
            g2.drawImage(img, x, y, width, height, null);
        } else {
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

                // Dùng ảnh theo cột (hoặc bạn đổi theo row cũng được)
                int imageIndex = col % 8;

                brickList.add(new Brick(x, y, brickWidth, brickHeight, imageIndex));
            }
        }
        return brickList;
    }

    public void update() {
    }
}
