package OBJECTS;

import ENTITY.Brick;
import ENTITY.Player;
import MAIN.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class OBJ_Item{

    GamePanel gp;
    Player player;
    ArrayList<Brick> bricks;
    ArrayList<OBJ_Item> items;

    public int objectX;
    public int objectY;
    public int diameter = 20;
    int speedY = 2;

    public boolean objectActived = true;
    public boolean objectColission = false;

    public static final int TYPE_EXTRA_BALL = 0;
    public static final int TYPE_PLAYER_SIZE = 1;
    public static final int TYPE_EXTRA_LIFE = 2;
    public static final int TYPE_SLOW_BALL = 3;
    public static final int TYPE_BOMB = 4;

    private static BufferedImage imageExtraBall;
    private static BufferedImage imagePlayerSize;
    private static BufferedImage imageExtraLife;
    private static BufferedImage imageSlowBall;
    private static BufferedImage imageBomb;

    public int type;

    static {
        try {
            imageExtraBall = ImageIO.read(Objects.requireNonNull(OBJ_Item.class.getResourceAsStream("/Object/+1.png")));
            imagePlayerSize = ImageIO.read(Objects.requireNonNull(OBJ_Item.class.getResourceAsStream("/Object/ExtraSize.png")));
            imageExtraLife = ImageIO.read(Objects.requireNonNull(OBJ_Item.class.getResourceAsStream("/Object/Healing.png")));
            imageSlowBall = ImageIO.read(Objects.requireNonNull(OBJ_Item.class.getResourceAsStream("/Object/Slow.png")));
            imageBomb = ImageIO.read(Objects.requireNonNull(OBJ_Item.class.getResourceAsStream("/Object/bomb.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OBJ_Item(GamePanel gp, Player player, ArrayList<OBJ_Item> items, int itemX, int itemY, int type) {
        this.gp = gp;
        this.player = player;
        this.items = items;
        this.objectX = itemX;
        this.objectY = itemY;
        this.type = type;
        this.objectActived = true;
        this.objectColission = false;
    }

    public static void setObjectColission(OBJ_Item item, boolean objectColission) {
        item.objectColission = objectColission;
    }

    public void update() {
        if (objectActived) {
            objectY += speedY;
            if (objectY > gp.screenHeight) {
                objectActived = false;
            }
        }
    }


    public void draw(Graphics2D g2) {
        if (!objectActived) return;

        BufferedImage img = null;
        switch (type) {
            case TYPE_EXTRA_BALL: img = imageExtraBall; break;
            case TYPE_PLAYER_SIZE: img = imagePlayerSize; break;
            case TYPE_EXTRA_LIFE: img = imageExtraLife; break;
            case TYPE_SLOW_BALL: img = imageSlowBall; break;
            case TYPE_BOMB: img = imageBomb; break;
        }

        if (img != null) {
            g2.drawImage(img, objectX, objectY, gp.tileSize, gp.tileSize, null);
        } else {
            // Fallback drawing if image is null
            g2.setColor(Color.YELLOW);
            g2.fillOval(objectX, objectY, diameter, diameter);
            g2.setColor(Color.BLACK);
            g2.drawOval(objectX, objectY, diameter, diameter);
        }
    }
}
