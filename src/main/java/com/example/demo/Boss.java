package com.example.demo;

import java.util.*;

public class Boss extends FighterPlane {

    private static final String IMAGE_NAME = "alienX.png";
    private static final double INITIAL_X_POSITION = 1350.0;
    private static final double INITIAL_Y_POSITION = 250;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
    private static final double BOSS_FIRE_RATE = 0.01;
    private static final double BOSS_SHIELD_PROBABILITY = .002;
    private static final int IMAGE_HEIGHT = 500;
    private static final int VERTICAL_VELOCITY = 10;
    private static final int HEALTH = 100;
    private static final int MOVE_FREQUENCY_PER_CYCLE = 50;
    private static final int ZERO = 0;
    private static final int MAX_FRAMES_WITH_SAME_MOVE = 5;
    private static final int Y_POSITION_UPPER_BOUND = -200;
    private static final int Y_POSITION_LOWER_BOUND = 600;
    private static final int MAX_FRAMES_WITH_SHIELD = 500;
    
    private Level2_Interface levelView;
    
    private final List<Integer> movePattern;
    private boolean isShielded;
    private int consecutiveMovesInSameDirection;
    private int indexOfCurrentMove;
    private int framesWithShieldActivated;

    public Boss() {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
        movePattern = new ArrayList<>();
        consecutiveMovesInSameDirection = 0;
        indexOfCurrentMove = 0;
        framesWithShieldActivated = 0;
        isShielded = false;
        initializeMovePattern();
    }

    public void setLevelView(Level2_Interface levelView) {
        this.levelView = levelView;  // Set the level view so we can update the health bar (if needed later)
    }

    @Override
    public void updatePosition() {
        double initialTranslateY = getTranslateY();
        moveVertically(getNextMove());
        double currentPosition = getLayoutY() + getTranslateY();
        if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
            setTranslateY(initialTranslateY);
        }
    }

    @Override
    public void updateActor() {
        updatePosition();
        updateShield();
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        List<BossProjectile> projectiles = new ArrayList<>();
        if (bossFiresInCurrentFrame()) {
            // Create projectiles at different Y positions
            projectiles.add(new BossProjectile(getProjectileInitialPosition() - 50));
            projectiles.add(new BossProjectile(getProjectileInitialPosition()));
            projectiles.add(new BossProjectile(getProjectileInitialPosition() + 50));
        }
        return projectiles.isEmpty() ? null : projectiles.get(0);  // Return the first projectile for now
    }

    @Override
    public void takeDamage() {
        if (!isShielded) {
            super.takeDamage();
        }
    }

    private void initializeMovePattern() {
        for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(VERTICAL_VELOCITY);
            movePattern.add(-VERTICAL_VELOCITY);
            movePattern.add(ZERO);
        }
        Collections.shuffle(movePattern);
    }

    private void updateShield() {
        if (isShielded) {
            framesWithShieldActivated++;
        } else if (shieldShouldBeActivated()) {
            activateShield();
        }
        if (shieldExhausted()) {
            deactivateShield();
        }
    }

    private int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;
        if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
            Collections.shuffle(movePattern);
            consecutiveMovesInSameDirection = 0;
            indexOfCurrentMove++;
        }
        if (indexOfCurrentMove == movePattern.size()) {
            indexOfCurrentMove = 0;
        }
        return currentMove;
    }

    private boolean bossFiresInCurrentFrame() {
        return Math.random() < BOSS_FIRE_RATE;
    }

    private double getProjectileInitialPosition() {
        return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
    }

    private boolean shieldShouldBeActivated() {
        return Math.random() < BOSS_SHIELD_PROBABILITY;
    }

    private boolean shieldExhausted() {
        return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
    }

    private void activateShield() {
        isShielded = true;
    }

    private void deactivateShield() {
        isShielded = false;
        framesWithShieldActivated = 0;
    }

    public int getMaxHealth() {
        return HEALTH;
    }
}
