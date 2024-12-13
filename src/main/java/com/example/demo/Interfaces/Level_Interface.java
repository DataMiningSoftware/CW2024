package com.example.demo.Interfaces;

import javafx.scene.Group;
import javafx.scene.text.Text;

/**
 * The Level_Interface class is responsible for managing and displaying the user interface (UI) elements
 * associated with a level, including the heart display, win image, game over image, and kill count.
 * It updates the UI elements based on the player's progress in the game.
 */
public class Level_Interface {

    // Constants for positioning elements on the screen
    private static final double HEART_DISPLAY_X_POSITION = 5;
    private static final double HEART_DISPLAY_Y_POSITION = 25;
    private static final int WIN_IMAGE_X_POSITION = 355;
    private static final int WIN_IMAGE_Y_POSITION = 175;
    private static final int LOSS_SCREEN_X_POSITION = 200;
    private static final int LOSS_SCREEN_Y_POSITION = -150;
    private static final double KILL_COUNTER_X_POSITION = 20;
    private static final double KILL_COUNTER_Y_POSITION = 60;

    private final Group root;  // The root node of the scene, where all UI elements are added
    private final WinImage winImage;  // Win image displayed when the level is won
    private final GameOverImage gameOverImage;  // Game Over image displayed when the player loses
    private final HeartDisplay heartDisplay;  // Heart display for remaining lives
    private Text killCountText;  // Text object to show the current kill count
    private int killsToAdvance;  // Number of kills required to advance to the next level

    /**
     * Constructor that initializes the Level_Interface with the specified root, number of hearts, and kills to advance.
     * 
     * @param root The root node of the scene where the UI elements will be added.
     * @param heartsToDisplay The number of hearts to display for the player.
     * @param killsToAdvance The number of kills required to advance to the next level.
     */
    public Level_Interface(Group root, int heartsToDisplay, int killsToAdvance) {
        this.root = root;
        this.killsToAdvance = killsToAdvance;

        // Initialize the UI elements
        this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
        this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
        this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
        
        // Initialize and display the kill counter
        initializeKillCountDisplay();
    }

    /**
     * Initializes the kill count display and adds it to the root node.
     * The kill counter is shown in the top-left corner of the screen.
     */
    private void initializeKillCountDisplay() {
        killCountText = new Text(KILL_COUNTER_X_POSITION, KILL_COUNTER_Y_POSITION, "Kills: 0/" + killsToAdvance);
        killCountText.setStyle("-fx-font-size: 20px; -fx-fill: white;");
        root.getChildren().add(killCountText);
    }

    /**
     * Displays the heart display (UI for remaining hearts) in the scene.
     */
    public void showHeartDisplay() {
        root.getChildren().add(heartDisplay.getContainer());
    }

    /**
     * Displays the win image when the level is won. The image is added to the scene.
     */
    public void showWinImage() {
        root.getChildren().add(winImage);
        winImage.showWinImage();
    }

    /**
     * Displays the game over image when the game ends. The image is added to the scene.
     */
    public void showGameOverImage() {
        root.getChildren().add(gameOverImage);
    }

    /**
     * Removes hearts from the heart display based on the remaining number of hearts.
     * This method is used to update the heart display when the player loses hearts.
     * 
     * @param heartsRemaining The number of hearts remaining after taking damage.
     */
    public void removeHearts(int heartsRemaining) {
        int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
        for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
            heartDisplay.removeHeart();
        }
    }

    /**
     * Returns the number of kills required to advance to the next level.
     * 
     * @return The number of kills required to advance to the next level.
     */
    public int getKillsToAdvance() {
        return killsToAdvance;
    }

    /**
     * Updates the kill count text based on the current number of kills.
     * This method updates the UI to reflect the progress toward the kill goal.
     * 
     * @param currentKills The current number of kills the player has made.
     */
    public void updateKillCount(int currentKills) {
        killCountText.setText("Kills: " + currentKills + "/" + killsToAdvance);
    }
}
