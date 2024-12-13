package com.example.demo.Entities;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Interfaces.GunDisplay;
import com.example.demo.Projectiles.UserProjectile;
import com.example.demo.Scene_Manipulator.Scene_Properties;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Represents a user-controlled fighter plane in the game.
 * The plane can move, shoot projectiles, and interact with other game elements.
 */
public class UserPlane extends FighterPlane {

    // Constants for initial settings and bounds
    private static final String IMAGE_NAME = "userplaneP.png";
    private static final double Y_UPPER_BOUND = -40;
    private static final double Y_LOWER_BOUND = 800.0;
    private static final double X_UPPER_BOUND = 800.0;
    private static final double X_LOWER_BOUND = 0.0;
    private static final double INITIAL_X_POSITION = 5.0;
    public static final double INITIAL_Y_POSITION = 300.0;
    private static final int IMAGE_HEIGHT = 200;
    private static final int PROJECTILE_Y_POSITION_OFFSET = 350;
    public static final double SCREEN_WIDTH = 800;
    public static final double SCREEN_HEIGHT = 600;

    // Gun sound files
    private static final String PISTOL_SOUND = "/com/example/demo/sounds/ProjectileSound4.mp3";
    private static final String SHOTGUN_SOUND = "/com/example/demo/sounds/ProjectileSound3.mp3";
    private static final String MINIGUN_SOUND = "/com/example/demo/sounds/ProjectileSound2.mp3";

    // Movement and velocity constants
    private static final double MAX_VERTICAL_VELOCITY = 10.0;
    private static final double MAX_HORIZONTAL_VELOCITY = 10.0;
    private static final double ACCELERATION = 0.5;
    private static final double DECELERATION = 0.2;

    // Gun cooldown times (in milliseconds)
    private static final long PISTOL_COOLDOWN = 500;
    private static final long SHOTGUN_COOLDOWN = 1000;
    private static final long MINIGUN_COOLDOWN = 50;

    // Instance variables
    private double horizontalVelocity = 0;
    private double verticalVelocity = 0;
    private int numberOfKills;
    private boolean moveLeftPressed = false;
    private boolean moveRightPressed = false;
    private boolean moveUpPressed = false;
    private boolean moveDownPressed = false;

    private long lastFiredTimePistol = 0;
    private long lastFiredTimeShotgun = 0;
    private long lastFiredTimeMinigun = 0;

    private GunType currentGunType = GunType.PISTOL;
    public GunDisplay gunDisplay = new GunDisplay();

    // Enum for different gun types
    public enum GunType {
        PISTOL, SHOTGUN, MINIGUN;
    }

    /**
     * Constructs a UserPlane object with specified initial health.
     *
     * @param initialHealth The initial health of the plane.
     */
    public UserPlane(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
    }

    /**
     * Updates the position of the plane based on user input.
     * This method is called every frame to keep the plane's movement up to date.
     */
    @Override
    public void updatePosition() {
        updateVerticalMovement();
        updateHorizontalMovement();
        enforceBounds();
    }

    /**
     * Ensures the plane stays within the screen boundaries.
     * If the plane goes outside the screen, its position is reset.
     */
    private void enforceBounds() {
        // Enforce Y-axis bounds
        double initialTranslateY = getTranslateY();
        moveVertically(verticalVelocity);
        if (getLayoutY() + getTranslateY() < Y_UPPER_BOUND || getLayoutY() + getTranslateY() > Y_LOWER_BOUND) {
            setTranslateY(initialTranslateY);  // Reset position if out of bounds
            verticalVelocity = 0;
        }

        // Enforce X-axis bounds
        double initialTranslateX = getTranslateX();
        moveHorizontally(horizontalVelocity);
        if (getLayoutX() + getTranslateX() < X_LOWER_BOUND || getLayoutX() + getTranslateX() > X_UPPER_BOUND) {
            setTranslateX(initialTranslateX);  // Reset position if out of bounds
            horizontalVelocity = 0;
        }

        // Check if the user plane is out of the screen
        if (getTranslateX() > Scene_Properties.SCREEN_WIDTH || getTranslateY() > Scene_Properties.SCREEN_HEIGHT || getTranslateY() < 0) {
            setAlive(false);  // Mark the plane as not alive if it goes off-screen
        }
    }

