package com.example.demo.Levels;

import com.example.demo.Boss;
import com.example.demo.Level_Handler.LevelBoss_Interface;
import com.example.demo.Level_Handler.Level_Interface;
import com.example.demo.Level_Handler.Scene_Properties;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LevelBoss extends Scene_Properties {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background7.gif";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final Boss boss;
    private LevelBoss_Interface levelView;

    private static final String MUSIC_FILE = "/com/example/demo/sounds/LevelBossOST.mp3"; // Path to music file
    private MediaPlayer mediaPlayer; // MediaPlayer for background music

    public LevelBoss(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = new Boss();
        playBackgroundMusic(); // Play background music when the boss level is created
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (boss.isDestroyed()) {
            winGame();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
        }
    }

    @Override
    protected Level_Interface instantiateLevelView() {
        levelView = new LevelBoss_Interface(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }

    // Method to play background music
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
