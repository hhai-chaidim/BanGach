package MAIN;

import ENTITY.Ball;
import ENTITY.Brick;
import ENTITY.Player;
import OBJECTS.OBJ_Heart;
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

        OBJ_Heart.drawHearts(g2);
        g2.dispose();
    }

    public void updateBricks() {
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            if (!brick.isVisible()) {
                iterator.remove();
            }
        }
    }

}
