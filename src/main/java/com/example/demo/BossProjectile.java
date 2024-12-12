package com.example.demo;

import com.example.demo.Projectiles.Projectile;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BossProjectile extends Projectile {

    private static final String IMAGE_NAME = "projectile_boss.png";
    private static final int IMAGE_HEIGHT = 100;
    private static final int HORIZONTAL_VELOCITY = -25;
    private double targetVelocityX;
    private double targetVelocityY;

    private MediaPlayer projectileSoundPlayer; // MediaPlayer to manage the sound

    public BossProjectile(double initialXPos, double initialYPos, UserPlane userPlane) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);

        // Calculate direction towards the userPlane
        double userX = userPlane.getLayoutX() - userPlane.getTranslateX();
        double userY = userPlane.getLayoutY() + userPlane.getTranslateY();

        double deltaX = userX - initialXPos;
        double deltaY = userY - initialYPos;
        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        System.out.println("deltaX: " + deltaX + ", deltaY: " + deltaY + ", magnitude: " + magnitude);

        // Normalize and scale the velocity
        targetVelocityX = (deltaX / magnitude) * Math.abs(HORIZONTAL_VELOCITY);
        targetVelocityY = (deltaY / magnitude) * Math.abs(HORIZONTAL_VELOCITY);

        // Play the projectile sound when the projectile is created
        playProjectileSound();
    }

    // Method to play the projectile sound
    private void playProjectileSound() {
        String soundFile = "/com/example/demo/sounds/BossProjectileSound.mp3";  // Path to the sound file
        Media sound = new Media(getClass().getResource(soundFile).toExternalForm());
        projectileSoundPlayer = new MediaPlayer(sound);
        projectileSoundPlayer.play();  // Play the sound
    }

    @Override
    public void updatePosition() {
        moveHorizontally(targetVelocityX);
        moveVertically(targetVelocityY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}
