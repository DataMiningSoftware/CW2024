package com.example.demo.Projectiles;

import com.example.demo.Entities.UserPlane;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Represents a Boss's projectile fired towards the player.
 * This class extends the {@link Projectile} class and includes 
 * features specific to the Boss's projectile, including sound effects
 * and velocity calculation towards the player's plane.
 */
public class BossProjectile extends Projectile {

    private static final String IMAGE_NAME = "projectile_boss.png";  // The image for the projectile
    private static final int IMAGE_HEIGHT = 100;  // The height of the projectile
    private static final int HORIZONTAL_VELOCITY = -25;  // The horizontal velocity of the projectile

    private double targetVelocityX;  // The calculated horizontal velocity towards the player
    private double targetVelocityY;  // The calculated vertical velocity towards the player

    private MediaPlayer projectileSoundPlayer;  // MediaPlayer to manage the sound of the projectile

    /**
     * Constructs a new {@link BossProjectile} and initializes its position and behavior.
     * The projectile will automatically calculate its direction towards the player's plane
     * and play the corresponding sound upon creation.
     *
     * @param initialXPos Initial X position of the projectile.
     * @param initialYPos Initial Y position of the projectile.
     * @param userPlane The {@link UserPlane} object representing the player's plane, 
     *                  used to calculate the direction of the projectile.
     */
    public BossProjectile(double initialXPos, double initialYPos, UserPlane userPlane) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        calculateTargetVelocity(userPlane);  // Calculate the velocity towards the user
        playProjectileSound();  // Play the sound when the projectile is created
    }

    /**
     * Calculates the velocity required for the projectile to move towards the player's plane.
     * This method computes the direction by finding the difference in X and Y positions between
     * the projectile and the user plane, then normalizes the vector and scales the velocity.
     *
     * @param userPlane The {@link UserPlane} object used to calculate the direction.
     */
    private void calculateTargetVelocity(UserPlane userPlane) {
        // Get the position of the user plane
        double userX = userPlane.getLayoutX() - userPlane.getTranslateX();
        double userY = userPlane.getLayoutY() + userPlane.getTranslateY();

        // Calculate the difference between the projectile and the user plane
        double deltaX = userX - getLayoutX();
        double deltaY = userY - getLayoutY();
        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);  // Calculate the magnitude of the vector

        // Normalize the vector and scale the velocity
        targetVelocityX = (deltaX / magnitude) * Math.abs(HORIZONTAL_VELOCITY);
        targetVelocityY = (deltaY / magnitude) * Math.abs(HORIZONTAL_VELOCITY);
    }

    /**
     * Plays the sound effect for the projectile when it is created.
     * The sound is loaded from a specific file path and played through a {@link MediaPlayer}.
     */
    private void playProjectileSound() {
        String soundFile = "/com/example/demo/sounds/BossProjectileSound.mp3";  // Path to the sound file
        Media sound = new Media(getClass().getResource(soundFile).toExternalForm());
        projectileSoundPlayer = new MediaPlayer(sound);
        projectileSoundPlayer.play();  // Play the sound
    }

    /**
     * Updates the position of the projectile by moving it based on its calculated velocity.
     * This method calls the movement methods to adjust the position in both X and Y directions.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(targetVelocityX);  // Move the projectile horizontally
        moveVertically(targetVelocityY);  // Move the projectile vertically
    }

    /**
     * Updates the behavior of the Boss's projectile. This method overrides the {@link Projectile#updateActor()}
     * method to update the projectile's position based on its target velocity.
     */
    @Override
    public void updateActor() {
        updatePosition();  // Update the position based on calculated velocities
    }
}
