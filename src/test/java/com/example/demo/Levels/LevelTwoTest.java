package com.example.demo.Levels;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.Entities.ActiveActorDestructible;
import com.example.demo.Entities.EnemyPlaneV2;
import com.example.demo.Interfaces.Level_Interface;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Unit tests for the LevelTwo class.
 */
public class LevelTwoTest {

    private LevelTwo levelTwo;
    private LevelTwo spyLevelTwo;

    @BeforeEach
    public void setUp() {
        levelTwo = new LevelTwo(800, 600); // Create a LevelTwo instance with a screen size of 800x600
        spyLevelTwo = Mockito.spy(levelTwo); // Create a spy instance for more control
    }

    @Test
    public void testUserHasReachedKillTarget() {
        // Arrange
        var mockUser = mock(ActiveActorDestructible.class);
        when(mockUser.getNumberOfKills()).thenReturn(15);
        doReturn(mockUser).when(spyLevelTwo).getUser();

        // Act
        boolean result = spyLevelTwo.userHasReachedKillTarget();

        // Assert
        assertTrue(result, "The user should have reached the kill target.");
    }

    @Test
    public void testCheckIfGameOver_UserDestroyed() {
        // Arrange
        doReturn(true).when(spyLevelTwo).userIsDestroyed();
        doNothing().when(spyLevelTwo).handleGameOver();

        // Act
        spyLevelTwo.checkIfGameOver();

        // Assert
        verify(spyLevelTwo, times(1)).handleGameOver();
    }

    @Test
    public void testCheckIfGameOver_KillTargetReached() {
        // Arrange
        doReturn(false).when(spyLevelTwo).userIsDestroyed();
        doReturn(true).when(spyLevelTwo).userHasReachedKillTarget();
        doNothing().when(spyLevelTwo).proceedToNextLevel();

        // Act
        spyLevelTwo.checkIfGameOver();

        // Assert
        verify(spyLevelTwo, times(1)).proceedToNextLevel();
    }

    @Test
    public void testSpawnEnemy() {
        // Arrange
        doReturn(600.0).when(spyLevelTwo).getEnemyMaximumYPosition();
        doReturn(800.0).when(spyLevelTwo).getScreenWidth();
        doNothing().when(spyLevelTwo).addEnemyUnit(any(ActiveActorDestructible.class));

        // Act
        spyLevelTwo.spawnEnemy();

        // Assert
        verify(spyLevelTwo, times(1)).addEnemyUnit(any(EnemyPlaneV2.class));
    }

    @Test
    public void testPlayBackgroundMusic() {
        // Arrange
        doNothing().when(spyLevelTwo).stopMusic();

        // Act
        spyLevelTwo.playBackgroundMusic();

        // Assert
        assertNotNull(spyLevelTwo.mediaPlayer, "MediaPlayer should not be null after playing music.");
        assertTrue(spyLevelTwo.mediaPlayer.isAutoPlay(), "MediaPlayer should be set to autoplay.");
        assertEquals(MediaPlayer.INDEFINITE, spyLevelTwo.mediaPlayer.getCycleCount(), "MediaPlayer should loop indefinitely.");
    }

    @Test
    public void testStopMusic() {
        // Arrange
        MediaPlayer mockMediaPlayer = mock(MediaPlayer.class);
        spyLevelTwo.mediaPlayer = mockMediaPlayer;

        // Act
        spyLevelTwo.stopMusic();

        // Assert
        verify(mockMediaPlayer, times(1)).stop();
    }
}
