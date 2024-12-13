package com.example.demo.Interfaces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the "Game Over" image displayed at the end of the game.
 * This class extends ImageView to display a specific game over image at a given position.
 */
public class GameOverImage extends ImageView {

    // Path to the "Game Over" image file.
    private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

    /**
     * Constructs a GameOverImage object and sets its image and position on the screen.
     * 
     * @param xPosition The X coordinate for the position of the image.
     * @param yPosition The Y coordinate for the position of the image.
     */
    public GameOverImage(double xPosition, double yPosition) {
        // Load and set the game over image from the specified resource
        setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        
        // Set the position of the image on the screen
        setLayoutX(xPosition);
        setLayoutY(yPosition);
    }
}
