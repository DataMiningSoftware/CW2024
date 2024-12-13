package com.example.demo.Interfaces;

import com.example.demo.Entities.Shield;

import javafx.scene.Group;

/**
 * The LevelBoss_Interface class manages the UI elements specific to the boss level, 
 * including the shield image for the boss.
 */
public class LevelBoss_Interface extends Level_Interface {

    // Constants for positioning the shield image relative to the boss
    private static final int SHIELD_X_OFFSET = -50;
    private static final int SHIELD_Y_OFFSET = -50;

    private final Group root;  // Root node of the scene
    private final Shield shieldImage;  // Shield representation

    /**
     * Constructor to initialize the boss level interface, including hearts and shield.
     * 
     * @param root The root node of the scene where the UI elements will be added.
     * @param heartsToDisplay The number of hearts to display for the player in the boss level.
     */
    public LevelBoss_Interface(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay, heartsToDisplay); // Initialize heart display
        this.root = root;
        this.shieldImage = new Shield(0, 0); // Default position; will sync with boss

        initializeShield();
    }

    /**
     * Adds the shield image to the root node of the scene, making it part of the display.
     */
    private void initializeShield() {
        shieldImage.setVisible(false); // Hidden by default
        root.getChildren().add(shieldImage);
    }

    /**
     * Synchronizes the shield position with the boss.
     * 
     * @param bossX The X-coordinate of the boss.
     * @param bossY The Y-coordinate of the boss.
     */
    public void updateShieldPosition(double bossX, double bossY) {
        shieldImage.setLayoutX(bossX + SHIELD_X_OFFSET);
        shieldImage.setLayoutY(bossY + SHIELD_Y_OFFSET);
    }

    /**
     * Displays the shield.
     */
    public void showShield() {
        shieldImage.setVisible(true);
    }

    /**
     * Hides the shield.
     */
    public void hideShield() {
        shieldImage.setVisible(false);
    }
}
