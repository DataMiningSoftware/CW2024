package com.example.demo.Projectiles;

import com.example.demo.Entities.ActiveActorDestructible;

/**
 * The Projectile class represents a destructible projectile that can be fired by various actors in the game.
 * This class handles the destruction and movement of the projectile.
 */
public abstract class Projectile extends ActiveActorDestructible {

    /**
     * Constructor for the Projectile class.
     * Initializes the projectile with the given image, size, and position.
     *
     * @param imageName Image name for the projectile.
     * @param imageHeight Height of the projectile image.
     * @param initialXPos Initial X position of the projectile.
     * @param initialYPos Initial Y position of the projectile.
     */
    public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);  // Call the superclass constructor to initialize the projectile
    }

    /**
     * Handles the projectile taking damage. This will destroy the projectile when it takes damage.
     * When a projectile is hit, it is immediately destroyed and removed from the game.
     */
    @Override
    public void takeDamage() {
        destroy(); // Destroy the projectile when it takes damage
    }

    /**
     * Abstract method to update the position of the projectile.
     * Subclasses must define how their specific projectiles move.
     */
    @Override
    public abstract void updatePosition();

    /**
     * Set the trajectory of the projectile.
     * This method allows modification of the projectile's trajectory, such as changing its direction based on an angle.
     * The logic for modifying the trajectory can be expanded if needed.
     *
     * @param angle The angle at which the projectile should travel.
     */
    public void setTrajectory(int angle) {
        // Implement trajectory modification logic here (optional for now)
        // Example: Use angle to calculate velocity components.
    }

    /**
     * Updates the actor's position if it is still alive.
     * This method ensures that position updates only happen when the projectile is alive (not destroyed).
     * 
     * @see #isAlive() for checking the projectile's state.
     */
    @Override
    public void updateActor() {
        if (isAlive()) {
            updatePosition(); // Only update position if the projectile is alive
        }
    }
}
