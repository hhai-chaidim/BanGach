package GAMESTATE;

import MAIN.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class InforState {
    GamePanel gp;
    BufferedImage image;

    public InforState(GamePanel gp) {
        this.gp = gp;
        loadImages();
    }

    public void loadImages() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Background/test.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, 0, 0, gp.maxScreenCol * gp.tileSize, gp.maxScreenRow * gp.tileSize, null);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.BLACK);
        g2.drawString("Press Space to start", 100, 100);
        g2.drawString("Press A to turn left", 100, 200);
        g2.drawString("Press D to turn right", 100, 300);
        g2.drawString("Press P to pause", 100, 400);
        g2.drawString("Press ←|↑|↓|→ to change option", 100, 500);
        g2.drawString("Press ENTER to choose", 100, 600);
        g2.drawString("Press ESC to return", 60, 740);
    }
}
