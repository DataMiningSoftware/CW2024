package com.example.demo.Entities;

/**
 * Represents an object that can be damaged and destroyed.
 * Any class that implements this interface should define the behavior 
 * for taking damage and being destroyed.
 */
public interface Destructible {

    /**
     * Applies damage to the object. This method is called when the object
     * takes damage, and its state should be modified accordingly.
     */
    void takeDamage();

    /**
     * Destroys the object. This method is called when the object is
     * no longer functional or needs to be removed from the game or system.
     * It should define the actions needed to fully destroy the object.
     */
    void destroy();
}
