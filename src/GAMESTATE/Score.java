package GAMESTATE;

import MAIN.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

public class Score {
    GamePanel gp;
    BufferedImage image;

    public Score(GamePanel gp) {
        this.gp = gp;
        loadImages();
    }

    public void loadImages() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Background/test.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveScore(int score) {
        try (FileWriter writer = new FileWriter("src/GAMESTATE/Score.txt")) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public int loadScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/GAMESTATE/Score.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int score = Integer.parseInt(line);
                return score;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void saveHighScore(int newScore) {
        int currentHighScore = loadScore();
        if (newScore > currentHighScore) {
            saveScore(newScore);
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, 0, 0, gp.maxScreenCol * gp.tileSize, gp.maxScreenRow * gp.tileSize, null);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        g2.setColor(Color.WHITE);
        g2.drawString("High Score: " + formatNumber(loadScore()), 130, 390);
        g2.drawString("Try your best!!!", 115, 450 );
        g2.drawString("Press ESC to return", 60, 740);
    }

    private String formatNumber(int num) {
        return String.format("%,d", num);
    }
}
