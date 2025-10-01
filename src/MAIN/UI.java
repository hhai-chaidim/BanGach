package MAIN;

import GAMESTATE.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UI {
    GamePanel gp;
    BufferedImage pauseMenuImage;
    BufferedImage[] buttonIcons;
    BufferedImage image;
    BufferedImage[] volumeButton;

    private int selectedButton = 0;
    private int totalButtons = 6;
    private Rectangle[] buttonBounds;
    private int volumeLevel = 2;
    private boolean highPressedLastFrame = false;
    private boolean lowPressedLastFrame = false;

    private boolean isMuted = false;

    public UI(GamePanel gp) {
        this.gp = gp;
        loadImages();
        initializeButtons();
    }

    private void loadImages() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Background/test.jpg")));
            pauseMenuImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Large Rectangle.png")));
            buttonIcons = new BufferedImage[6];
            buttonIcons[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Square Replay.png")));
            buttonIcons[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Square Volume.png")));
            buttonIcons[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Square Home.png")));
            buttonIcons[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Square Setting.png")));
            buttonIcons[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Square More.png")));
            buttonIcons[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Square Info.png")));
            volumeButton = new BufferedImage[6];
            volumeButton[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Volume 0.png")));
            volumeButton[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Volume 1.png")));
            volumeButton[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Volume 2.png")));
            volumeButton[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Volume 3.png")));
            volumeButton[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Volume 4.png")));
            volumeButton[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Volume 5.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, 0, 0, gp.maxScreenCol * gp.tileSize, gp.maxScreenRow * gp.tileSize, null);
        switch (gp.gameState){
            case PAUSE:
                g2.drawImage(pauseMenuImage, 0, 12 * 16, gp.maxScreenCol * gp.tileSize, 26 * 16, null);
                drawButtons(g2);
                drawButtonHighlight(g2);
                updateVolumeLevel();
                drawVolumeButtons(g2);
                break;
            case GAME_OVER:
                g2.drawImage(pauseMenuImage, 0, 12 * 16, gp.maxScreenCol * gp.tileSize, 26 * 16, null);
                drawButtons(g2);
                drawButtonHighlight(g2);
                updateVolumeLevel();
                drawVolumeButtons(g2);
                break;
        }
    }

    private void initializeButtons() {
        buttonBounds = new Rectangle[totalButtons];

        int buttonSize = 80;
        int startX = 80;
        int startY = 272;
        int spacingX = 80;
        int spacingY = 32;
        for (int i = 0; i < totalButtons; i++){
            int row = i / 3;
            int col = i % 3;
            int x = startX + col * (buttonSize + spacingX);
            int y = startY + row * (buttonSize + spacingY);
            buttonBounds[i] = new  Rectangle(x, y, buttonSize, buttonSize);
        }
    }

    private void drawButtons(Graphics2D g2) {

        int buttonSize = 80;
        int startX = 80;
        int startY = 272;
        int spacingX = 80;
        int spacingY = 32;

        for (int i = 0; i < totalButtons; i++){
            int row = i / 3;
            int col = i % 3;
            int x = startX + col * (buttonSize + spacingX);
            int y = startY + row * (buttonSize + spacingY);
            g2.drawImage(buttonIcons[i], x, y, buttonSize, buttonSize, null);
        }
    }

    public void selectLeftButton() {
        if (selectedButton % 3 != 0) {
            selectedButton--;
        }
    }

    public void selectRightButton() {
        if (selectedButton % 3 != 2) {
            selectedButton++;
        }
    }

    public void selectUpButton() {
        if (selectedButton >= 3) {
            selectedButton -= 3;
        }
    }



    public void selectDownButton() {
        if (selectedButton < 3) {
            selectedButton += 3;
        }
    }

    private void drawButtonHighlight(Graphics2D g2) {
        Rectangle bounds = buttonBounds[selectedButton];
        g2.setColor(new Color(255, 255, 0, 150));
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(bounds.x - 5, bounds.y - 5, bounds.width + 10, bounds.height + 10);
        g2.setColor(new Color(255, 255, 255, 50));
        g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    private void drawVolumeButtons(Graphics2D g2) {
        int x = 64;
        int y = 480;
        int widthVolume = 432;
        int heightVolume = 64;
        g2.drawImage(volumeButton[volumeLevel], x, y, widthVolume, heightVolume, null);
    }
    private void updateVolumeLevel() {
        if (gp.keyHandler.highPressed && !highPressedLastFrame) {
            if (volumeLevel < 5) {
                volumeLevel++;
            }
        }

        if (gp.keyHandler.lowPressed && !lowPressedLastFrame) {
            if (volumeLevel > 0) {
                volumeLevel--;
            }
        }
        highPressedLastFrame = gp.keyHandler.highPressed;
        lowPressedLastFrame = gp.keyHandler.lowPressed;
    }

    public void activateSelectedButton() {
        switch (selectedButton) {
            case 0: // Restart
                gp.restart();
                break;
            case 1: // Sound
                try {
                    isMuted = !isMuted;
                    if (isMuted) {
                        gp.stopMusic();
                        buttonIcons[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Square Mute.png")));
                    } else {
                        buttonIcons[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tileset/Pause menu/Square Volume.png")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2: // Home
                gp.gameQuit();
                break;
            case 3: // Settings
                // Open settings
                break;
            case 4: // Menu
                // Open menu
                break;
            case 5: // Info
                // Show info
                break;
        }
    }

    public int getSelectedButton() {
        return selectedButton;
    }
}
