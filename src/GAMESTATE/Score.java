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
        try (PrintWriter writer = new PrintWriter("/Score.txt")) {
            writer.println(score);
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    public int loadScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Score.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
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
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + formatNumber(loadScore()), 100, 100);
    }

    private String formatNumber(int num) {
        return String.format("%,d", num);
    }
}
