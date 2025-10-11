package MAIN;

import ENTITY.Ball;
import ENTITY.Brick;
import ENTITY.Player;
import GAMESTATE.LevelState;
import GAMESTATE.MenuState;
import GAMESTATE.PauseState;
import OBJECTS.OBJ_Heart;
import OBJECTS.OBJ_Item;
import TILE.TileManager;
import GAMESTATE.GameState;

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
    public PauseState pause = new PauseState(this);
    public MenuState menu = new MenuState(this);
    public LevelState level = new LevelState(this);

    int FPS = 60;

    public GameState gameState = GameState.PLAYING;
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound sound = new Sound();

    Thread gameThread = new Thread(this);
    Player player = new Player(this, keyHandler);
    ArrayList<Brick> bricks = Brick.createBricks();
    ArrayList<OBJ_Item> items = new ArrayList<>();
    public ArrayList<Ball> balls = new ArrayList<>();

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
        Ball initialBall = new Ball(this, player, bricks);
        balls.add(initialBall);
    }

    public void startGameThread() {
        gameState = GameState.MENU;
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
        switch (gameState){
            case PLAYING:
            player.update();
                ArrayList<Ball> ballsCopy = new ArrayList<>(balls);
                ArrayList<Ball> toRemove = new ArrayList<>();

                for (Ball ball : ballsCopy) {
                    ball.update();
                    if (keyHandler.spacePressed && !ball.ballActived) {
                        ball.activeBall();
                    }
                    if (!ball.ballActived && ball.ballY >= screenHeight - ball.diameter) {
                        toRemove.add(ball);
                    }
                }
                balls.removeAll(toRemove);

            if (balls.isEmpty()) {
                Ball newBall = new Ball(this, player, bricks);
                balls.add(newBall);
            }

            updateBricks();
            updateItems();
            break;
            case PAUSE:
                break;
            case MENU:
                break;
            case GAME_OVER:
                break;
            default:
                break;
        }
    }

    private void updateItems() {
        Iterator<OBJ_Item> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            OBJ_Item item = itemIterator.next();
            item.update();

            Rectangle playerRect = new Rectangle(player.playerX, player.playerY, player.width, player.height);
            Rectangle itemRect = new Rectangle(item.objectX, item.objectY, item.diameter, item.diameter);

            if (playerRect.intersects(itemRect)) {
                if (!item.objectColission) {
                    activateItemEffect(item);
                }
                itemIterator.remove();
            }
            else if (item.objectY > screenHeight) {
                itemIterator.remove();
            }
        }
    }

    public void gameQuit() {
        System.exit(0);
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        switch (gameState){
            case PLAYING:
                tileManager.draw(g2);

                player.draw(g2);
                for(Ball ball : balls) {
                    ball.draw(g2);
                }
                for(Brick brick : bricks) {
                    brick.draw(g2);
                }
                for (OBJ_Item item : items) {
                    item.draw(g2);
                }

                OBJ_Heart.drawHearts(g2);
                break;
                case PAUSE:
                    pause.draw(g2);
                break;
                case MENU:
                    menu.draw(g2);
                break;
                case GAME_OVER:
                    pause.draw(g2);
                break;
                case LEVEL:
                level.draw(g2);
                default:
                break;

        }
    }

    public void updateBricks() {
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            if (!brick.isVisible()) {
                if (Math.random() < 0.9) {
                    int itemX = brick.x + brick.width / 2;
                    int itemY = brick.y + brick.height / 2;

                    int randomType = (int)(Math.random() * 5);
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
                playSE(2);
                if (!balls.isEmpty()) {
                    Ball sourceBall = balls.get(0);
                    sourceBall.addExtraBall(sourceBall);
                }
                break;

            case OBJ_Item.TYPE_PLAYER_SIZE:
                playSE(2);
                player.enlargePlayer();
                break;

            case OBJ_Item.TYPE_EXTRA_LIFE:
                playSE(2);
                player.addExtraLife();
                break;

            case OBJ_Item.TYPE_SLOW_BALL:
                playSE(2);
                OBJ_Item.setObjectColission(item, true);
                for(Ball ball : balls) {
                    ball.slowDownBall();
                }
                break;
            case OBJ_Item.TYPE_BOMB:
                playSE(5);
                player.bomb();
                break;

            default:
                System.out.println("Item không xác định!");
                break;
        }
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

        public void restart() {
        gameState = GameState.PLAYING;
        balls.clear();
        bricks.clear();
        items.clear();
        player.playerLives = 3;
        bricks = Brick.createBricks();
        Ball initialBall = new Ball(this, player, bricks);
        balls.add(initialBall);
        keyHandler.spacePressed = false;
        repaint();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        for(int i = 0; i <= 4; i++){
            sound.setFile(i);
            sound.stop();
        }
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public void setLevel(int i) {
        Brick.brickLevel = i;
        bricks.clear();
        bricks = Brick.createBricks();
        for(Ball ball : balls) {
            ball.bricks = bricks;
        }
    }
}
