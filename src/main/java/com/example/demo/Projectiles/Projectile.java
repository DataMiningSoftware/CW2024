package com.example.demo.Projectiles;

public abstract class Projectile extends ActiveActorDestructible {

    public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);
    }

    @Override
    public void takeDamage() {
        destroy(); // If the projectile takes damage, it should be destroyed
    }

    @Override
    public abstract void updatePosition(); // Each specific projectile needs to define how it moves

    public void setTrajectory(int angle) {
        // This method can be used to modify the trajectory of the projectile based on the angle.
        // Add your logic for trajectory calculation here.
    }

    @Override
    public void updateActor() {
        if (isAlive()) {
            updatePosition(); // Only update the position if the projectile is alive
        }
    }
}