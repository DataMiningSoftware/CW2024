package com.example.demo.Levels;

import com.example.demo.EnemyPlaneV2;
import com.example.demo.Level_Handler.Level_Interface;
import com.example.demo.Level_Handler.Scene_Properties;
import com.example.demo.Projectiles.ActiveActorDestructible;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LevelTwo extends Scene_Properties {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.gif";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelBoss";
    private static final int TOTAL_ENEMIES = 2;
    private static final int KILLS_TO_ADVANCE = 15;
    private static final double ENEMY_SPAWN_PROBABILITY = 1.5;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    private static final String MUSIC_FILE = "/com/example/demo/sounds/LevelTwoOST.mp3"; // Path to music file
    private MediaPlayer mediaPlayer; // MediaPlayer for background music

    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        playBackgroundMusic(); // Play background music when the level is created
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget()) {
        	stopMusic();
            goToNextLevel(NEXT_LEVEL);
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                double newEnemyInitialXPosition = getScreenWidth(); // Starting from the right side of the screen
                ActiveActorDestructible newEnemy = new EnemyPlaneV2(newEnemyInitialXPosition, newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected Level_Interface instantiateLevelView() {
        return new Level_Interface(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    private void playBackgroundMusic() {
        try {
            // Load and play the background music
            String musicPath = getClass().getResource(MUSIC_FILE).toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);

            // Set loop to true to play music continuously
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setAutoPlay(true);
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }

    // Optionally, stop the music when the level is no longer in use
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
