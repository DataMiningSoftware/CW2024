package com.example.demo.Interfaces;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javafx.scene.Group;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Level_Interface class.
 */
public class Level_InterfaceTest {

    private Level_Interface levelInterface;
    private Group mockRoot;

    @BeforeEach
    public void setUp() {
        mockRoot = mock(Group.class);
        levelInterface = new Level_Interface(mockRoot, 3, 10); // 3 hearts, 10 kills to advance
    }

    @Test
    public void testInitializeKillCountDisplay() {
        // Arrange
        Group spyRoot = spy(new Group());
        Level_Interface levelInterfaceWithSpy = new Level_Interface(spyRoot, 3, 10);

        // Act
        levelInterfaceWithSpy.updateKillCount(0);

        // Assert
        boolean containsKillCounter = spyRoot.getChildren().stream()
                .anyMatch(node -> node instanceof Text && ((Text) node).getText().equals("Kills: 0/10"));
        assertTrue(containsKillCounter, "Kill counter should be added to the root node.");
    }

    @Test
    public void testShowHeartDisplay() {
        // Arrange
        Group spyRoot = spy(new Group());
        Level_Interface levelInterfaceWithSpy = new Level_Interface(spyRoot, 3, 10);

        // Act
        levelInterfaceWithSpy.showHeartDisplay();

        // Assert
        verify(spyRoot, atLeastOnce()).getChildren();
    }

    @Test
    public void testShowWinImage() {
        // Arrange
        Group spyRoot = spy(new Group());
        Level_Interface levelInterfaceWithSpy = new Level_Interface(spyRoot, 3, 10);

        // Act
        levelInterfaceWithSpy.showWinImage();

        // Assert
        verify(spyRoot, atLeastOnce()).getChildren();
    }

    @Test
    public void testShowGameOverImage() {
        // Arrange
        Group spyRoot = spy(new Group());
        Level_Interface levelInterfaceWithSpy = new Level_Interface(spyRoot, 3, 10);

        // Act
        levelInterfaceWithSpy.showGameOverImage();

        // Assert
        verify(spyRoot, atLeastOnce()).getChildren();
    }

    @Test
    public void testRemoveHearts() {
        // Arrange
        Group spyRoot = spy(new Group());
        Level_Interface levelInterfaceWithSpy = new Level_Interface(spyRoot, 3, 10);

        // Act
        levelInterfaceWithSpy.showHeartDisplay();
        levelInterfaceWithSpy.removeHearts(2);

        // Assert
        assertEquals(2, levelInterfaceWithSpy.getKillsToAdvance(), "Hearts remaining should match the updated display.");
    }

    @Test
    public void testUpdateKillCount() {
        // Arrange
        Group spyRoot = spy(new Group());
        Level_Interface levelInterfaceWithSpy = new Level_Interface(spyRoot, 3, 10);

        // Act
        levelInterfaceWithSpy.updateKillCount(5);

        // Assert
        boolean containsUpdatedKillCount = spyRoot.getChildren().stream()
                .anyMatch(node -> node instanceof Text && ((Text) node).getText().equals("Kills: 5/10"));
        assertTrue(containsUpdatedKillCount, "Kill count should be updated in the UI.");
    }
}
