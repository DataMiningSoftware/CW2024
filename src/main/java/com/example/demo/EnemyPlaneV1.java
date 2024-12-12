package com.example.demo;

import com.example.demo.Projectiles.ActiveActorDestructible;
import com.example.demo.Projectiles.EnemyProjectile;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.effect.ColorAdjust;

public class EnemyPlaneV1 extends FighterPlane {

    private static final String IMAGE_NAME = "spacetriangle.png";
    private static final int IMAGE_HEIGHT = 150;
    private static final int HORIZONTAL_VELOCITY = -6;  // Horizontal speed of the enemy plane
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 2;
    private static final double FIRE_RATE = .01;  // Probability of firing a projectile

    private RotateTransition rotateTransition;
    private boolean isDestroyed = false;


    public EnemyPlaneV1(double initialXPos, double initialYPos) {
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

            // Create a new projectile moving straight (without homing to the player)
            return new EnemyProjectile(projectileXPosition, projectileYPosition);
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

    // New Method to Trigger Damage Effect (Flashing Red)

    // Reset the opacity after the damage effect is finished

    public void destroy() {
        if (!isDestroyed) {
            super.destroy(); // Call the parent class's destroy method
            triggerDeathEffect();
            isDestroyed = true; // Mark as destroyed
        }
    }
}
