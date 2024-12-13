package com.example.demo.Interfaces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Class to manage and display the hearts (lives) on the screen.
 * It provides functionality to display a set number of hearts and remove hearts when needed.
 */
public class HeartDisplay {

    private static final String HEART_IMAGE_PATH = "/com/example/demo/images/heart.png"; // Path to the heart image
    private static final int HEART_HEIGHT = 50; // Height for each heart image
    private static final int FIRST_ITEM_INDEX = 0; // The index of the first heart image in the container

    private final HBox container; // HBox container for holding the heart images
    private final double xPosition; // The X position of the heart container
    private final double yPosition; // The Y position of the heart container
    private int heartsRemaining; // The number of hearts remaining to display

    /**
     * Constructor to initialize the heart display with the given position and number of hearts.
     *
     * @param xPosition        The X position of the heart container.
     * @param yPosition        The Y position of the heart container.
     * @param heartsToDisplay  The number of hearts to display initially.
     */
    public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
        this.xPosition = xPosition; // Set the X position
        this.yPosition = yPosition; // Set the Y position
        this.heartsRemaining = heartsToDisplay; // Set the initial number of hearts
        this.container = createContainer(); // Create the container to hold hearts
        initializeHearts(); // Initialize the hearts in the container
    }

    /**
     * Creates and returns the container (HBox) for holding the hearts.
     *
     * @return The HBox container that will hold the heart images.
     */
    private HBox createContainer() {
        HBox container = new HBox(); // Create a new HBox container
        container.setLayoutX(xPosition); // Set the X position for the container
        container.setLayoutY(yPosition); // Set the Y position for the container
        return container; // Return the HBox container
    }

    /**
     * Initializes the hearts to be displayed in the container based on the number of hearts remaining.
     * Each heart is represented as an image.
     */
    private void initializeHearts() {
        for (int i = 0; i < heartsRemaining; i++) {
            container.getChildren().add(createHeart()); // Add each heart image to the container
        }
    }

    /**
     * Creates a heart image view.
     * This method loads the heart image and sets its display properties (height and preserve ratio).
     *
     * @return The ImageView object representing a heart.
     */
    private ImageView createHeart() {
        ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_PATH).toExternalForm()));
        heart.setFitHeight(HEART_HEIGHT); // Set the height of the heart image
        heart.setPreserveRatio(true); // Maintain the aspect ratio of the heart image
        return heart; // Return the created ImageView for the heart
    }

    /**
     * Removes one heart from the display.
     * This method decreases the remaining hearts and updates the container accordingly.
     */
    public void removeHeart() {
        if (heartsRemaining > 0) {
            container.getChildren().remove(FIRST_ITEM_INDEX); // Remove the first heart from the container
            heartsRemaining--; // Decrease the remaining hearts count
        }
    }

    /**
     * Gets the container holding the heart images.
     *
     * @return The HBox container that holds the hearts.
     */
    public HBox getContainer() {
        return container; // Return the container with hearts
    }

    /**
     * Gets the number of hearts remaining.
     *
     * @return The number of hearts remaining.
     */
    public int getHeartsRemaining() {
        return heartsRemaining; // Return the number of hearts remaining
    }
}
