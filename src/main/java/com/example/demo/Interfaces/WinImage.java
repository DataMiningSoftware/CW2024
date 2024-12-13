package com.example.demo.Interfaces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The WinImage class represents an image that is displayed when the player wins the game.
 * It extends ImageView to provide the functionality of displaying a win image at a specified position.
 */
public class WinImage extends ImageView {

    // Constant for the image file path and dimensions
    private static final String WIN_IMAGE_PATH = "/com/example/demo/images/youwin.png";
    private static final int IMAGE_HEIGHT = 1000;
    private static final int IMAGE_WIDTH = 1100;

    /**
     * Constructs a WinImage instance with the specified position.
     *
     * @param xPosition The x-coordinate of the image's position on the screen.
     * @param yPosition The y-coordinate of the image's position on the screen.
     */
    public WinImage(double xPosition, double yPosition) {
        initializeWinImage(xPosition, yPosition);
    }

    /**
     * Initializes the win image by setting its properties such as image, size, position, and visibility.
     *
     * @param xPosition The x-coordinate of the image's position on the screen.
     * @param yPosition The y-coordinate of the image's position on the screen.
     */
    private void initializeWinImage(double xPosition, double yPosition) {
        // Set the image from the specified resource path
        setImage(new Image(getClass().getResource(WIN_IMAGE_PATH).toExternalForm()));
        setVisible(false); // Initially hide the image
        setFitHeight(IMAGE_HEIGHT); // Set the image's height
        setFitWidth(IMAGE_WIDTH); // Set the image's width
        setLayoutX(xPosition); // Set the x-coordinate of the image
        setLayoutY(yPosition); // Set the y-coordinate of the image
    }

    /**
     * Displays the win image by making it visible.
     */
    public void display() {
        setVisible(true); // Make the image visible when called
    }

    /**
     * Shows the win image by setting its visibility to true.
     */
    public void showWinImage() {
        this.setVisible(true); // Set the visibility of the win image to true
    }
}
