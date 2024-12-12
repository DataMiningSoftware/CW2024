package com.example.demo;

import com.example.demo.Level_Handler.Level_Interface;
import com.example.demo.Level_Handler.Scene_Properties;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MainMenu extends Scene_Properties {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background9.gif";
    private static final String MUSIC_FILE = "/com/example/demo/sounds/MainMenuOST.mp3"; // Path to music file
    private MediaPlayer mediaPlayer; // MediaPlayer for background music

    public MainMenu(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, 1000, 3000, 0); // Call parent constructor
        playBackgroundMusic(); // Play background music when the menu is created
    }

    @Override
    public Scene initializeScene() {
        // Create menu layout
        VBox menu = new VBox();
        menu.setSpacing(20); // Increased spacing for better layout
        menu.setAlignment(Pos.CENTER);

        // Create the daunting title with dramatic effects
        Text title = new Text("Sky Survivor");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 120)); // Extra bold font with larger size
        title.setFill(Color.RED);  // Intense red to add a sense of danger or urgency
        title.setStyle("-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 5);"); // Add drop shadow for depth

        // Pulsating effect to make it more dynamic
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, e -> title.setOpacity(1)),
            new KeyFrame(Duration.seconds(0.5), e -> title.setOpacity(0.6))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

        // Start Game Button
        Button startButton = new Button("Start Game");
        styleButton(startButton);
        startButton.setOnAction(e -> {
            stopMusic();

            setChanged();
            notifyObservers("com.example.demo.Levels.LevelOne"); // Notify controller to load LevelOne
        });

        // Exit Button
        Button exitButton = new Button("Exit");
        styleButton(exitButton);
        exitButton.setOnAction(e -> System.exit(0));

        // Add title and buttons to the menu
        menu.getChildren().addAll(title, startButton, exitButton);

        // Add background image
        ImageView background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));
        background.setPreserveRatio(false);  // Allow stretching
        background.setFitWidth(getScreenWidth());  // Set the background to the screen width
        background.setFitHeight(getScreenHeight()); // Set the background to the screen height

        // Use StackPane to layer the background and buttons
        StackPane root = new StackPane();
        root.getChildren().addAll(background, menu); // Ensure the menu is on top of the background

        // Create and return the scene
        return new Scene(root, getScreenWidth(), getScreenHeight());
    }

    private void styleButton(Button button) {
        button.setPrefWidth(300); // Set button width
        button.setPrefHeight(50); // Set button height

        // Cinematic dark theme button style with a border
        button.setStyle("-fx-background-color: #2c2c2c; " +  // Dark grey background
                        "-fx-text-fill: white; " +  // White text
                        "-fx-font-size: 20px; " +  // Font size
                        "-fx-font-weight: bold; " + // Bold font
                        "-fx-padding: 15px 30px; " + // Adjusted padding
                        "-fx-border-color: #000000; " +  // Black border
                        "-fx-border-width: 2px; " + // Border width
                        "-fx-border-radius: 15px; " +  // Rounded corners
                        "-fx-background-radius: 15px;");  // Rounded background

        // Hover effect for dark theme with a highlight
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #444444; " +  // Slightly lighter grey on hover
                                                      "-fx-text-fill: white; " +
                                                      "-fx-font-size: 20px; " +
                                                      "-fx-font-weight: bold; " +
                                                      "-fx-padding: 15px 30px; " +
                                                      "-fx-border-color: #ffffff; " +  // White border on hover
                                                      "-fx-border-width: 2px; " +
                                                      "-fx-border-radius: 15px; " +
                                                      "-fx-background-radius: 15px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #2c2c2c; " +  // Dark grey on exit
                                                     "-fx-text-fill: white; " +
                                                     "-fx-font-size: 20px; " +
                                                     "-fx-font-weight: bold; " +
                                                     "-fx-padding: 15px 30px; " +
                                                     "-fx-border-color: #000000; " +  // Black border on exit
                                                     "-fx-border-width: 2px; " +
                                                     "-fx-border-radius: 15px; " +
                                                     "-fx-background-radius: 15px;"));
    }

    @Override
    protected void initializeFriendlyUnits() {
        // No friendly units in the main menu
    }

    @Override
    protected void checkIfGameOver() {
        // No game-over logic in the main menu
    }

    @Override
    protected void spawnEnemyUnits() {
        // No enemies to spawn in the main menu
    }

    @Override
    protected Level_Interface instantiateLevelView() {
        return new Level_Interface(getRoot(), 0, currentNumberOfEnemies); // Placeholder LevelView
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

    // Optionally, stop the music when the scene is no longer in use
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
