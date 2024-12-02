package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenu extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.gif";

    public MainMenu(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, 0); // Call parent constructor
    }

    @Override
    public Scene initializeScene() {
        // Create menu layout
        VBox menu = new VBox();
        menu.setSpacing(10);
        menu.setAlignment(Pos.CENTER);

        // Start Game Button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            setChanged();
            notifyObservers("com.example.demo.LevelOne"); // Notify controller to load LevelOne
        });

        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        menu.getChildren().addAll(startButton, exitButton);

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

    @Override
    protected void initializeFriendlyUnits() {}

    @Override
    protected void checkIfGameOver() {}

    @Override
    protected void spawnEnemyUnits() {}

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), 0); // Return a placeholder LevelView
    }
}
