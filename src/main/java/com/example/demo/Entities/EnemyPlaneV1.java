package com.example.demo.Entities;

import com.example.demo.Projectiles.EnemyProjectile;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

/**
 * Represents an enemy plane in the game, specifically the version 1 of the enemy plane.
 * This plane moves horizontally across the screen and has the ability to fire projectiles
 * at the player's plane. The plane also features a spinning animation when active and reacts
 * to taking damage.
 */
public class EnemyPlaneV1 extends FighterPlane {

    private static final String IMAGE_NAME = "spacetriangle.png";  // Image representing the enemy plane
    private static final int IMAGE_HEIGHT = 150;  // Height of the enemy plane image
    private static final int HORIZONTAL_VELOCITY = -6;  // Horizontal speed of the enemy plane
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;  // X offset for projectile firing position
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;  // Y offset for projectile firing position
    private static final int INITIAL_HEALTH = 2;  // Initial health of the enemy plane
    private static final double FIRE_RATE = .01;  // Probability of firing a projectile

    private RotateTransition rotateTransition;  // Rotation animation for the enemy plane
    private boolean isDestroyed = false;  // Flag to track if the plane is destroyed

    /**
     * Constructs a new EnemyPlaneV1 with a specified initial position.
     * The enemy plane is initialized with a spinning animation and a health value.
     *
     * @param initialXPos Initial X position of the enemy plane.
     * @param initialYPos Initial Y position of the enemy plane.
     */
    public EnemyPlaneV1(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        initializeSpin();  // Set up the spinning animation
    }

    /**
     * Updates the position of the enemy plane by moving it horizontally.
     * The plane moves left at a fixed speed.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Fires a projectile towards the player's plane. The projectile is fired
     * based on the fire rate, which is a random probability check.
     *
     * @param userPlane The player's {@link UserPlane}, used to calculate firing direction (not currently utilized).
     * @return The {@link ActiveActorDestructible} object representing the fired projectile,
     *         or null if no projectile is fired.
     */
    @Override
    public ActiveActorDestructible fireProjectile(UserPlane userPlane) {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectile(projectileXPosition, projectileYPosition);  // Create and return new projectile
        }
        return null;  // No projectile fired
    }

    /**
     * Updates the behavior of the enemy plane. This method is called to update the 
     * plane’s position based on its movement and fire status.
     */
    @Override
    public void updateActor() {
        updatePosition();  // Update the plane's position
    }

    /**
     * Applies damage to the enemy plane. This method calls the superclass's takeDamage() 
     * method and triggers additional visual effects related to damage.
     */
    @Override
    public void takeDamage() {
        super.takeDamage();  // Call the superclass method to reduce health
        triggerDamageEffect();  // Trigger the damage effect (e.g., flashing red)
    }

    /**
     * Initializes the spinning animation for the enemy plane. The plane rotates
     * continuously with a smooth animation.
     */
    private void initializeSpin() {
        rotateTransition = new RotateTransition(Duration.seconds(0.5), this);  // Set up the rotation duration and target
        rotateTransition.setFromAngle(0);  // Start angle
        rotateTransition.setToAngle(360);  // Full rotation
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);  // Infinite loop
        rotateTransition.setInterpolator(Interpolator.LINEAR);  // Linear interpolation for smooth rotation
        rotateTransition.play();  // Start the rotation animation
    }

    /**
     * Destroys the enemy plane, triggering the destruction sequence including
     * visual effects. The plane is marked as destroyed and the destruction effects 
     * are triggered once.
     */
    public void destroy() {
        if (!isDestroyed) {
            super.destroy();  // Call the superclass's destroy method
            triggerDeathEffect();  // Trigger the visual effect for death
            isDestroyed = true;  // Mark the plane as destroyed
        }
    }
}
