package com.example.demo.Projectiles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the EnemyProjectile class.
 */
public class EnemyProjectileTest {

    private EnemyProjectile enemyProjectile;

    @BeforeEach
    public void setUp() {
        enemyProjectile = new EnemyProjectile(500, 300); // Create an instance with initial position
    }

    @Test
    public void testUpdatePosition() {
        // Arrange
        double initialX = enemyProjectile.getTranslateX();
        double initialY = enemyProjectile.getTranslateY();

        // Act
        enemyProjectile.updatePosition();

        // Assert
        assertTrue(enemyProjectile.getTranslateX() < initialX, "Projectile should move left.");
        assertEquals(initialY, enemyProjectile.getTranslateY(), "Projectile should not move vertically.");
    }

    @Test
    public void testUpdateActor() {
        // Arrange
        double initialX = enemyProjectile.getTranslateX();
        double initialY = enemyProjectile.getTranslateY();

        // Act
        enemyProjectile.updateActor();

        // Assert
        assertTrue(enemyProjectile.getTranslateX() < initialX, "Actor update should move projectile left.");
        assertEquals(initialY, enemyProjectile.getTranslateY(), "Actor update should not move projectile vertically.");
    }

    @Test
    public void testInitialPosition() {
        // Assert
        assertEquals(500, enemyProjectile.getTranslateX(), "Initial X position should match the constructor.");
        assertEquals(300, enemyProjectile.getTranslateY(), "Initial Y position should match the constructor.");
    }
}
