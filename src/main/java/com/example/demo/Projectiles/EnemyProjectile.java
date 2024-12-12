package com.example.demo.Projectiles;

public class EnemyProjectile extends Projectile {

    private static final String IMAGE_NAME = "projectile_enemy.png";
    private static final int IMAGE_HEIGHT = 40;
    private static final int HORIZONTAL_VELOCITY = -10;  // Horizontal speed (to the left)
    private static final int VERTICAL_VELOCITY = 0;     // No vertical movement (straight line)

    public EnemyProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition() {
        // Move horizontally with constant velocity
        moveHorizontally(HORIZONTAL_VELOCITY);

        // Optionally, move vertically (you can keep it at 0 for straight horizontal movement)
        moveVertically(VERTICAL_VELOCITY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}
