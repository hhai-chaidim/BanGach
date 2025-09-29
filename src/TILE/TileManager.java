package TILE;

import MAIN.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    Tile[] tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];

        getTileImage();
    }

    public void getTileImage() {
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Background/ahieu.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void draw(Graphics2D g2) {
        // Draw the image once, stretched to fit the entire screen
        g2.drawImage(tile[0].image, 0, 0, gp.screenWidth, gp.screenHeight, null);
    }
}
