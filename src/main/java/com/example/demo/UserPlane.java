package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import com.example.demo.Level_Handler.Scene_Properties;
import com.example.demo.Projectiles.ActiveActorDestructible;
import com.example.demo.Projectiles.UserProjectile;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class UserPlane extends FighterPlane {

    // Existing constants and variables
    private static final String IMAGE_NAME = "userplaneP.png";
    private static final double Y_UPPER_BOUND = -40;
    private static final double Y_LOWER_BOUND = 800.0;
    private static final double X_UPPER_BOUND = 800.0;
    private static final double X_LOWER_BOUND = 0.0; // Corrected to 0 for the left boundary
    private static final double INITIAL_X_POSITION = 5.0;
    private static final double INITIAL_Y_POSITION = 300.0;
    private static final int IMAGE_HEIGHT = 200;
    private static final int PROJECTILE_Y_POSITION_OFFSET = 350; // where the mini gun is
    public static final double SCREEN_WIDTH = 800;  // Example width
    public static final double SCREEN_HEIGHT = 600; // Example height

    // Gun sounds
    private static final String PISTOL_SOUND = "/com/example/demo/sounds/ProjectileSound4.mp3";
    private static final String SHOTGUN_SOUND = "/com/example/demo/sounds/ProjectileSound3.mp3";
    private static final String MINIGUN_SOUND = "/com/example/demo/sounds/ProjectileSound2.mp3";

    private static final double MAX_VERTICAL_VELOCITY = 10.0;
    private static final double MAX_HORIZONTAL_VELOCITY = 10.0;

    private static final double ACCELERATION = 0.5; 
    private static final double DECELERATION = 0.2;

    private double horizontalVelocity = 0; 
    private double verticalVelocity = 0; 
    private int numberOfKills;

    private boolean moveLeftPressed = false;
    private boolean moveRightPressed = false;
    private boolean moveUpPressed = false;
    private boolean moveDownPressed = false;
    
    private static final long PISTOL_COOLDOWN = 500; // 500ms cooldown for pistol
    private static final long SHOTGUN_COOLDOWN = 1000; // 1000ms cooldown for shotgun
    private static final long MINIGUN_COOLDOWN = 50; // 50ms cooldown for minigun (cooldown for lag purposes)

    private long lastFiredTimePistol = 0; 
    private long lastFiredTimeShotgun = 0; 
    private long lastFiredTimeMinigun = 0; 
    
    GunDisplay gunDisplay = new GunDisplay();
    
    public enum GunType {
        PISTOL,
        SHOTGUN,
        MINIGUN;
    }
    
    private GunType currentGunType = GunType.PISTOL; // Default weapon

    public UserPlane(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
    }

    @Override
    public void updatePosition() {
        updateVerticalMovement();
        updateHorizontalMovement();

        double initialTranslateY = getTranslateY();
        this.moveVertically(verticalVelocity);
        double newYPosition = getLayoutY() + getTranslateY();

        if (newYPosition < Y_UPPER_BOUND || newYPosition > Y_LOWER_BOUND) {
            this.setTranslateY(initialTranslateY);
            verticalVelocity = 0;
        }

        double initialTranslateX = getTranslateX();
        this.moveHorizontally(horizontalVelocity);
        if (getTranslateX() > Scene_Properties.SCREEN_WIDTH || getTranslateY() > Scene_Properties.SCREEN_HEIGHT || getTranslateY() < 0) {
            setAlive(false); 
        }
        double newXPosition = getLayoutX() + getTranslateX();

        if (newXPosition < X_LOWER_BOUND || newXPosition > X_UPPER_BOUND) {
            this.setTranslateX(initialTranslateX);
            horizontalVelocity = 0;
        }
    }

    private void updateVerticalMovement() {
        if (moveUpPressed) {
            verticalVelocity = Math.max(verticalVelocity - ACCELERATION, -MAX_VERTICAL_VELOCITY);
        } 
        if (moveDownPressed) {
            verticalVelocity = Math.min(verticalVelocity + ACCELERATION, MAX_VERTICAL_VELOCITY);
        } 
        if (!moveUpPressed && !moveDownPressed) {
            verticalVelocity = applyDeceleration(verticalVelocity); 
        }
    }

    private void updateHorizontalMovement() {
        if (moveLeftPressed) {
            horizontalVelocity = Math.max(horizontalVelocity - ACCELERATION, -MAX_HORIZONTAL_VELOCITY);
        } 
        if (moveRightPressed) {
            horizontalVelocity = Math.min(horizontalVelocity + ACCELERATION, MAX_HORIZONTAL_VELOCITY);
        } 
        if (!moveLeftPressed && !moveRightPressed) {
            horizontalVelocity = applyDeceleration(horizontalVelocity); 
        }
    }

    private double applyDeceleration(double velocity) {
        if (velocity > 0) {
            velocity = Math.max(velocity - DECELERATION, 0); 
        } else if (velocity < 0) {
            velocity = Math.min(velocity + DECELERATION, 0); 
        }
        return velocity;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    public List<ActiveActorDestructible> fireProjectile() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        
        long currentTime = System.currentTimeMillis();

        switch (currentGunType) {
        case PISTOL:
            if (currentTime - lastFiredTimePistol >= PISTOL_COOLDOWN) {
                projectiles.add(firePistol());
                playSound(PISTOL_SOUND);  // Play pistol shot sound
                lastFiredTimePistol = currentTime; 
            }
            break;
        case SHOTGUN:
            if (currentTime - lastFiredTimeShotgun >= SHOTGUN_COOLDOWN) {
                projectiles.addAll(fireShotgun());
                playSound(SHOTGUN_SOUND);  // Play shotgun shot sound
                lastFiredTimeShotgun = currentTime; 
            }
            break;
        case MINIGUN:
            if (currentTime - lastFiredTimeMinigun >= MINIGUN_COOLDOWN) {
                projectiles.addAll(fireMinigun());
                playSound(MINIGUN_SOUND);  // Play minigun shot sound
                lastFiredTimeMinigun = currentTime; 
            }
            break;
    }
    return projectiles;
}

 // Override the original method
    @Override
    public ActiveActorDestructible fireProjectile(UserPlane userPlane) {
        return firePistol(); // Default to pistol firing behavior for single projectile
    }
    
    public void moveLeft() {
        moveLeftPressed = true;
    }

    public void moveRight() {
        moveRightPressed = true;
    }

    public void moveUp() {
        moveUpPressed = true;
    }

    public void moveDown() {
        moveDownPressed = true;
    }

    public void stopLeft() {
        moveLeftPressed = false;
    }

    public void stopRight() {
        moveRightPressed = false;
    }

    public void stopUp() {
        moveUpPressed = false;
    }

    public void stopDown() {
        moveDownPressed = false;
    }

    public void stop() {
        stopLeft();
        stopRight();
        stopUp();
        stopDown();
    }

    public int getNumberOfKills() {
        return numberOfKills;
    }

    public void incrementKillCount() {
        numberOfKills++;
    }
    
    private ActiveActorDestructible firePistol() {
        double projectileXPosition = getTranslateX() + (getBoundsInLocal().getWidth());
        double projectileYPosition = getTranslateY() + PROJECTILE_Y_POSITION_OFFSET + (getBoundsInLocal().getHeight() / 2);
        return new UserProjectile(projectileXPosition, projectileYPosition);
    }

    private List<ActiveActorDestructible> fireShotgun() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        double projectileXPosition = getTranslateX() + (getBoundsInLocal().getWidth());
        double baseProjectileYPosition = getTranslateY() + PROJECTILE_Y_POSITION_OFFSET + (getBoundsInLocal().getHeight() / 2);

        for (int angle : new int[]{-10, 0, 10}) {
            UserProjectile projectile = new UserProjectile(projectileXPosition, baseProjectileYPosition);
            projectile.setTrajectory(angle); 
            projectiles.add(projectile);
        }
        return projectiles;
    }

    private List<ActiveActorDestructible> fireMinigun() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        double projectileXPosition = getTranslateX() + (getBoundsInLocal().getWidth());
        double projectileYPosition = getTranslateY() + PROJECTILE_Y_POSITION_OFFSET + (getBoundsInLocal().getHeight() / 2);

        for (int i = 0; i < 5; i++) { 
            projectiles.add(new UserProjectile(projectileXPosition, projectileYPosition));
        }
        return projectiles;
    }

    private void playSound(String soundFilePath) {
        try {
            String path = getClass().getResource(soundFilePath).toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    public void setGunType(GunType gunType) {
        this.currentGunType = gunType;
    }

    public GunType getGunType() {
        return currentGunType;
    }
    
    public void switchGun() {
        switch (currentGunType) {
            case PISTOL:
                gunDisplay.updateGunDisplay("Pistol");
                break;
            case SHOTGUN:
                gunDisplay.updateGunDisplay("Shotgun");
                break;
            case MINIGUN:
                gunDisplay.updateGunDisplay("Minigun");
                break;
        }
    }
}