    /**
     * Updates the vertical movement based on user input.
     * The plane accelerates or decelerates depending on user input and the current velocity.
     */
    private void updateVerticalMovement() {
        if (moveUpPressed) {
            verticalVelocity = Math.max(verticalVelocity - ACCELERATION, -MAX_VERTICAL_VELOCITY);  // Move up
        } else if (moveDownPressed) {
            verticalVelocity = Math.min(verticalVelocity + ACCELERATION, MAX_VERTICAL_VELOCITY);  // Move down
        } else {
            verticalVelocity = applyDeceleration(verticalVelocity);  // Apply deceleration if no movement
        }
    }

    /**
     * Updates the horizontal movement based on user input.
     * The plane accelerates or decelerates depending on user input and the current velocity.
     */
    private void updateHorizontalMovement() {
        if (moveLeftPressed) {
            horizontalVelocity = Math.max(horizontalVelocity - ACCELERATION, -MAX_HORIZONTAL_VELOCITY);  // Move left
        } else if (moveRightPressed) {
            horizontalVelocity = Math.min(horizontalVelocity + ACCELERATION, MAX_HORIZONTAL_VELOCITY);  // Move right
        } else {
            horizontalVelocity = applyDeceleration(horizontalVelocity);  // Apply deceleration if no movement
        }
    }

    /**
     * Applies deceleration to the given velocity.
     * This method is used to gradually slow down movement when no key is pressed.
     *
     * @param velocity The current velocity of the plane.
     * @return The new velocity after applying deceleration.
     */
    private double applyDeceleration(double velocity) {
        return (velocity > 0) ? Math.max(velocity - DECELERATION, 0) : Math.min(velocity + DECELERATION, 0);
    }

    /**
     * Updates the plane's actor every frame.
     * This method calls updatePosition to keep the plane's movement in sync.
     */
    @Override
    public void updateActor() {
        updatePosition();  // Update position every time the actor is updated
    }

    /**
     * Fires a projectile based on the current gun type.
     * The projectile is fired only if enough time has passed since the last shot (based on the cooldown).
     *
     * @return A list of projectiles fired by the plane.
     */
    public List<ActiveActorDestructible> fireProjectile() {
        long currentTime = System.currentTimeMillis();
        List<ActiveActorDestructible> projectiles = new ArrayList<>();

        // Switch between guns based on the cooldown period
        switch (currentGunType) {
            case PISTOL:
                if (currentTime - lastFiredTimePistol >= PISTOL_COOLDOWN) {
                    projectiles.add(firePistol());
                    playSound(PISTOL_SOUND);
                    lastFiredTimePistol = currentTime;
                }
                break;
            case SHOTGUN:
                if (currentTime - lastFiredTimeShotgun >= SHOTGUN_COOLDOWN) {
                    projectiles.addAll(fireShotgun());
                    playSound(SHOTGUN_SOUND);
                    lastFiredTimeShotgun = currentTime;
                }
                break;
            case MINIGUN:
                if (currentTime - lastFiredTimeMinigun >= MINIGUN_COOLDOWN) {
                    projectiles.addAll(fireMinigun());
                    playSound(MINIGUN_SOUND);
                    lastFiredTimeMinigun = currentTime;
                }
                break;
        }
        return projectiles;
    }

    /**
     * Fires the plane's pistol and returns the projectile created.
     *
     * @return The projectile fired by the plane.
     */
    private ActiveActorDestructible firePistol() {
        double projectileXPosition = getTranslateX() + getBoundsInLocal().getWidth();
        double projectileYPosition = getTranslateY() + PROJECTILE_Y_POSITION_OFFSET + getBoundsInLocal().getHeight() / 2;
        return new UserProjectile(projectileXPosition, projectileYPosition);
    }

