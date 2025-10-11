package GAMESTATE;

import ENTITY.Brick;
import MAIN.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LevelState {
    static GamePanel gp;
    static BufferedImage[] buttonIcons;
    static BufferedImage image;

    private static int selectedButton = 0;
    private static Rectangle[] buttonBounds;
    private static int totalButtons = 14;

    public LevelState(GamePanel gp) {
        this.gp = gp;
        loadImages();
        initializeButtons();
    }

    private void loadImages() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Background/test.jpg")));
            buttonIcons = new BufferedImage[totalButtons];
            for (int i = 0; i < totalButtons; i++) {
                buttonIcons[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Level/" + (i + 1) +".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeButtons() {
        buttonBounds = new Rectangle[totalButtons];

        int buttonWidth = 192;
        int buttonHeight = 48;
        int startX = 48;
        int startY = 48;
        int spacingX = 96;
        int spacingY = 48;

        for (int i = 0; i < totalButtons; i++) {
            int row = i / 2;
            int col = i % 2;
            int x = startX + col * (buttonWidth + spacingX);
            int y = startY + row * (buttonHeight + spacingY);
            buttonBounds[i] = new  Rectangle(x, y, buttonWidth, buttonHeight);
        }

    }
    public void selectLeftButton() {
        if (selectedButton % 2 == 1) {
            selectedButton--;
        }
    }

    public void selectRightButton() {
        if (selectedButton % 2 == 0 && selectedButton + 1 < totalButtons) {
            selectedButton++;
        }
    }

    public void selectUpButton() {
        if (selectedButton - 2 >= 0) {
            selectedButton -= 2;
        }
    }

    public void selectDownButton() {
        if (selectedButton + 2 < totalButtons) {
            selectedButton += 2;
        }
    }

    private static void drawButtonHighlight(Graphics2D g2) {
        Rectangle bounds = buttonBounds[selectedButton];
        g2.setColor(new Color(255, 255, 0, 150));
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(bounds.x - 5, bounds.y - 5, bounds.width + 10, bounds.height + 10);
        g2.setColor(new Color(255, 255, 255, 50));
        g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    private static void drawButtons(Graphics2D g2) {

        int buttonWidth = 192;
        int buttonHeight = 48;
        int startX = 48;
        int startY = 48;
        int spacingX = 96;
        int spacingY = 48;

        for (int i = 0; i < totalButtons; i++) {
            int row = i / 2;
            int col = i % 2;
            int x = startX + col * (buttonWidth + spacingX);
            int y = startY + row * (buttonHeight + spacingY);
            g2.drawImage(buttonIcons[i], x, y, buttonWidth, buttonHeight, null);
        }
    }

    public void activateSelectedButton() {
        switch (selectedButton) {
            case 0:
                gp.setLevel(1);
                System.out.println("set level 1");
                break;
            case 1:
                gp.setLevel(2);
                System.out.println("set level 2");
                break;
            case 2:
                gp.setLevel(3);
                break;
            case 3:
                gp.setLevel(4);
                break;
            case 4:
                gp.setLevel(5);
                break;
            case 5:
                gp.setLevel(6);
                break;
            case 6:
                gp.setLevel(7);
                break;
            case 7:
                gp.setLevel(8);
                break;
            case 8:
                gp.setLevel(9);
                break;
            case 9:
                gp.setLevel(10);
                break;
            case 10:
                gp.setLevel(11);
                break;
            case 11:
                gp.setLevel(12);
                break;
            case 12:
                gp.setLevel(13);
                break;
            case 13:
                gp.setLevel(14);
                break;
            case 14:
                gp.gameState = GameState.MENU;
                break;

        }
    }
    public static void draw(Graphics2D g2) {
        g2.drawImage(image, 0, 0, gp.maxScreenCol * gp.tileSize, gp.maxScreenRow * gp.tileSize, null);
        drawButtons(g2);
        drawButtonHighlight(g2);
    }
}
