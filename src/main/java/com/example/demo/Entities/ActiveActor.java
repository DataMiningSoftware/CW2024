package com.example.demo.Entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The ActiveActor class represents an actor that is actively moving or interacting in the game.
 * It extends ImageView to display an image in the JavaFX scene and provides methods to update its position.
 * Subclasses must implement the abstract method `updatePosition` to define specific movement logic.
 */
public abstract class ActiveActor extends ImageView {

    private static final String IMAGE_LOCATION = "/com/example/demo/images/";

    /**
     * Constructor for ActiveActor, initializes the actor with an image, position, and size.
     * 
     * @param imageName   The name of the image file to be loaded for the actor.
     * @param imageHeight The height of the image to be displayed.
     * @param initialXPos The initial X position of the actor on the screen.
     * @param initialYPos The initial Y position of the actor on the screen.
     */
    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        setImage(loadImage(imageName)); // Load the image based on the provided imageName
        setLayoutX(initialXPos);         // Set the initial X position
        setLayoutY(initialYPos);         // Set the initial Y position
        setFitHeight(imageHeight);       // Set the height of the image
        setPreserveRatio(true);          // Preserve the aspect ratio of the image
    }

    /**
     * Abstract method that must be implemented by subclasses to update the position of the actor.
     * This method is intended to be used for updating the actor's state or movement during each game frame.
     */
    public abstract void updatePosition();

    /**
     * Moves the actor horizontally by a given amount.
     * 
     * @param horizontalMove The amount to move the actor horizontally. 
     *                       Positive values move the actor to the right, negative values move it to the left.
     */
    protected void moveHorizontally(double horizontalMove) {
        setTranslateX(getTranslateX() + horizontalMove); // Move the actor horizontally by updating the translateX property
    }

    /**
     * Moves the actor vertically by a given amount.
     * 
     * @param verticalMove The amount to move the actor vertically. 
     *                     Positive values move the actor down, negative values move it up.
     */
    protected void moveVertically(double verticalMove) {
        setTranslateY(getTranslateY() + verticalMove); // Move the actor vertically by updating the translateY property
    }

    /**
     * Loads an image from the resources folder based on the given image name.
     * 
     * @param imageName The name of the image file to load.
     * @return The Image object loaded from the resources folder.
     */
    private Image loadImage(String imageName) {
        return new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()); // Load and return the image from the resource folder
    }
}
