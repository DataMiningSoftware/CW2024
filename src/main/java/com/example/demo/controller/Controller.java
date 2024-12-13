package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.Scene_Manipulator.Scene_Properties;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * The Controller class handles the interaction between the user interface and the game's logic.
 * It manages the game flow, navigation between levels, and error handling.
 * It also acts as an observer for changes in the game state and updates the view accordingly.
 */
public class Controller implements Observer {

    // Constant for the main menu class name
    private static final String MAIN_MENU_CLASS_NAME = "com.example.demo.MainMenu";
    private final Stage stage;

    /**
     * Constructs a Controller instance with the specified stage.
     * 
     * @param stage The main application window (Stage) for the game.
     */
    public Controller(Stage stage) {
        this.stage = stage;
    }

    /**
     * Launches the game by showing the main stage and navigating to the main menu.
     */
    public void launchGame() {
        try {
            stage.show(); // Show the stage
            navigateToLevel(MAIN_MENU_CLASS_NAME); // Navigate to the main menu
        } catch (Exception e) {
            showErrorAlert(e); // If an error occurs, show an alert
        }
    }

    /**
     * Navigates to a specified game level by the provided class name.
     * 
     * @param className The fully qualified class name of the level to navigate to.
     */
    protected void navigateToLevel(String className) {
        try {
            // Create the level using reflection
            Scene_Properties sceneProperties = createLevel(className);
            // Initialize and set the scene for the stage
            initializeScene(sceneProperties);
        } catch (Exception e) {
            showErrorAlert(e); // If an error occurs, show an alert
        }
    }

    /**
     * Creates a new level (Scene_Properties) based on the class name using reflection.
     * 
     * @param className The fully qualified class name of the level to be created.
     * @return A new instance of Scene_Properties corresponding to the specified level class.
     * @throws Exception If there is an error during the reflection or instantiation process.
     */
    protected Scene_Properties createLevel(String className) throws Exception {
        // Load the class dynamically
        Class<?> levelClass = Class.forName(className);
        // Get the constructor that takes two double parameters (stage width and height)
        Constructor<?> constructor = levelClass.getConstructor(double.class, double.class);
        // Create a new instance of Scene_Properties (the level)
        return (Scene_Properties) constructor.newInstance(stage.getHeight(), stage.getWidth());
    }

    /**
     * Initializes the scene for the specified level and sets it on the stage.
     * 
     * @param sceneProperties The properties and initialization information for the level scene.
     */
    private void initializeScene(Scene_Properties sceneProperties) {
        sceneProperties.addObserver(this); // Add the controller as an observer
        Scene scene = sceneProperties.initializeScene(); // Initialize the scene
        stage.setScene(scene); // Set the scene to the stage
        stage.setMaximized(true); // Set the stage to maximized
        sceneProperties.startGame(); // Start the game for the level
    }

    /**
     * Displays an error alert with details about the exception.
     * 
     * @param e The exception to be displayed in the error alert.
     */
    private void showErrorAlert(Exception e) {
        Alert alert = new Alert(AlertType.ERROR); // Create an error alert
        alert.setContentText("Error: " + e.getClass().getName() + " - " + e.getMessage()); // Set error details
        alert.show(); // Show the alert
    }

    /**
     * Called when the observed object changes. If the change indicates a level change,
     * this method navigates to the new level.
     * 
     * @param observable The observable object that triggered the update.
     * @param arg The argument passed from the observable object, expected to be a class name string.
     */
    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof String) {
            // If the argument is a valid class name, navigate to the corresponding level
            navigateToLevel((String) arg);
        } else {
            // If the argument is not a valid class name, show an error alert
            showErrorAlert(new IllegalArgumentException("Expected a class name string but got: " + arg));
        }
    }
}
