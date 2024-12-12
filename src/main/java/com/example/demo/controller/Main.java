package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String TITLE = "Sky Survivor";
    private Controller myController;

    @Override
    public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        stage.setTitle(TITLE);
        //stage.setResizable(false);  // Allow resizing to make it more adaptable for fullscreen
        //stage.setFullScreen(false); // Make the window fullscreen
        
        // Optionally, you can preserve the aspect ratio to avoid distortion when resizing.
        // stage.setMaximized(true); // This will maximize the window, but still allow for resizing.
        
        myController = new Controller(stage);
        myController.launchGame();
    }

    public static void main(String[] args) {
        launch();
    }
}
