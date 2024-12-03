package com.example.demo;

public class UserPlane extends FighterPlane {

    private static final String IMAGE_NAME = "spacecircle.png";
    private static final double Y_UPPER_BOUND = -40;
    private static final double Y_LOWER_BOUND = 800.0;
    private static final double INITIAL_X_POSITION = 5.0;
    private static final double INITIAL_Y_POSITION = 300.0;
    private static final int IMAGE_HEIGHT = 200;

    private static final double MAX_VERTICAL_VELOCITY = 10.0; // Maximum velocity for smooth movement
    private static final int PROJECTILE_X_POSITION = 110;
    private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

    private double verticalVelocity = 0;  // Current vertical velocity
    private int numberOfKills;

    public UserPlane(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
    }

    @Override
    public void updatePosition() {
        // Update position based on the current vertical velocity
        double initialTranslateY = getTranslateY();
        this.moveVertically(verticalVelocity);
        double newPosition = getLayoutY() + getTranslateY();
        
        // Prevent the plane from moving out of bounds
        if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
            this.setTranslateY(initialTranslateY);
            verticalVelocity = 0; // Stop movement if boundary is hit
        }
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
    }

    public void moveUp() {
        verticalVelocity = -MAX_VERTICAL_VELOCITY; // Set velocity upward
    }

    public void moveDown() {
        verticalVelocity = MAX_VERTICAL_VELOCITY; // Set velocity downward
    }

    public void stop() {
        verticalVelocity = 0; // Stop immediately when key is released
    }

    public int getNumberOfKills() {
        return numberOfKills;
    }

    public void incrementKillCount() {
        numberOfKills++;
    }
}
