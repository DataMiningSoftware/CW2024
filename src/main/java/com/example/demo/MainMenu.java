package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainMenu extends Scene_Properties {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.gif";

    public MainMenu(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, 1000, 4000, 0); // Call parent constructor
    }

    @Override
    public Scene initializeScene() {
        // Create menu layout
        VBox menu = new VBox();
        menu.setSpacing(20); // Increased spacing for better layout
        menu.setAlignment(Pos.CENTER);

        // Create the title
        Text title = new Text("Mystical Might");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        title.setFill(Color.WHITE);

        // Start Game Button
        Button startButton = new Button("Start Game");
        styleButton(startButton);
        startButton.setOnAction(e -> {
            setChanged();
            notifyObservers("com.example.demo.LevelOne"); // Notify controller to load LevelOne
        });

        // Exit Button
        Button exitButton = new Button("Exit");
        styleButton(exitButton);
        exitButton.setOnAction(e -> System.exit(0));

        // Add title and buttons to the menu
        menu.getChildren().addAll(title, startButton, exitButton);

        // Add background image
        ImageView background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));
        background.setFitWidth(getScreenWidth());
        background.setFitHeight(getScreenHeight());

        // Use StackPane to layer the background and buttons
        StackPane root = new StackPane();
        root.getChildren().addAll(background, menu);

        // Create and return the scene
        return new Scene(root, getScreenWidth(), getScreenHeight());
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #336699; " + // Blue background
                        "-fx-text-fill: white; " +         // White text
                        "-fx-font-size: 16px; " +          // Font size
                        "-fx-font-weight: bold; " +        // Bold font
                        "-fx-padding: 10px 20px; " +       // Padding around text
                        "-fx-border-radius: 5px; " +       // Rounded corners
                        "-fx-background-radius: 5px;");    // Rounded background
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #5588cc; " + // Lighter blue on hover
                                                      "-fx-text-fill: white; " +
                                                      "-fx-font-size: 16px; " +
                                                      "-fx-font-weight: bold; " +
                                                      "-fx-padding: 10px 20px; " +
                                                      "-fx-border-radius: 5px; " +
                                                      "-fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #336699; " + // Original blue on exit
                                                     "-fx-text-fill: white; " +
                                                     "-fx-font-size: 16px; " +
                                                     "-fx-font-weight: bold; " +
                                                     "-fx-padding: 10px 20px; " +
                                                     "-fx-border-radius: 5px; " +
                                                     "-fx-background-radius: 5px;"));
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
        return new Level_Interface(getRoot(), 0); // Placeholder LevelView
    }
}
