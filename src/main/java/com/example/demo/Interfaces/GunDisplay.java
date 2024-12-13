package com.example.demo.Interfaces;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Class responsible for displaying the current gun in use on the screen.
 * It provides functionality to display and update the gun's name.
 */
public class GunDisplay {

    private static final String DEFAULT_GUN = "PISTOL";  // Default gun name
    private static final String FONT_FAMILY = "Arial";    // Font family for displaying text
    private static final int FONT_SIZE = 20;              // Font size for displaying text
    private static final FontWeight FONT_WEIGHT = FontWeight.BOLD; // Font weight for displaying text
    private static final Color FONT_COLOR = Color.WHITE;  // Font color for displaying text

    private final VBox gunBox;  // VBox to contain the gun display elements
    private final Text gunText; // Text object to display the current gun

    /**
     * Constructor to initialize the GunDisplay with the default gun.
     * It creates a VBox container and a Text object for the default gun ("PISTOL").
     */
    public GunDisplay() {
        this.gunBox = new VBox();                  // Initialize the VBox container
        this.gunText = createGunText(DEFAULT_GUN); // Create the default gun text
        gunBox.getChildren().add(gunText);        // Add the gun text to the VBox
    }

    /**
     * Updates the displayed gun name.
     *
     * @param gunName The name of the new gun to be displayed.
     */
    public void updateGunDisplay(String gunName) {
        gunText.setText("Gun: " + gunName); // Update the gun text with the new gun name
    }

    /**
     * Creates a Text object for displaying the gun name with the specified styling.
     *
     * @param gunName The name of the gun to display.
     * @return The styled Text object.
     */
    private Text createGunText(String gunName) {
        Text text = new Text("Gun: " + gunName); // Create a new Text object with the gun name
        text.setFont(Font.font(FONT_FAMILY, FONT_WEIGHT, FONT_SIZE)); // Set the font properties
        text.setFill(FONT_COLOR); // Set the font color
        return text; // Return the styled Text object
    }

    /**
     * Gets the VBox containing the gun display.
     *
     * @return The VBox containing the gun display elements.
     */
    public VBox getGunBox() {
        return gunBox; // Return the VBox containing the gun display
    }

    /**
     * Gets the Text object that displays the current gun.
     *
     * @return The Text object displaying the current gun.
     */
    public Text getGunText() {
        return gunText; // Return the Text object displaying the current gun
    }
}
