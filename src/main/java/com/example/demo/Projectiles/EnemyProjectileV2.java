package com.example.demo.Projectiles;

import com.example.demo.UserPlane;

public class EnemyProjectileV2 extends Projectile {
	
	private static final String IMAGE_NAME = "projectile_enemy.png";
	private static final int IMAGE_HEIGHT = 40;
	private static final int HORIZONTAL_VELOCITY = -15; // Control the speed
	private double targetVelocityX;
	private double targetVelocityY;

	public EnemyProjectileV2(double initialXPos, double initialYPos, UserPlane userPlane) {
	    super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);

	    // Calculate direction towards the userPlane
	    double userX = userPlane.getLayoutX() - userPlane.getTranslateX();
	    double userY = userPlane.getLayoutY() + userPlane.getTranslateY();

	    double deltaX = userX - initialXPos;
	    double deltaY = userY - initialYPos;
	    double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

	    // Normalize and scale the velocity based on direction
	    targetVelocityX = (deltaX / magnitude) * Math.abs(HORIZONTAL_VELOCITY);
	    targetVelocityY = (deltaY / magnitude) * Math.abs(HORIZONTAL_VELOCITY);
	}

	@Override
	public void updatePosition() {
	    // Update position based on calculated velocities
	    moveHorizontally(targetVelocityX);
	    moveVertically(targetVelocityY);
	}

	@Override
	public void updateActor() {
		updatePosition(); // Keep updating the position
	}
}
