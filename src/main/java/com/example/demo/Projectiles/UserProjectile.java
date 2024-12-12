package com.example.demo.Projectiles;

public class UserProjectile extends Projectile {

    private static final String IMAGE_NAME = "projectile_user.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final int BASE_HORIZONTAL_VELOCITY = 20; // Base horizontal speed of the projectile
    private double horizontalVelocity;
    private double verticalVelocity;

    public UserProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.horizontalVelocity = BASE_HORIZONTAL_VELOCITY; // Default horizontal velocity
        this.verticalVelocity = 0; // Default vertical velocity
    }

    @Override
    public void updatePosition() {
        moveHorizontally(horizontalVelocity);
        moveVertically(verticalVelocity); // You should also implement this in the Projectile class or UserProjectile class
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void setTrajectory(int angle) {
        // Assuming the angle is given in degrees and you want to convert it to velocities
        double radians = Math.toRadians(angle); // Convert angle to radians

        // Basic trigonometry to calculate the velocities based on angle
        this.horizontalVelocity = BASE_HORIZONTAL_VELOCITY * Math.cos(radians);  // Adjust horizontal velocity based on angle
        this.verticalVelocity = BASE_HORIZONTAL_VELOCITY * Math.sin(radians);    // Adjust vertical velocity based on angle
    }
    
    public void moveVertically(double velocity) {
        setTranslateY(getTranslateY() + velocity);  // Adjust Y position based on the vertical velocity
    }
    
}
