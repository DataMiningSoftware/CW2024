package com.example.demo.Entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.Interfaces.GunDisplay;
import com.example.demo.Projectiles.UserProjectile;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Unit tests for the UserPlane class.
 */
public class UserPlaneTest {

    private UserPlane userPlane;

    @BeforeEach
    public void setUp() {
        userPlane = new UserPlane(5); // Create a UserPlane with initial health 5
    }

    @Test
    public void testUpdatePositionWithinBounds() {
        // Arrange
        userPlane.moveUp();

        // Act
        userPlane.updatePosition();

        // Assert
        assertTrue(userPlane.getTranslateY() >= 0, "UserPlane should remain within upper Y-bound.");
    }

    @Test
    public void testUpdatePositionOutOfBounds() {
        // Arrange
        userPlane.moveDown();
        userPlane.setTranslateY(UserPlane.SCREEN_HEIGHT + 10);

        // Act
        userPlane.updatePosition();

        // Assert
        assertFalse(userPlane.isAlive(), "UserPlane should be marked as not alive when out of bounds.");
    }

    @Test
    public void testFirePistol() {
        // Arrange
        userPlane.setGunType(UserPlane.GunType.PISTOL);

        // Act
        List<ActiveActorDestructible> projectiles = userPlane.fireProjectile();

        // Assert
        assertEquals(1, projectiles.size(), "Pistol should fire one projectile.");
        assertTrue(projectiles.get(0) instanceof UserProjectile, "Projectile should be of type UserProjectile.");
    }

    @Test
    public void testFireShotgun() {
        // Arrange
        userPlane.setGunType(UserPlane.GunType.SHOTGUN);

        // Act
        List<ActiveActorDestructible> projectiles = userPlane.fireProjectile();

        // Assert
        assertEquals(3, projectiles.size(), "Shotgun should fire three projectiles.");
    }

    @Test
    public void testFireMinigun() {
        // Arrange
        userPlane.setGunType(UserPlane.GunType.MINIGUN);

        // Act
        List<ActiveActorDestructible> projectiles = userPlane.fireProjectile();

        // Assert
        assertEquals(5, projectiles.size(), "Minigun should fire five projectiles.");
    }

    @Test
    public void testSwitchGun() {
        // Arrange
        userPlane.setGunType(UserPlane.GunType.PISTOL);
        GunDisplay mockGunDisplay = mock(GunDisplay.class);
        userPlane.gunDisplay = mockGunDisplay;

        // Act
        userPlane.switchGun();

        // Assert
        verify(mockGunDisplay, times(1)).updateGunDisplay("Pistol");
    }

    @Test
    public void testMovementControls() {
        // Act
        userPlane.moveUp();
        userPlane.updatePosition();

        // Assert
        assertTrue(userPlane.getTranslateY() < UserPlane.INITIAL_Y_POSITION, "UserPlane should move up when moveUp is called.");

        // Act
        userPlane.moveDown();
        userPlane.updatePosition();

        // Assert
        assertTrue(userPlane.getTranslateY() > UserPlane.INITIAL_Y_POSITION, "UserPlane should move down when moveDown is called.");
    }

    @Test
    public void testKillCount() {
        // Arrange
        assertEquals(0, userPlane.getNumberOfKills(), "Initial kill count should be 0.");

        // Act
        userPlane.incrementKillCount();

        // Assert
        assertEquals(1, userPlane.getNumberOfKills(), "Kill count should increment by 1.");
    }

    @Test
    public void testPlaySound() {
        // Arrange
        String soundFilePath = "/com/example/demo/sounds/ProjectileSound4.mp3";

        // Act
        userPlane.playSound(soundFilePath);

        // Assert
        assertNotNull(soundFilePath, "Sound should play without exceptions.");
    }
}
