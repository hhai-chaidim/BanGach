package OBJECTS;

import ENTITY.Brick;
import ENTITY.Player;
import MAIN.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class OBJ_Item extends SuperObject{

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

    public int type;


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

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        if (!objectActived) return;

        switch (type) {
            case TYPE_EXTRA_BALL:
                g2.setColor(Color.YELLOW);
                break;
            case TYPE_PLAYER_SIZE:
                g2.setColor(Color.BLUE);
                break;
            case TYPE_EXTRA_LIFE:
                g2.setColor(Color.RED);
                break;
            case TYPE_SLOW_BALL:
                g2.setColor(Color.GREEN);
                break;
        }
        g2.fillOval(objectX, objectY, diameter, diameter);
    }
}
