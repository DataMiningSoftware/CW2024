package com.example.demo.Level_Handler;

import com.example.demo.GameOverImage;
import com.example.demo.HeartDisplay;
import com.example.demo.WinImage;

import javafx.scene.Group;
import javafx.scene.text.Text;

public class Level_Interface {

    private static final double HEART_DISPLAY_X_POSITION = 5;
    private static final double HEART_DISPLAY_Y_POSITION = 25;
    private static final int WIN_IMAGE_X_POSITION = 355;
    private static final int WIN_IMAGE_Y_POSITION = 175;
    private static final int LOSS_SCREEN_X_POSITION = 200;
    private static final int LOSS_SCREEN_Y_POSITION = -150;
    private static final double KILL_COUNTER_X_POSITION = 20;  // X position for the kill counter
    private static final double KILL_COUNTER_Y_POSITION = 60;  // Y position for the kill counter
    private final Group root;
    private final WinImage winImage;
    private final GameOverImage gameOverImage;
    private final HeartDisplay heartDisplay;
    private Text killCountText;  // The text object for the kill counter
    private int killsToAdvance;  // Number of kills needed to advance to the next level

    public Level_Interface(Group root, int heartsToDisplay, int killsToAdvance) {
        this.root = root;
        this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
        this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
        this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
        this.killsToAdvance = killsToAdvance;  // Initialize target kills
        
        // Initialize kill count display
        initializeKillCountDisplay();
    }

    private void initializeKillCountDisplay() {
        // Initialize the kill counter text with the target kills
        killCountText = new Text(KILL_COUNTER_X_POSITION, KILL_COUNTER_Y_POSITION, "Kills: 0/" + killsToAdvance);
        killCountText.setStyle("-fx-font-size: 20px; -fx-fill: white;");
        root.getChildren().add(killCountText);  // Add it to the root node
    }

    public void showHeartDisplay() {
        root.getChildren().add(heartDisplay.getContainer());
    }

    public void showWinImage() {
        root.getChildren().add(winImage);
        winImage.showWinImage();
    }

    public void showGameOverImage() {
        root.getChildren().add(gameOverImage);
    }

    public void removeHearts(int heartsRemaining) {
        int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
        for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
            heartDisplay.removeHeart();
        }
    }
    
    public int getKillsToAdvance() {
        return killsToAdvance;
    }

    public void updateKillCount(int currentKills) {
        killCountText.setText("Kills: " + currentKills + "/" + killsToAdvance);
    }

}