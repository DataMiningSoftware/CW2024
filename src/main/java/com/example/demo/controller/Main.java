package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class is the entry point for the Sky Survivor game.
 * It initializes the main game window, sets the title, and launches the game by 
 * invoking the Controller to manage game navigation and logic.
 */
public class Main extends Application {

    // Constant for the game window title
    private static final String TITLE = "Sky Survivor";  // Game title displayed on the window
    
    // Controller to manage game navigation and logic
    private Controller myController;

    /**
     * Initializes the main game window and sets up the controller to manage the game.
     * 
     * @param stage The primary stage (window) for the game.
     * @throws ClassNotFoundException If a class cannot be found during reflection.
     * @throws NoSuchMethodException If a required method is not found.
     * @throws SecurityException If there is a security violation during reflection.
     * @throws InstantiationException If an error occurs during instantiation.
     * @throws IllegalAccessException If there is an error accessing a method or constructor.
     * @throws IllegalArgumentException If an invalid argument is passed during reflection.
     * @throws InvocationTargetException If the method or constructor being invoked throws an exception.
     */
    @Override
    public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        // Set the title of the window
        stage.setTitle(TITLE);

        // Optional window settings (currently commented out for flexibility)
        // stage.setResizable(false);  // Prevent resizing to keep consistent aspect ratio
        // stage.setFullScreen(false); // Make the window fullscreen if preferred
        
        // Optional: Preserve the aspect ratio when resizing
        // stage.setMaximized(true); // Maximize the window, but still allow resizing
        
        // Initialize the Controller to manage game state and navigation
        myController = new Controller(stage);
        
        // Launch the game by navigating to the main menu
        myController.launchGame();
    }

    /**
     * The main entry point for launching the JavaFX application.
     * 
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }
}
