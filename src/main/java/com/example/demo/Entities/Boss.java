package com.example.demo.Entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.demo.Interfaces.LevelBoss_Interface;
import com.example.demo.Projectiles.BossProjectile;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Represents the Boss entity in the game, inheriting from FighterPlane.
 * The boss has a movement pattern, can toggle its shield, and fire projectiles.
 */
public class Boss extends FighterPlane {

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

    // Shield properties
    private boolean shieldActive;
    private long lastShieldToggleTime;
    private static final int SHIELD_TOGGLE_INTERVAL = 5000; // 5 seconds

    // Shield indicator
    private Rectangle shieldIndicatorBox;
    private Text shieldIndicatorText;

    /**
     * Initializes the Boss entity with default properties, such as movement pattern, health, and shield.
     * The boss's shield is initially inactive, and the movement pattern is randomized.
     */
    public Boss() {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
        this.movePattern = new ArrayList<>();
        this.consecutiveMoves = 0;
        this.currentMoveIndex = 0;
        this.shieldActive = false;
        this.lastShieldToggleTime = System.currentTimeMillis();
        initializeMovePattern();
        initializeShieldIndicator();

        System.out.println("Boss initialized successfully. ParentPane must be set before starting updates.");
    }

    // ----------- Public Methods -----------

    /**
     * Sets the LevelBoss_Interface for interaction with the level.
     *
     * @param levelView The level interface that allows interaction with the boss in the context of the game level.
     */
    public void setLevelView(LevelBoss_Interface levelView) {
        this.levelView = levelView;
    }

    /**
     * Sets the parent pane for managing visibility and interactions with the game scene.
     *
     * @param parentPane The parent pane where the boss and shield indicator will be displayed.
     * @throws IllegalArgumentException If the provided parentPane is not of type Pane.
     */
    public void setParentPane(Parent parentPane) {
        if (parentPane instanceof Pane) {
            this.parentPane = (Pane) parentPane;
            this.parentPane.getChildren().addAll(shieldIndicatorBox, shieldIndicatorText); // Add shield indicator
            System.out.println("Parent pane successfully set.");
        } else {
            throw new IllegalArgumentException("Parent must be of type Pane.");
        }
    }

    /**
     * Updates the boss actor's state, including its position, shield, and other behaviors.
     */
    @Override
    public void updateActor() {
        updatePosition();
        updateShieldState();
    }
    
    /**
     * Updates the boss's position based on its movement pattern, ensuring it doesn't move out of bounds.
     */
    @Override
    public void updatePosition() {
        double initialTranslateY = getTranslateY();
        moveVertically(getNextMove());

        // Check bounds to ensure the boss doesn't move out of the allowed area
        double currentY = getLayoutY() + getTranslateY();
        if (currentY < Y_UPPER_BOUND || currentY > Y_LOWER_BOUND) {
            setTranslateY(initialTranslateY); // Revert to previous position if out of bounds
        }
    }

    /**
     * Fires a projectile at the player's plane if the firing conditions are met.
     *
     * @param userPlane The user's plane, which is the target for the projectile.
     * @return A new BossProjectile if the boss fires, or null if it does not.
     */
    @Override
    public ActiveActorDestructible fireProjectile(UserPlane userPlane) {
        if (shouldFireProjectile()) {
            double projectileX = getProjectileXPosition(PROJECTILE_X_OFFSET);
            double projectileY = getProjectileYPosition(PROJECTILE_Y_OFFSET);
            return new BossProjectile(projectileX, projectileY, userPlane);
        }
        return null;
    }

    /**
     * Reduces the boss's health when it takes damage. If the shield is active, damage is prevented.
     */
    @Override
    public void takeDamage() {
        if (shieldActive) {
            System.out.println("Shield is active! Boss takes no damage.");
            return; // Ignore damage when shield is active
        }
        super.takeDamage();
        triggerDamageEffect();
    }

    /**
     * Gets the maximum health of the boss.
     *
     * @return The maximum health value for the boss.
     */
    public int getMaxHealth() {
        return HEALTH;
    }

    // ----------- Private Methods -----------

    /**
     * Initializes the movement pattern for the boss. The pattern alternates between moving up, down, and staying still.
     * The pattern is shuffled to introduce randomness.
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
     * Determines whether the boss should fire a projectile based on a random chance.
     *
     * @return True if the boss should fire, false otherwise.
     */
    private boolean shouldFireProjectile() {
        return Math.random() < FIRE_RATE;
    }

    /**
     * Gets the next vertical movement value from the boss's move pattern.
     *
     * @return The vertical movement value (positive, negative, or zero).
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

    /**
     * Toggles the shield's state at random intervals, activating or deactivating it.
     */
    private void updateShieldState() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShieldToggleTime > SHIELD_TOGGLE_INTERVAL) {
            shieldActive = !shieldActive; // Toggle shield state
            lastShieldToggleTime = currentTime;
            updateShieldIndicator();
        }
    }

    /**
     * Initializes the visual shield indicator that shows the current state of the shield.
     * This includes a rectangle and text label that will be displayed in the UI.
     */
    private void initializeShieldIndicator() {
        shieldIndicatorBox = new Rectangle(150, 30);
        shieldIndicatorBox.setFill(Color.GRAY);
        shieldIndicatorBox.setOpacity(0.7);
        shieldIndicatorBox.setX(50);
        shieldIndicatorBox.setY(50);

        shieldIndicatorText = new Text("Shield: OFF");
        shieldIndicatorText.setFont(Font.font("Arial", 18));
        shieldIndicatorText.setFill(Color.WHITE);
        shieldIndicatorText.setX(shieldIndicatorBox.getX() + 10);
        shieldIndicatorText.setY(shieldIndicatorBox.getY() + 20);
    }

    /**
     * Updates the shield indicator to reflect the current shield state (ON or OFF).
     */
    private void updateShieldIndicator() {
        if (shieldActive) {
            shieldIndicatorBox.setFill(Color.GREEN);
            shieldIndicatorText.setText("Shield: ON");
        } else {
            shieldIndicatorBox.setFill(Color.RED);
            shieldIndicatorText.setText("Shield: OFF");
        }
    }
}