    /**
     * Fires the plane's shotgun and returns the projectiles created.
     * The shotgun fires multiple projectiles at different angles.
     *
     * @return A list of projectiles fired by the plane.
     */
    private List<ActiveActorDestructible> fireShotgun() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        double projectileXPosition = getTranslateX() + getBoundsInLocal().getWidth();
        double baseProjectileYPosition = getTranslateY() + PROJECTILE_Y_POSITION_OFFSET + getBoundsInLocal().getHeight() / 2;

        // Fire 3 projectiles at different angles
        for (int angle : new int[]{-10, 0, 10}) {
            UserProjectile projectile = new UserProjectile(projectileXPosition, baseProjectileYPosition);
            projectile.setTrajectory(angle);
            projectiles.add(projectile);
        }
        return projectiles;
    }

    /**
     * Fires the plane's minigun and returns the projectiles created.
     * The minigun fires multiple projectiles rapidly.
     *
     * @return A list of projectiles fired by the plane.
     */
    private List<ActiveActorDestructible> fireMinigun() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        double projectileXPosition = getTranslateX() + getBoundsInLocal().getWidth();
        double projectileYPosition = getTranslateY() + PROJECTILE_Y_POSITION_OFFSET + getBoundsInLocal().getHeight() / 2;

        // Fire 5 projectiles rapidly
        for (int i = 0; i < 5; i++) {
            projectiles.add(new UserProjectile(projectileXPosition, projectileYPosition));
        }
        return projectiles;
    }

    /**
     * Plays the sound effect for a projectile firing.
     * The sound played depends on the current gun type.
     *
     * @param soundFilePath The file path of the sound to play.
     */
    public void playSound(String soundFilePath) {
        try {
            String path = getClass().getResource(soundFilePath).toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    /**
     * Sets the current gun type.
     *
     * @param gunType The new gun type.
     */
    public void setGunType(GunType gunType) { currentGunType = gunType; }

    /**
     * Gets the current gun type.
     *
     * @return The current gun type.
     */
    public GunType getGunType() { return currentGunType; }

    /**
     * Switches the plane's gun and updates the display accordingly.
     */
    public void switchGun() {
        switch (currentGunType) {
            case PISTOL: gunDisplay.updateGunDisplay("Pistol"); break;
            case SHOTGUN: gunDisplay.updateGunDisplay("Shotgun"); break;
            case MINIGUN: gunDisplay.updateGunDisplay("Minigun"); break;
        }
    }

    /**
     * Starts moving the plane left.
     */
    public void moveLeft() { moveLeftPressed = true; }

    /**
     * Starts moving the plane right.
     */
    public void moveRight() { moveRightPressed = true; }

    /**
     * Starts moving the plane up.
     */
    public void moveUp() { moveUpPressed = true; }

    /**
     * Starts moving the plane down.
     */
    public void moveDown() { moveDownPressed = true; }

    /**
     * Stops moving the plane left.
     */
    public void stopLeft() { moveLeftPressed = false; }

    /**
     * Stops moving the plane right.
     */
    public void stopRight() { moveRightPressed = false; }

    /**
     * Stops moving the plane up.
     */
    public void stopUp() { moveUpPressed = false; }

    /**
     * Stops moving the plane down.
     */
    public void stopDown() { moveDownPressed = false; }

    /**
     * Stops all movement of the plane.
     */
    public void stop() { stopLeft(); stopRight(); stopUp(); stopDown(); }

    /**
     * Gets the current number of kills the plane has achieved.
     *
     * @return The number of kills.
     */
    public int getNumberOfKills() { return numberOfKills; }

    /**
     * Increments the plane's kill count by 1.
     */
    public void incrementKillCount() { numberOfKills++; }
}
