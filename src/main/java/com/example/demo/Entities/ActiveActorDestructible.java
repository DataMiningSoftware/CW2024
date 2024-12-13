package com.example.demo.Entities;

/**
 * The ActiveActorDestructible class extends the ActiveActor class and implements the Destructible interface.
 * This class represents an actor that can be actively moved and also can be destroyed.
 * It provides methods for updating the actor's state, taking damage, and marking the actor as destroyed.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

    private boolean isDestroyed; // Flag to track if the actor is destroyed
    private boolean alive;       // Flag to track if the actor is still alive or has been destroyed

    /**
     * Constructor for ActiveActorDestructible, initializes the actor's image, position, and destruction state.
     * 
     * @param imageName   The name of the image file to be used for the actor.
     * @param imageHeight The height of the actor's image.
     * @param initialXPos The initial X position of the actor in the scene.
     * @param initialYPos The initial Y position of the actor in the scene.
     */
    public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos); // Call the superclass constructor
        this.isDestroyed = false; // Initialize as not destroyed
        this.alive = true;        // Initialize as alive
    }

    /**
     * Abstract method to update the position of the actor.
     * This method must be implemented by subclasses to define specific movement behavior.
     */
    @Override
    public abstract void updatePosition();

    /**
     * Abstract method to update the actor's state.
     * This method must be implemented by subclasses to define specific logic for state changes (e.g., animations or interactions).
     */
    public abstract void updateActor();

    /**
     * Abstract method for taking damage.
     * This method must be implemented by subclasses to define how the actor responds to damage (e.g., reduce health, play damage animation).
     */
    @Override
    public abstract void takeDamage();

    /**
     * Marks the actor as destroyed and updates the alive status to false.
     * This method is used to destroy the actor in the game, for example, after it takes fatal damage.
     */
    @Override
    public void destroy() {
        setDestroyed(true); // Mark the actor as destroyed
        setAlive(false);    // Set the actor's alive status to false
    }

    /**
     * Sets the destruction status of the actor.
     * 
     * @param isDestroyed True if the actor is destroyed, false otherwise.
     */
    protected void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed; // Set the destruction status of the actor
    }

    /**
     * Checks if the actor is destroyed.
     * 
     * @return True if the actor is destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return isDestroyed; // Return the destruction status of the actor
    }

    /**
     * Checks if the actor is still alive.
     * 
     * @return True if the actor is alive, false if it's destroyed.
     */
    public boolean isAlive() {
        return alive; // Return the alive status of the actor
    }

    /**
     * Sets the alive status of the actor.
     * 
     * @param alive True if the actor is alive, false if it's destroyed.
     */
    public void setAlive(boolean alive) {
        this.alive = alive; // Set the alive status of the actor
    }
}
