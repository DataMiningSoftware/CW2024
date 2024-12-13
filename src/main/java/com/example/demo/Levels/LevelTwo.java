package com.example.demo.Levels;

import com.example.demo.Entities.ActiveActorDestructible;
import com.example.demo.Entities.EnemyPlaneV2;
import com.example.demo.Interfaces.Level_Interface;
import com.example.demo.Scene_Manipulator.Scene_Properties;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The LevelTwo class represents the second level in the game.
 * It manages the gameplay mechanics, including spawning enemies, tracking kills, and transitioning to the next level.
 */
public class LevelTwo extends Scene_Properties {

    // Constants for the level configuration
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.gif";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelBoss";
    private static final int TOTAL_ENEMIES = 2;
    private static final int KILLS_TO_ADVANCE = 15;
    private static final double ENEMY_SPAWN_PROBABILITY = 1.5;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final String MUSIC_FILE = "/com/example/demo/sounds/LevelTwoOST.mp3"; // Path to level-specific music file

    public MediaPlayer mediaPlayer; // MediaPlayer for background music

    /**
     * Constructs a new LevelTwo instance.
     * Initializes the level with the appropriate background, player health, and background music.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth The width of the screen.
     */
    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        playBackgroundMusic(); // Play background music when the level is created
    }

    /**
     * Checks if the game is over by evaluating the user's status and kill count.
     * If the user is destroyed, the game is lost. If the user has reached the required number of kills,
     * the next level is triggered.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            handleGameOver(); // Handle game over scenario
        } else if (userHasReachedKillTarget()) {
            proceedToNextLevel(); // Proceed to the next level when the kill target is reached
        }
    }

    /**
     * Initializes the friendly units for the level.
     * This includes adding the user (player) to the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser()); // Add the user to the scene
    }

    /**
     * Spawns enemy units in the level.
     * The number of enemies is limited by the total enemies for the level and a spawn probability.
     * Enemies are added to the scene when the conditions are met.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                spawnEnemy(); // Spawn a new enemy at a random position
            }
        }
    }

    /**
     * Instantiates the level-specific view for LevelTwo.
     * This view is responsible for rendering the visual elements specific to this level.
     *
     * @return The Level_Interface representing the level view.
     */
    @Override
    protected Level_Interface instantiateLevelView() {
        return new Level_Interface(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE); // Instantiate the level view
    }

    /**
     * Helper method to check if the user has reached the required number of kills to advance to the next level.
     *
     * @return true if the user has reached the kill target, false otherwise.
     */
    boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Handles the game over scenario, including stopping the background music.
     */
    public void handleGameOver() {
        loseGame(); // Lose the game
        stopMusic(); // Stop the background music
    }

    /**
     * Proceeds to the next level when the kill target is met.
     * Stops the current level's background music and transitions to the next level.
     */
    public void proceedToNextLevel() {
        stopMusic(); // Stop the background music when transitioning to the next level
        goToNextLevel(NEXT_LEVEL); // Move to the next level
    }

    /**
     * Spawns a new enemy at a random Y position.
     * The enemy starts at the right side of the screen.
     */
    public void spawnEnemy() {
        double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        double newEnemyInitialXPosition = getScreenWidth(); // Starting from the right side of the screen
        ActiveActorDestructible newEnemy = new EnemyPlaneV2(newEnemyInitialXPosition, newEnemyInitialYPosition);
        addEnemyUnit(newEnemy); // Add the new enemy to the scene
    }

    /**
     * Plays the level background music.
     * The music will loop indefinitely during the level.
     */
    public void playBackgroundMusic() {
        try {
            String musicPath = getClass().getResource(MUSIC_FILE).toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop music indefinitely
            mediaPlayer.setAutoPlay(true); // Automatically start playing music
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }

    /**
     * Stops the background music if it's currently playing.
     * This can be used when the level ends or when transitioning to another level.
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the music
        }
    }
}
