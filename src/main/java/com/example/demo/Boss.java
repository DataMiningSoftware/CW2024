package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.demo.Level_Handler.LevelBoss_Interface;
import com.example.demo.Projectiles.ActiveActorDestructible;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 * Represents the Boss entity in the game, inheriting from FighterPlane.
 */
public class Boss extends FighterPlane {

    // Constants for boss properties
    private static final String IMAGE_NAME = "alienXShielded.png";
    private static final double INITIAL_X_POSITION = 1350.0;
    private static final double INITIAL_Y_POSITION = 250.0;
    private static final double PROJECTILE_X_OFFSET = -100.0;
    private static final double PROJECTILE_Y_OFFSET = 75.0;
    private static final double FIRE_RATE = 0.015;
    private static final int IMAGE_HEIGHT = 500;
    private static final int HEALTH = 100;
    private static final int MOVE_SPEED = 10;
    private static final int MOVE_PATTERN_FREQUENCY = 50;
    private static final int MAX_CONSECUTIVE_MOVES = 5;
    private static final int Y_UPPER_BOUND = -100;
    private static final int Y_LOWER_BOUND = 600;

    private final List<Integer> movePattern;
    private int consecutiveMoves;
    private int currentMoveIndex;

    private LevelBoss_Interface levelView;
    private Pane parentPane;

    /**
     * Initializes the Boss entity with default properties.
     */
    public Boss() {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
        this.movePattern = new ArrayList<>();
        this.consecutiveMoves = 0;
        this.currentMoveIndex = 0;
        initializeMovePattern();

        System.out.println("Boss initialized successfully. ParentPane must be set before starting updates.");
    }

    // ----------- Public Methods -----------

    /**
     * Sets the LevelBoss_Interface for interaction with the level.
     *
     * @param levelView The level interface.
     */
    public void setLevelView(LevelBoss_Interface levelView) {
        this.levelView = levelView;
    }

    /**
     * Sets the parent pane for managing the visibility.
     *
     * @param parentPane The parent pane.
     */
    public void setParentPane(Parent parentPane) {
        if (parentPane instanceof Pane) {
            this.parentPane = (Pane) parentPane;
            System.out.println("Parent pane successfully set.");
        } else {
            throw new IllegalArgumentException("Parent must be of type Pane.");
        }
    }

    @Override
    public void updatePosition() {
        double initialTranslateY = getTranslateY();
        moveVertically(getNextMove());

        double currentY = getLayoutY() + getTranslateY();
        if (currentY < Y_UPPER_BOUND || currentY > Y_LOWER_BOUND) {
            setTranslateY(initialTranslateY);
        }
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public ActiveActorDestructible fireProjectile(UserPlane userPlane) {
        if (shouldFireProjectile()) {
            double projectileX = getProjectileXPosition(PROJECTILE_X_OFFSET);
            double projectileY = getProjectileYPosition(PROJECTILE_Y_OFFSET);
            return new BossProjectile(projectileX, projectileY, userPlane);
        }
        return null;
    }

    @Override
    public void takeDamage() {
        super.takeDamage();
        triggerDamageEffect();  // Call to trigger damage effect
    }

    public int getMaxHealth() {
        return HEALTH;
    }

    // ----------- Private Methods -----------

    /**
     * Initializes the movement pattern for the boss.
     */
    private void initializeMovePattern() {
        for (int i = 0; i < MOVE_PATTERN_FREQUENCY; i++) {
            movePattern.add(MOVE_SPEED);
            movePattern.add(-MOVE_SPEED);
            movePattern.add(0);
        }
        Collections.shuffle(movePattern);
    }

    /**
     * Determines whether the boss should fire a projectile.
     *
     * @return True if the boss should fire, false otherwise.
     */
    private boolean shouldFireProjectile() {
        return Math.random() < FIRE_RATE;
    }

    /**
     * Gets the next movement value from the move pattern.
     *
     * @return The vertical movement value.
     */
    private int getNextMove() {
        int move = movePattern.get(currentMoveIndex);
        consecutiveMoves++;

        if (consecutiveMoves >= MAX_CONSECUTIVE_MOVES) {
            Collections.shuffle(movePattern);
            consecutiveMoves = 0;
            currentMoveIndex++;
        }

        if (currentMoveIndex >= movePattern.size()) {
            currentMoveIndex = 0;
        }

        return move;
    }
}
