package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.Scene_Manipulator.Scene_Properties;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Unit tests for the Controller class.
 */
public class ControllerTest {

    private Controller controller;
    private Stage mockStage;

    @BeforeAll
    public static void initJavaFX() {
        // Initialize JavaFX runtime for testing
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        // Mock the Stage
        mockStage = mock(Stage.class);
        controller = new Controller(mockStage);
    }

    @Test
    public void testLaunchGameSuccess() {
        // Arrange
        doNothing().when(mockStage).show();

        // Act & Assert
        assertDoesNotThrow(() -> controller.launchGame());
        verify(mockStage, times(1)).show();
    }

    @Test
    public void testLaunchGameFailure() {
        // Arrange
        doThrow(new RuntimeException("Stage error")).when(mockStage).show();

        // Act
        assertDoesNotThrow(() -> controller.launchGame());

        // Verify
        verify(mockStage, times(1)).show();
    }

    @Test
    public void testNavigateToLevelValidClass() throws Exception {
        // Arrange
        String validClassName = "com.example.demo.SomeLevel";
        Scene_Properties mockLevel = mock(Scene_Properties.class);
        when(mockLevel.initializeScene()).thenReturn(mock(Scene.class));

        // Mock reflection
        Controller spyController = Mockito.spy(controller);
        doReturn(mockLevel).when(spyController).createLevel(validClassName);

        // Act & Assert
        assertDoesNotThrow(() -> spyController.navigateToLevel(validClassName));
        verify(mockLevel, times(1)).initializeScene();
        verify(mockLevel, times(1)).startGame();
    }

    @Test
    public void testNavigateToLevelInvalidClass() {
        // Arrange
        String invalidClassName = "com.example.demo.NonExistentLevel";

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> controller.navigateToLevel(invalidClassName));
        assertTrue(exception instanceof ClassNotFoundException);
    }

    @Test
    public void testUpdateWithValidClassName() throws Exception {
        // Arrange
        String validClassName = "com.example.demo.SomeLevel";
        Scene_Properties mockLevel = mock(Scene_Properties.class);
        when(mockLevel.initializeScene()).thenReturn(mock(Scene.class));

        // Mock reflection
        Controller spyController = Mockito.spy(controller);
        doReturn(mockLevel).when(spyController).createLevel(validClassName);

        // Act
        spyController.update(null, validClassName);

        // Verify
        verify(spyController, times(1)).navigateToLevel(validClassName);
    }

    @Test
    public void testUpdateWithInvalidArgument() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.update(null, 123));
        assertEquals("Expected a class name string but got: 123", exception.getMessage());
    }
}
