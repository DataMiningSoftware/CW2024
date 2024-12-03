package com.example.demo;

public class UserPlane extends FighterPlane {

    private static final String IMAGE_NAME = "userplane.png";
    private static final double Y_UPPER_BOUND = -40;
    private static final double Y_LOWER_BOUND = 800.0;
    private static final double X_UPPER_BOUND = 800.0;
    private static final double X_LOWER_BOUND = 0.0;
    private static final double INITIAL_X_POSITION = 5.0;
    private static final double INITIAL_Y_POSITION = 300.0;
    private static final int IMAGE_HEIGHT = 200;

    private static final double MAX_VERTICAL_VELOCITY = 300.0; // Velocity in pixels per second
    private static final double MAX_HORIZONTAL_VELOCITY = 300.0; // Velocity in pixels per second
    private static final int PROJECTILE_Y_POSITION_OFFSET = 150;

    private double horizontalVelocity = 0; // Current horizontal velocity
    private double verticalVelocity = 0; // Current vertical velocity
    private int numberOfKills;

    public UserPlane(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
    }

    /**
     * Updates the position of the plane based on velocities and elapsed time.
     * 
     * @param deltaTime Time elapsed since the last frame in seconds.
     */
    public void updatePosition(double deltaTime) {
        // Calculate new positions
        double deltaX = horizontalVelocity * deltaTime;
        double deltaY = verticalVelocity * deltaTime;

        double newXPosition = getLayoutX() + getTranslateX() + deltaX;
        double newYPosition = getLayoutY() + getTranslateY() + deltaY;

        // Check bounds for Y-axis
        if (newYPosition < Y_UPPER_BOUND) {
            setTranslateY(Y_UPPER_BOUND - getLayoutY());
        } else if (newYPosition > Y_LOWER_BOUND) {
            setTranslateY(Y_LOWER_BOUND - getLayoutY());
        } else {
            moveVertically(deltaY);
        }

        // Check bounds for X-axis
        if (newXPosition < X_LOWER_BOUND) {
            setTranslateX(X_LOWER_BOUND - getLayoutX());
        } else if (newXPosition > X_UPPER_BOUND) {
            setTranslateX(X_UPPER_BOUND - getLayoutX());
        } else {
            moveHorizontally(deltaX);
        }
    }

    @Override
    public void updateActor() {
        // `updatePosition` requires deltaTime; pass a value during the game loop
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        double projectileXPosition = getTranslateX() + (getBoundsInLocal().getWidth());
        double projectileYPosition = getTranslateY() + 350 + (getBoundsInLocal().getHeight() / 2);
        return new UserProjectile(projectileXPosition, projectileYPosition);
    }

    // Movement controls
    public void moveLeft() {
        horizontalVelocity = -MAX_HORIZONTAL_VELOCITY;
    }

    public void moveRight() {
        horizontalVelocity = MAX_HORIZONTAL_VELOCITY;
    }

    public void moveUp() {
        verticalVelocity = -MAX_VERTICAL_VELOCITY;
    }

    public void moveDown() {
        verticalVelocity = MAX_VERTICAL_VELOCITY;
    }

    public void stopHorizontal() {
        horizontalVelocity = 0;
    }

    public void stopVertical() {
        verticalVelocity = 0;
    }

    public void stop() {
        stopHorizontal();
        stopVertical();
    }

    // Additional methods
    public int getNumberOfKills() {
        return numberOfKills;
    }

    public void incrementKillCount() {
        numberOfKills++;
    }

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		
	}
}
