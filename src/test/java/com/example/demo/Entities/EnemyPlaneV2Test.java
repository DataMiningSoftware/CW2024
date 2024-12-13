package com.example.demo.Entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.Projectiles.EnemyProjectileV2;
import javafx.animation.RotateTransition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the EnemyPlaneV2 class.
 */
public class EnemyPlaneV2Test {

    private EnemyPlaneV2 enemyPlane;

    @BeforeEach
    public void setUp() {
        enemyPlane = new EnemyPlaneV2(500, 300); // Create an instance of EnemyPlaneV2
    }

    @Test
    public void testUpdatePosition() {
        // Arrange
        double initialXPosition = enemyPlane.getTranslateX();

        // Act
        enemyPlane.updatePosition();

        // Assert
        assertTrue(enemyPlane.getTranslateX() < initialXPosition, "EnemyPlaneV2 should move left.");
    }

    @Test
    public void testFireProjectile_Success() {
        // Arrange
        UserPlane mockUserPlane = mock(UserPlane.class);
        // Mock Math.random() to force a successful firing
        EnemyPlaneV2 spyEnemyPlane = spy(enemyPlane);
        doReturn(0.005).when(spyEnemyPlane).fireRateCheck();

        // Act
        var projectile = spyEnemyPlane.fireProjectile(mockUserPlane);

        // Assert
        assertNotNull(projectile, "EnemyPlaneV2 should fire a projectile.");
        assertTrue(projectile instanceof EnemyProjectileV2, "Projectile should be of type EnemyProjectileV2.");
    }

    @Test
    public void testFireProjectile_Failure() {
        // Arrange
        UserPlane mockUserPlane = mock(UserPlane.class);
        // Mock Math.random() to force a firing failure
        EnemyPlaneV2 spyEnemyPlane = spy(enemyPlane);
        doReturn(0.02).when(spyEnemyPlane).fireRateCheck();

        // Act
        var projectile = spyEnemyPlane.fireProjectile(mockUserPlane);

        // Assert
        assertNull(projectile, "EnemyPlaneV2 should not fire a projectile.");
    }

    @Test
    public void testTakeDamage() {
        // Arrange
        int initialHealth = enemyPlane.getHealth();

        // Act
        enemyPlane.takeDamage();

        // Assert
        assertEquals(initialHealth - 1, enemyPlane.getHealth(), "Health should decrease by 1 when damaged.");
    }

    @Test
    public void testInitializeSpin() {
        // Arrange
        EnemyPlaneV2 spyEnemyPlane = spy(enemyPlane);
        spyEnemyPlane.initializeSpin();

        // Assert
        assertNotNull(spyEnemyPlane.rotateTransition, "RotateTransition should be initialized.");
        assertEquals(360, spyEnemyPlane.rotateTransition.getToAngle(), "Rotation should complete a full circle.");
        assertEquals(RotateTransition.INDEFINITE, spyEnemyPlane.rotateTransition.getCycleCount(), "Rotation should be indefinite.");
    }
}
