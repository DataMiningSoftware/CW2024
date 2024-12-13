package com.example.demo.Projectiles;

/**
 * The EnemyProjectile class represents a projectile fired by an enemy.
 * It inherits from the Projectile class and defines specific behavior for the enemy's projectile, 
 * including its image, velocity, and movement.
 */
public class EnemyProjectile extends Projectile {

    private static final String IMAGE_NAME = "projectile_enemy.png"; // Image file for the enemy projectile
    private static final int IMAGE_HEIGHT = 40;                      // Height of the enemy projectile image
    private static final int HORIZONTAL_VELOCITY = -10;              // Horizontal speed (moving to the left)
    private static final int VERTICAL_VELOCITY = 0;                  // No vertical movement (straight line)

    /**
     * Constructor for the EnemyProjectile class.
     * Initializes the projectile's image, height, and position.
     * 
     * @param initialXPos Initial X position of the projectile.
     * @param initialYPos Initial Y position of the projectile.
     */
    public EnemyProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos); // Call the superclass constructor
    }

    /**
     * Updates the position of the projectile based on its velocity.
     * This method moves the projectile horizontally to the left at a constant speed
     * and keeps the vertical position unchanged.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY); // Move horizontally with constant velocity
        moveVertically(VERTICAL_VELOCITY);     // No vertical movement, so it stays the same
    }

    /**
     * Updates the actor by updating its position.
     * This method delegates the actual movement to the updatePosition() method.
     */
    @Override
    public void updateActor() {
        updatePosition();  // Delegate the position update to the updatePosition() method
    }
}
