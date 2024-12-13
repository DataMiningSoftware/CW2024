package com.example.demo.Entities;

import com.example.demo.Projectiles.EnemyProjectileV2;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

/**
 * Represents the second version of an enemy plane that spins and fires projectiles.
 * This version of the enemy plane has the same functionality as the previous one, 
 * with the added behavior of firing a specific type of projectile, {@link EnemyProjectileV2}.
 */
public class EnemyPlaneV2 extends FighterPlane {

    private static final String IMAGE_NAME = "spacecircle.png";  // Image representing the enemy plane
    private static final int IMAGE_HEIGHT = 150;  // Height of the enemy plane image
    private static final int HORIZONTAL_VELOCITY = -6;  // Horizontal speed of the enemy plane
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;  // X offset for projectile firing position
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;  // Y offset for projectile firing position
    private static final int INITIAL_HEALTH = 2;  // Initial health of the enemy plane
    private static final double FIRE_RATE = 0.01;  // Probability of firing a projectile

    private RotateTransition rotateTransition;  // Rotation animation for the enemy plane

    /**
     * Constructs a new EnemyPlaneV2 with a specified initial position.
     * The enemy plane is initialized with a spinning animation and a health value.
     *
     * @param initialXPos Initial X position of the enemy plane.
     * @param initialYPos Initial Y position of the enemy plane.
     */
    public EnemyPlaneV2(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        initializeSpin();  // Set up the spinning animation for the plane
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
     * @param userPlane The {@link UserPlane} object, used to calculate the projectile's direction.
     * @return The {@link ActiveActorDestructible} object representing the fired projectile,
     *         or null if no projectile is fired.
     */
    @Override
    public ActiveActorDestructible fireProjectile(UserPlane userPlane) {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectileV2(projectileXPosition, projectileYPosition, userPlane);  // Create and return new projectile
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
     * method and triggers additional visual effects related to damage, such as flashing.
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
}
