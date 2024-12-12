package com.example.demo.Levels;

import com.example.demo.EnemyPlaneV1;
import com.example.demo.Level_Handler.Level_Interface;
import com.example.demo.Level_Handler.Scene_Properties;
import com.example.demo.Projectiles.ActiveActorDestructible;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LevelOne extends Scene_Properties {
    
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.gif";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelTwo";
    private static final int TOTAL_ENEMIES = 2;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = 1.5;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final String MUSIC_FILE = "/com/example/demo/sounds/LevelOneOST.mp3"; // Path to level-specific music file
    
    private MediaPlayer mediaPlayer; // MediaPlayer for background music

    public LevelOne(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        playBackgroundMusic(); // Play background music when the level starts
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
            stopMusic(); // Stop music when game over
        }
        else if (userHasReachedKillTarget()) {
            stopMusic(); // Stop music when transitioning to next level
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
                ActiveActorDestructible newEnemy = new EnemyPlaneV1(getScreenWidth(), newEnemyInitialYPosition);
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

    // Method to play the level background music
    private void playBackgroundMusic() {
        try {
            String musicPath = getClass().getResource(MUSIC_FILE).toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop music indefinitely
            mediaPlayer.setAutoPlay(true); // Automatically play music
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }

    // Method to stop the background music
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
