package com.example.demo.Levels;

import com.example.demo.Entities.Boss;
import com.example.demo.Interfaces.LevelBoss_Interface;
import com.example.demo.Interfaces.Level_Interface;
import com.example.demo.Scene_Manipulator.Scene_Properties;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The LevelBoss class represents the Boss level in the game.
 * This level includes a boss fight and handles the game state specific to the boss level.
 */
public class LevelBoss extends Scene_Properties {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background7.gif";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final String MUSIC_FILE = "/com/example/demo/sounds/LevelBossOST.mp3"; // Path to music file

    private final Boss boss; // Boss unit for the level
    private LevelBoss_Interface levelView; // Level-specific view for Boss level
    private MediaPlayer mediaPlayer; // MediaPlayer for background music

    /**
     * Constructs a new LevelBoss instance.
     * Initializes the boss and starts the background music for the level.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth The width of the screen.
     */
    public LevelBoss(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = new Boss();
        playBackgroundMusic(); // Play background music when the boss level is created
    }

    /**
     * Initializes the friendly units for the level.
     * This includes adding the user (player) to the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        // Add the player (user) to the scene
        getRoot().getChildren().add(getUser());
    }

    /**
     * Checks if the game is over by evaluating the status of the user and the boss.
     * If the user is destroyed, the game is lost. If the boss is destroyed, the game is won.
     */
    @Override
    protected void checkIfGameOver() {
        // Check if the game is over by checking user's and boss's status
        if (userIsDestroyed()) {
            loseGame(); // Handle losing the game
        } else if (boss.isDestroyed()) {
            winGame(); // Handle winning the game
        }
    }

    /**
     * Spawns enemy units for the level.
     * In the boss level, the boss is spawned once no enemies are present.
     */
    @Override
    protected void spawnEnemyUnits() {
        // Spawn the boss when no enemies are present
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
        }
    }

    /**
     * Instantiates the level-specific view for the boss level.
     * This view is responsible for rendering the visual elements specific to the boss fight.
     *
     * @return The LevelBoss_Interface representing the boss level view.
     */
    @Override
    protected Level_Interface instantiateLevelView() {
        // Initialize the level-specific view
        levelView = new LevelBoss_Interface(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }

    /**
     * Plays the background music for the boss level.
     * The music will loop indefinitely during the level.
     */
    private void playBackgroundMusic() {
        try {
            // Load and play the background music
            String musicPath = getClass().getResource(MUSIC_FILE).toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);

            // Set the media player to loop the music indefinitely
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setAutoPlay(true); // Start the music automatically
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }

    /**
     * Stops the background music if it is playing.
     * This can be used when the level is no longer in use or when transitioning to another level.
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the music
        }
    }
}
