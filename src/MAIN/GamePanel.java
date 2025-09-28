package MAIN;

import ENTITY.Ball;
import ENTITY.Brick;
import ENTITY.Player;
import OBJECTS.OBJ_Heart;
import OBJECTS.OBJ_Item;
import OBJECTS.SuperObject;
import TILE.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize  = originalTileSize * scale;
    public final int maxScreenCol = 12;
    public final int maxScreenRow = 16;
    public final int screenWidth  = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;
    Player player = new Player(this, keyHandler);
    ArrayList<Brick> bricks = Brick.createBricks();
    ArrayList<OBJ_Item> items = new ArrayList<>();
    Ball ball = new Ball(this, player, bricks);
    OBJ_Heart objHeart = new OBJ_Heart(this, player);
    public AssetSetter assetSetter = new AssetSetter(this);


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.pink);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

        }
    }
    public void update() {
        player.update();
        ball.update();

        if (keyHandler.spacePressed && !ball.ballActived) {
            ball.activeBall();
        }
        updateBricks();
        for (int i = 0; i < items.size(); i++) {
            OBJ_Item item = items.get(i);
            item.update();

            Rectangle playerRect = new Rectangle(player.playerX, player.playerY, player.width, player.height);
            Rectangle itemRect = new Rectangle(item.objectX, item.objectY, item.diameter, item.diameter);

            if (playerRect.intersects(itemRect)) {
                if (item.objectColission == true) {
                    return;
                } else {
                    activateItemEffect(item);
                }
                items.remove(i);
                i--;
            }
        }


    }

    public void gameQuit() {
        System.exit(0);
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        player.draw(g2);
        ball.draw(g2);
        for(Brick brick : bricks) {
            brick.draw(g2);
        }
        for (OBJ_Item item : items) {
            item.draw(g2, this);
        }

        OBJ_Heart.drawHearts(g2);
        g2.dispose();
    }

    public void updateBricks() {
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            if (!brick.isVisible()) {
                if (Math.random() < 0.4) {
                    int itemX = brick.x + brick.width / 2;
                    int itemY = brick.y + brick.height / 2;

                    int randomType = (int)(Math.random() * 4);
                    OBJ_Item newItem = new OBJ_Item(this, player, items, itemX, itemY, randomType);
                    items.add(newItem);
                }
                iterator.remove();
            }
        }
    }

    public void activateItemEffect(OBJ_Item item) {
        switch (item.type) {
            case OBJ_Item.TYPE_EXTRA_BALL:
                ball.addExtraBall();
                break;

            case OBJ_Item.TYPE_PLAYER_SIZE:
                player.enlargePlayer();
                break;

            case OBJ_Item.TYPE_EXTRA_LIFE:
                player.addExtraLife();
                break;

            case OBJ_Item.TYPE_SLOW_BALL:
                OBJ_Item.setObjectColission(item, true);
                ball.slowDownBall();
                break;

            default:
                System.out.println("Item không xác định!");
                break;
        }
    }
}
