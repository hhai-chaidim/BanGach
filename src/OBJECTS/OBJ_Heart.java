package OBJECTS;

import ENTITY.Player;
import MAIN.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{
    static GamePanel gp;
    static Player player;

    public OBJ_Heart(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        name = "Heart";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/Heart.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void drawHearts(Graphics2D g2) {
        int x = 10;
        int y = 10;
        int heartSize = gp.tileSize;

        for (int i = 0; i < player.playerLives; i++) {
            g2.drawImage(image, x, y, heartSize, heartSize, null);
            x += heartSize + 5;
        }
    }

}
