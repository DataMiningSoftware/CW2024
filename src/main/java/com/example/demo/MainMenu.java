package com.example.demo;

import com.example.demo.Interfaces.Level_Interface;
import com.example.demo.Scene_Manipulator.Scene_Properties;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

/**
 * Main menu scene for the game, displaying the title and buttons to start or exit the game.
 * It also handles background music and scene setup.
 */
public class MainMenu extends Scene_Properties {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background9.gif"; // Path to the background image
    private static final String MUSIC_FILE = "/com/example/demo/sounds/MainMenuOST.mp3"; // Path to music file
    private MediaPlayer mediaPlayer; // MediaPlayer for playing background music

    /**
     * Constructor to initialize the main menu scene.
     * 
     * @param screenHeight The height of the screen.
     * @param screenWidth  The width of the screen.
     */
    public MainMenu(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, 1000, 3000, 0); // Call parent constructor
        playBackgroundMusic(); // Start playing background music when the menu is created
    }

    /**
     * Initializes the main menu scene with title, buttons, and background.
     * 
     * @return The Scene object representing the main menu.
     */
    @Override
    public Scene initializeScene() {
        VBox menu = createMenuLayout();

        // Create background image and stack the menu over it
        StackPane root = new StackPane();
        root.getChildren().addAll(createBackgroundImage(), menu);

        return new Scene(root, getScreenWidth(), getScreenHeight());
    }

    /**
     * Creates the layout for the menu including the title and buttons.
     * 
     * @return The VBox layout containing the menu elements.
     */
    private VBox createMenuLayout() {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);

        Text title = createTitle(); // Create the game title
        Button startButton = createStartButton(); // Create the start button
        Button exitButton = createExitButton(); // Create the exit button

        menu.getChildren().addAll(title, startButton, exitButton);
        return menu;
    }

    /**
     * Creates the title text with specific font, style, and animation.
     * 
     * @return The Text object representing the title.
     */
    private Text createTitle() {
        Text title = new Text("Sky Survivor");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 120));
        title.setFill(Color.RED);
        title.setStyle("-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 5);");

        // Create a pulsating effect for the title text
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, e -> title.setOpacity(1)),
            new KeyFrame(Duration.seconds(0.5), e -> title.setOpacity(0.6))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

        return title;
    }

    /**
     * Creates the start button and sets its action to start the game.
     * 
     * @return The Button object for starting the game.
     */
    private Button createStartButton() {
        Button startButton = new Button("Start Game");
        styleButton(startButton);
        startButton.setOnAction(e -> {
            stopMusic(); // Stop music when the game starts
            setChanged();
            notifyObservers("com.example.demo.Levels.LevelOne"); // Notify the controller to load LevelOne
        });
        return startButton;
    }

    /**
     * Creates the exit button to close the application.
     * 
     * @return The Button object for exiting the game.
     */
    private Button createExitButton() {
        Button exitButton = new Button("Exit");
        styleButton(exitButton);
        exitButton.setOnAction(e -> System.exit(0)); // Exit the game when clicked
        return exitButton;
    }

    /**
     * Creates the background image for the main menu scene.
     * 
     * @return The ImageView representing the background.
     */
    private ImageView createBackgroundImage() {
        ImageView background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));
        background.setPreserveRatio(false);
        background.setFitWidth(getScreenWidth());
        background.setFitHeight(getScreenHeight());
        return background;
    }

    /**
     * Applies a consistent style to the given button, including hover effects.
     * 
     * @param button The Button object to style.
     */
    private void styleButton(Button button) {
        button.setPrefWidth(300);
        button.setPrefHeight(50);
        button.setStyle("-fx-background-color: #2c2c2c; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 15px 30px; " +
                        "-fx-border-color: #000000; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 15px; " +
                        "-fx-background-radius: 15px;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #444444; " +
                                                      "-fx-text-fill: white; " +
                                                      "-fx-font-size: 20px; " +
                                                      "-fx-font-weight: bold; " +
                                                      "-fx-padding: 15px 30px; " +
                                                      "-fx-border-color: #ffffff; " +
                                                      "-fx-border-width: 2px; " +
                                                      "-fx-border-radius: 15px; " +
                                                      "-fx-background-radius: 15px;"));

        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #2c2c2c; " +
                                                     "-fx-text-fill: white; " +
                                                     "-fx-font-size: 20px; " +
                                                     "-fx-font-weight: bold; " +
                                                     "-fx-padding: 15px 30px; " +
                                                     "-fx-border-color: #000000; " +
                                                     "-fx-border-width: 2px; " +
                                                     "-fx-border-radius: 15px; " +
                                                     "-fx-background-radius: 15px;"));
    }

    /**
     * Initializes the friendly units for the main menu (not needed here).
     */
    @Override
    protected void initializeFriendlyUnits() {
        // No friendly units in the main menu
    }

    /**
     * Checks if the game is over (not applicable in the main menu).
     */
    @Override
    protected void checkIfGameOver() {
        // No game-over logic in the main menu
    }

    /**
     * Spawns enemy units (not applicable in the main menu).
     */
    @Override
    protected void spawnEnemyUnits() {
        // No enemies to spawn in the main menu
    }

    /**
     * Instantiates a level view for the main menu (just a placeholder).
     * 
     * @return A Level_Interface instance representing the level view.
     */
    @Override
    protected Level_Interface instantiateLevelView() {
        return new Level_Interface(getRoot(), 0, currentNumberOfEnemies); // Placeholder LevelView
    }

    /**
     * Plays background music for the main menu.
     * The music loops indefinitely.
     */
    private void playBackgroundMusic() {
        try {
            String musicPath = getClass().getResource(MUSIC_FILE).toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music indefinitely
            mediaPlayer.setAutoPlay(true); // Automatically start playing the music
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }

    /**
     * Stops the background music when the game starts or the menu is exited.
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the music playback
        }
    }
}
