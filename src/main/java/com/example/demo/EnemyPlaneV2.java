package com.example.demo;

import com.example.demo.Projectiles.ActiveActorDestructible;
import com.example.demo.Projectiles.EnemyProjectileV2;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

public class EnemyPlaneV2 extends FighterPlane {

    private static final String IMAGE_NAME = "spacecircle.png";
    private static final int IMAGE_HEIGHT = 150;
    private static final int HORIZONTAL_VELOCITY = -6;  // Horizontal speed of the enemy plane
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 2;
    private static final double FIRE_RATE = .01;  // Probability of firing a projectile

    private RotateTransition rotateTransition;

    public EnemyPlaneV2(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        initializeSpin();
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public ActiveActorDestructible fireProjectile(UserPlane userPlane) {
        if (Math.random() < FIRE_RATE) {
            // Calculate the initial positions of the projectile
        	double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectileV2(projectileXPosition, projectileYPosition, userPlane);
        }
        return null;  // No projectile is fired
    }

    
    @Override
    public void updateActor() {
        updatePosition();
    }
    
    @Override
    public void takeDamage() {
        super.takeDamage();
        triggerDamageEffect();  // Call to trigger damage effect
    }

    // New Method to Initialize Spin
    private void initializeSpin() {
        rotateTransition = new RotateTransition(Duration.seconds(0.5), this); // Rotate every 0.5 seconds
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360); // Full rotation
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE); // Loop indefinitely
        rotateTransition.setInterpolator(Interpolator.LINEAR); // Smooth continuous rotation
        rotateTransition.play(); // Start spinning
    }
}