package com.example.demo.Projectiles;

import com.example.demo.Entities.UserPlane;

/**
 * The EnemyProjectileV2 class represents a more advanced projectile fired by an enemy,
 * which tracks the user's plane and adjusts its trajectory accordingly.
 * It calculates the direction towards the user plane and moves towards it.
 */
public class EnemyProjectileV2 extends Projectile {

    private static final String IMAGE_NAME = "projectile_enemy.png";  // Image for the enemy projectile
    private static final int IMAGE_HEIGHT = 40;                       // Height of the enemy projectile image
    private static final int BASE_HORIZONTAL_VELOCITY = 15;           // Control the speed (magnitude) of the projectile
    private double targetVelocityX;                                   // X component of the target velocity
    private double targetVelocityY;                                   // Y component of the target velocity

    /**
     * Constructor for the EnemyProjectileV2 class.
     * Initializes the projectile and calculates the velocity needed to track the user plane.
     * 
     * @param initialXPos Initial X position of the projectile.
     * @param initialYPos Initial Y position of the projectile.
     * @param userPlane The user plane to calculate the target direction.
     */
    public EnemyProjectileV2(double initialXPos, double initialYPos, UserPlane userPlane) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);  // Call the superclass constructor
        calculateTargetVelocity(userPlane, initialXPos, initialYPos); // Calculate the velocity towards the user plane
    }

    /**
     * Calculates the velocity required for the projectile to move toward the user plane.
     * This method computes the direction vector from the projectile to the user plane and
     * scales it by the horizontal velocity to determine the target velocity.
     *
     * @param userPlane The target user plane to track.
     * @param initialXPos Initial X position of the projectile.
     * @param initialYPos Initial Y position of the projectile.
     */
    private void calculateTargetVelocity(UserPlane userPlane, double initialXPos, double initialYPos) {
        // Get the user's position
        double userX = userPlane.getLayoutX() - userPlane.getTranslateX();
        double userY = userPlane.getLayoutY() + userPlane.getTranslateY();

        // Calculate the difference between the user and the projectile's position
        double deltaX = userX - initialXPos;
        double deltaY = userY - initialYPos;

        // Calculate the magnitude (distance) between the projectile and the user
        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Normalize the direction vector and scale by the base horizontal velocity
        targetVelocityX = (deltaX / magnitude) * BASE_HORIZONTAL_VELOCITY;
        targetVelocityY = (deltaY / magnitude) * BASE_HORIZONTAL_VELOCITY;
    }

    /**
     * Updates the position of the projectile based on its velocity.
     * The projectile will move towards the user plane based on the calculated target velocity.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(targetVelocityX); // Move horizontally towards the user plane
        moveVertically(targetVelocityY);   // Move vertically towards the user plane
    }

    /**
     * Updates the actor by updating its position based on the calculated target velocity.
     * This method calls the updatePosition() method to keep the projectile moving towards the user.
     */
    @Override
    public void updateActor() {
        updatePosition();  // Update position based on the target velocity
    }
}
