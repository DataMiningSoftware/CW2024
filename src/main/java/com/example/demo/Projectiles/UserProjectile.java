package com.example.demo.Projectiles;

/**
 * The UserProjectile class represents a projectile fired by the user in the game.
 * It handles the movement, trajectory, and velocity of the projectile.
 */
public class UserProjectile extends Projectile {

    private static final String IMAGE_NAME = "projectile_user.png"; // Image name for the user projectile
    private static final int IMAGE_HEIGHT = 50; // Height of the user projectile image
    private static final int BASE_HORIZONTAL_VELOCITY = 20; // Base horizontal speed of the projectile
    private double horizontalVelocity; // Horizontal velocity of the projectile
    private double verticalVelocity;   // Vertical velocity of the projectile

    /**
     * Constructor for creating a UserProjectile.
     * Initializes the projectile with the specified position and default velocities.
     *
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     */
    public UserProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos); // Call to parent class constructor to initialize the projectile
        this.horizontalVelocity = BASE_HORIZONTAL_VELOCITY; // Set default horizontal velocity
        this.verticalVelocity = 0; // Set default vertical velocity (straight line)
    }

    /**
     * Updates the position of the projectile based on its horizontal and vertical velocities.
     * The horizontal velocity moves the projectile along the X axis,
     * and the vertical velocity (if non-zero) moves the projectile along the Y axis.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(horizontalVelocity); // Move horizontally based on calculated velocity
        moveVertically(verticalVelocity);     // Move vertically (0 by default)
    }

    /**
     * Updates the actor's position if the projectile is alive.
     * This method ensures that the position of the projectile is updated only if it is still alive.
     */
    @Override
    public void updateActor() {
        updatePosition(); // Continually update position if the projectile is active
    }

    /**
     * Sets the trajectory of the projectile based on the given angle.
     * This method converts the angle into horizontal and vertical velocities,
     * allowing the projectile to move in the direction specified by the angle.
     *
     * @param angle The angle (in degrees) to set the projectile's trajectory.
     *              A 0-degree angle will result in the projectile moving horizontally to the right.
     */
    @Override
    public void setTrajectory(int angle) {
        double radians = Math.toRadians(angle); // Convert angle to radians

        // Calculate horizontal and vertical velocities using trigonometry
        this.horizontalVelocity = BASE_HORIZONTAL_VELOCITY * Math.cos(radians); // Adjust horizontal velocity based on angle
        this.verticalVelocity = BASE_HORIZONTAL_VELOCITY * Math.sin(radians);   // Adjust vertical velocity based on angle
    }

    /**
     * Moves the projectile vertically based on its velocity.
     * This method directly modifies the Y-coordinate of the projectile.
     *
     * @param velocity The vertical velocity to move the projectile.
     */
    public void moveVertically(double velocity) {
        setTranslateY(getTranslateY() + velocity); // Move the projectile vertically
    }
}
