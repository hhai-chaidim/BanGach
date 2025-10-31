package GAMESTATE;

import MAIN.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MenuState {
    static GamePanel gp;
    static BufferedImage[] buttonIcons;
    static BufferedImage[] highlightImage;
    static BufferedImage image;
    static BufferedImage gameName;

    private static int selectedButton = 0;
    private static Rectangle[] buttonBounds;
    private static int totalButtons = 4;

    public MenuState(GamePanel gp) {
        this.gp = gp;
        loadImages();
        initializeButtons();
    }

    private void loadImages() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Background/test.jpg")));
            gameName = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Setting menu/Arkanoid.png")));

            buttonIcons = new BufferedImage[4];
            buttonIcons[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Setting menu/Start 1.png")));
            buttonIcons[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Setting menu/Score 1.png")));
            buttonIcons[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Setting menu/Setting 1.png")));
            buttonIcons[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Setting menu/Quit 1.png")));

            highlightImage = new BufferedImage[4];
            highlightImage[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Setting menu/Start 2.png")));
            highlightImage[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Setting menu/Score 2.png")));
            highlightImage[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Setting menu/Setting 2.png")));
            highlightImage[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Setting menu/Quit 2.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void draw(Graphics2D g2) {
        g2.drawImage(image, 0, 0, gp.maxScreenCol * gp.tileSize, gp.maxScreenRow * gp.tileSize, null);
        g2.drawImage(gameName, 57 * gp.tileSize / 20, gp.tileSize / 2, 333, 97, null);
        drawButtons(g2);
        drawButtonHighlight(g2);
    }

    public static void drawButtons(Graphics2D g2) {
        int width = 300;
        int height = 108;

        int startX = 3 * gp.tileSize;
        int startY = 3 * gp.tileSize;
        int spaceY = 32;

        for (int i = 0; i < totalButtons; i++) {
            int x = startX;
            int y = startY + i * (height + spaceY);
            if(selectedButton == i) {
                g2.drawImage(highlightImage[i], x, y, width, height, null);
            } else {
                g2.drawImage(buttonIcons[i], x, y, width, height, null);
            }
        }
    }

    public void selectUpButton() {
        selectedButton--;
        if (selectedButton < 0) {
            selectedButton = totalButtons - 1;
        }
    }

    public void selectDownButton() {
        selectedButton++;
        if (selectedButton >= totalButtons) {
            selectedButton = 0;
        }
    }

    private static void drawButtonHighlight(Graphics2D g2) {
        Rectangle bounds = buttonBounds[selectedButton];
        g2.drawImage(highlightImage[selectedButton], bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    private void initializeButtons() {
        buttonBounds = new Rectangle[totalButtons];

        int startX = 3 * gp.tileSize;
        int startY = 3 * gp.tileSize;
        int spaceY = 32;

        int width = 300;
        int height = 108;
        for (int i = 0; i < totalButtons; i++){
            int x = startX;
            int y = startY + i * (height + spaceY);
            buttonBounds[i] = new  Rectangle(x, y, width, height);
        }
    }

    public void activateSelectedButton() {
        switch (selectedButton) {
            case 0: // Start
                gp.gameState = GameState.PLAYING;
                break;
            case 1: // Score
                gp.gameState = GameState.SCORE;
                break;
            case 2: // Setting
                gp.gameState = GameState.LEVEL;
                break;
            case 3: // Quit
                gp.gameQuit();
                break;

        }
    }
}
