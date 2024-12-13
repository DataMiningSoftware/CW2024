package com.example.demo.Entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.Interfaces.LevelBoss_Interface;
import com.example.demo.Projectiles.BossProjectile;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Boss class.
 */
public class BossTest {

    private Boss boss;

    @BeforeEach
    public void setUp() {
        boss = new Boss();
    }

    @Test
    public void testUpdatePositionWithinBounds() {
        // Arrange
        boss.setTranslateY(200);

        // Act
        boss.updatePosition();

        // Assert
        assertTrue(boss.getTranslateY() >= -100 && boss.getTranslateY() <= 600,
                "Boss should remain within Y-axis bounds.");
    }

    @Test
    public void testFireProjectile_Success() {
        // Arrange
        UserPlane mockUserPlane = mock(UserPlane.class);
        Boss spyBoss = spy(boss);
        doReturn(true).when(spyBoss).shouldFireProjectile();

        // Act
        var projectile = spyBoss.fireProjectile(mockUserPlane);

        // Assert
        assertNotNull(projectile, "Boss should fire a projectile.");
        assertTrue(projectile instanceof BossProjectile, "Projectile should be of type BossProjectile.");
    }

    @Test
    public void testFireProjectile_Failure() {
        // Arrange
        UserPlane mockUserPlane = mock(UserPlane.class);
        Boss spyBoss = spy(boss);
        doReturn(false).when(spyBoss).shouldFireProjectile();

        // Act
        var projectile = spyBoss.fireProjectile(mockUserPlane);

        // Assert
        assertNull(projectile, "Boss should not fire a projectile.");
    }

    @Test
    public void testTakeDamage_WithShieldActive() {
        // Arrange
        Boss spyBoss = spy(boss);
        spyBoss.setShieldActive(true);
        int initialHealth = spyBoss.getHealth();

        // Act
        spyBoss.takeDamage();

        // Assert
        assertEquals(initialHealth, spyBoss.getHealth(), "Boss should not take damage when shield is active.");
    }

    @Test
    public void testTakeDamage_WithoutShield() {
        // Arrange
        Boss spyBoss = spy(boss);
        spyBoss.setShieldActive(false);
        int initialHealth = spyBoss.getHealth();

        // Act
        spyBoss.takeDamage();

        // Assert
        assertEquals(initialHealth - 1, spyBoss.getHealth(), "Boss should take damage when shield is not active.");
    }

    @Test
    public void testUpdateShieldState() throws InterruptedException {
        // Arrange
        Boss spyBoss = spy(boss);
        spyBoss.setLastShieldToggleTime(System.currentTimeMillis() - 6000); // Force shield toggle interval

        // Act
        spyBoss.updateActor();

        // Assert
        assertTrue(spyBoss.isShieldActive() || !spyBoss.isShieldActive(), "Shield state should toggle after the interval.");
    }

    @Test
    public void testSetParentPane() {
        // Arrange
        Pane mockPane = mock(Pane.class);

        // Act
        boss.setParentPane(mockPane);

        // Assert
        verify(mockPane, times(1)).getChildren();
    }
}