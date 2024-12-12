package com.example.demo;

import com.example.demo.Projectiles.ActiveActorDestructible;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;

public abstract class FighterPlane extends ActiveActorDestructible {

    private int health;
    private FadeTransition fadeTransition;


    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
    }

    public ActiveActorDestructible fireProjectile(UserPlane userplane) {
		return null;
	}
    
    @Override
    public void takeDamage() {
        health--;
        if (healthAtZero()) {
            this.destroy();
        }
    }
    public void triggerDamageEffect() {
        fadeTransition = new FadeTransition(Duration.seconds(0.1), this);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.2); // Flash red by reducing opacity
        fadeTransition.setCycleCount(2); // Flash twice
        fadeTransition.setInterpolator(Interpolator.LINEAR);
        fadeTransition.setOnFinished(event -> resetOpacity());
        fadeTransition.play();
    }
    
    public void triggerDeathEffect() {
        // Explosion: Scale the enemy up quickly
        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(1), this);
        scaleUp.setFromX(1.0);
        scaleUp.setFromY(1.0);
        scaleUp.setToX(2.0);
        scaleUp.setToY(2.0);
        scaleUp.setInterpolator(Interpolator.EASE_BOTH);

        // Fade out the enemy plane
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), this);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setInterpolator(Interpolator.LINEAR);

        // Rotation: Spin the enemy plane
        RotateTransition rotate = new RotateTransition(Duration.seconds(1.0), this);
        rotate.setByAngle(720);
        rotate.setInterpolator(Interpolator.EASE_BOTH);

        // Color Adjustment for fiery effect
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(1.5);
        colorAdjust.setHue(0.2);  // Shift to red

        // Apply the color effect
        this.setEffect(colorAdjust);

        // Combine all transitions into a ParallelTransition
        ParallelTransition explosionEffect = new ParallelTransition(scaleUp, fadeOut, rotate);
        explosionEffect.setOnFinished(event -> {
            destroy();  // Remove the plane after the effect
            this.setEffect(null);  // Reset the color effect after destruction
        });
        explosionEffect.play();  // Play the combined effect
    }
    private void resetOpacity() {
        setOpacity(1.0);
    }

    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    private boolean healthAtZero() {
        return health == 0;
    }

    public int getHealth() {
        return health;
    }
        
    public void setHealth(int health) {
        this.health = health;
    }
}
