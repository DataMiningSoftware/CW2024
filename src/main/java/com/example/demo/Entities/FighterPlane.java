package com.example.demo.Entities;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;

/**
 * Abstract class representing a Fighter Plane, capable of firing projectiles, 
 * taking damage, and triggering visual effects when damaged or destroyed.
 * Subclasses of this class will define the specific behavior for different types 
 * of fighter planes in the game.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

    private int health;  // Health of the fighter plane
    private FadeTransition fadeTransition;  // Transition for the damage effect (flashing)

    /**
     * Constructor to initialize a FighterPlane object with the given properties.
     * 
     * @param imageName     The image name for the plane.
     * @param imageHeight   The height of the image.
     * @param initialXPos   The initial X position of the plane.
     * @param initialYPos   The initial Y position of the plane.
     * @param health        The health of the plane.
     */
    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
    }

    /**
     * Fires a projectile from the plane. This method is intended to be overridden 
     * by subclasses to implement specific projectile firing behavior.
     * 
     * @param userPlane The user-controlled plane.
     * @return A new projectile, or null if none is fired.
     */
    public ActiveActorDestructible fireProjectile(UserPlane userPlane) {
        return null;  // Default implementation, can be overridden by subclasses.
    }

    /**
     * Reduces the health of the plane when it takes damage. If health reaches zero, 
     * the plane is destroyed.
     */
    @Override
    public void takeDamage() {
        health--;  // Decrease health by one
        if (isHealthAtZero()) {
            destroy();  // Destroy the plane if health reaches zero
        }
    }

    /**
     * Triggers a visual damage effect, such as flashing the plane to indicate damage.
     * The effect reduces the opacity of the plane and flashes it.
     */
    public void triggerDamageEffect() {
        fadeTransition = createFadeTransition();  // Create a fade transition effect
        fadeTransition.setOnFinished(event -> resetOpacity());  // Reset opacity after effect
        fadeTransition.play();  // Play the fade transition
    }

    /**
     * Triggers a visual death effect, including an explosion with scaling, fading, and rotation.
     * The plane is removed after the explosion effect finishes.
     */
    public void triggerDeathEffect() {
        ParallelTransition explosionEffect = createExplosionEffect();  // Create the explosion effect
        explosionEffect.setOnFinished(event -> {
            destroy();  // Remove the plane after the effect
            setEffect(null);  // Reset the color effect after destruction
        });
        explosionEffect.play();  // Play the combined explosion effect
    }

    /**
     * Resets the opacity of the plane to full visibility (opacity = 1.0).
     */
    private void resetOpacity() {
        setOpacity(1.0);
    }

    /**
     * Calculates the X position for a projectile based on the plane's current position and an offset.
     * 
     * @param xPositionOffset The X position offset.
     * @return The calculated X position.
     */
    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    /**
     * Calculates the Y position for a projectile based on the plane's current position and an offset.
     * 
     * @param yPositionOffset The Y position offset.
     * @return The calculated Y position.
     */
    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    /**
     * Checks if the plane's health has reached zero.
     * 
     * @return True if health is zero, otherwise false.
     */
    private boolean isHealthAtZero() {
        return health == 0;
    }

    // Getter and Setter for health
    /**
     * Gets the current health of the plane.
     * 
     * @return The current health of the plane.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the plane to the specified value.
     * 
     * @param health The health value to set.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Creates a fade transition effect for damage. The plane's opacity is reduced 
     * and flashes twice to indicate the damage.
     * 
     * @return A configured FadeTransition for the damage effect.
     */
    private FadeTransition createFadeTransition() {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.1), this);
        fade.setFromValue(1.0);  // Start with full opacity
        fade.setToValue(0.2);  // Reduce opacity to 0.2 for flashing effect
        fade.setCycleCount(2);  // Flash twice
        fade.setInterpolator(Interpolator.LINEAR);  // Linear transition for smooth effect
        return fade;
    }

    /**
     * Creates a combined explosion effect with scaling, fading, and rotation.
     * The explosion enlarges the plane, fades it out, rotates it, and applies a color shift 
     * to simulate an explosion.
     * 
     * @return A ParallelTransition containing all the effects for the explosion.
     */
    private ParallelTransition createExplosionEffect() {
        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(1), this);
        scaleUp.setFromX(1.0);
        scaleUp.setFromY(1.0);
        scaleUp.setToX(2.0);  // Scale up to double the size
        scaleUp.setToY(2.0);  // Scale up to double the size
        scaleUp.setInterpolator(Interpolator.EASE_BOTH);  // Easing effect for smooth scaling

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), this);
        fadeOut.setFromValue(1.0);  // Start with full opacity
        fadeOut.setToValue(0.0);  // Fade out to transparency
        fadeOut.setInterpolator(Interpolator.LINEAR);

        RotateTransition rotate = new RotateTransition(Duration.seconds(1.0), this);
        rotate.setByAngle(720);  // Full rotation (two complete turns)
        rotate.setInterpolator(Interpolator.EASE_BOTH);  // Smooth rotation

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(1.5);  // Increase saturation to intensify colors
        colorAdjust.setHue(0.2);  // Shift the hue to create a red effect (explosion-like)

        setEffect(colorAdjust);  // Apply the color adjustment effect to simulate explosion

        return new ParallelTransition(scaleUp, fadeOut, rotate);  // Combine all effects into a parallel transition
    }
}
