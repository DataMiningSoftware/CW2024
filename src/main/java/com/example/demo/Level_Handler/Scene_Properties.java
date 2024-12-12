package com.example.demo.Level_Handler;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.demo.FighterPlane;
import com.example.demo.GunDisplay;
import com.example.demo.UserPlane;
import com.example.demo.UserPlane.GunType;
import com.example.demo.Projectiles.ActiveActorDestructible;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public abstract class Scene_Properties extends Observable {

    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private static final int MILLISECOND_DELAY = 30; //original 50
	public static final double SCREEN_WIDTH = 0;
	public static final double SCREEN_HEIGHT = 0;
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;

	private final Stage stage;
    private final Group root;
    private final Timeline timeline;
    protected final UserPlane user;
    private final Scene scene;
    private final ImageView background;
    private final Button retryButton;
    

    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    private boolean paused; // Flag to track pause state
    private final Rectangle pauseOverlay; // Black overlay
    protected int currentNumberOfEnemies;
    private Level_Interface levelView;
    private Text killcounter;
    private int killcount = 0;
    private VBox pauseMenu;

    private Text pauseMessage; // Text object for displaying "Paused"
    
    private GunDisplay gunDisplay;
    private MediaPlayer mediaPlayer; // MediaPlayer to manage music

    private Set<KeyCode> pressedKeys = new HashSet<>(); // Track pressed keys


    public Scene_Properties(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.timeline = new Timeline();
        this.user = new UserPlane(playerInitialHealth);
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.paused = false; // Game starts unpaused
        this.stage = new Stage();
        this.gunDisplay = new GunDisplay();


        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
        this.levelView = instantiateLevelView();
        this.currentNumberOfEnemies = 0;

        // Initialize the pause overlay
        this.pauseOverlay = new Rectangle(screenWidth, screenHeight);
        this.pauseOverlay.setFill(Color.BLACK);
        this.pauseOverlay.setOpacity(0.5); // Semi-transparent
        this.pauseOverlay.setVisible(false); // Hidden by default
        root.getChildren().add(pauseOverlay);
        // Add gun display to the scene
      
        root.getChildren().add(gunDisplay.getGunBox());
        root.getChildren().add(gunDisplay.getGunText());
 

        // Initialize the pause message
        this.pauseMessage = new Text("Paused");
        pauseMessage.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        pauseMessage.setFill(Color.WHITE);
        pauseMessage.setX(screenWidth / 2 - 100); // Center horizontally
        pauseMessage.setY(screenHeight / 2); // Center vertically
        initializePauseMenu(); // This must be called

        initializeTimeline();
        friendlyUnits.add(user);
        
        retryButton = new Button("Retry");
        retryButton.setLayoutX(screenWidth / 2 - 50); // Center horizontally
        retryButton.setLayoutY(screenHeight / 2 + 50); // Position below "Paused" or win/lose messages
        retryButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        retryButton.setVisible(true); // Hidden by default
        root.getChildren().add(retryButton);
        retryButton.setOnAction(e -> {
            stopMusic(); // Stop music when game over

            setChanged();
            notifyObservers("com.example.demo.Levels.LevelOne"); // Notify controller to load LevelOne
        });
    }

    protected abstract void initializeFriendlyUnits();

    protected abstract void checkIfGameOver();

    protected abstract void spawnEnemyUnits();

    protected abstract Level_Interface instantiateLevelView();

    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();

        killcounter = new Text("Objective: kill ");
        killcounter.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        killcounter.setFill(Color.WHITE);
        killcounter.setX(700); // Position on the left side of the screen
        killcounter.setY(960); // Position near the top of the screen
        root.getChildren().add(killcounter);
        
        return scene;
    }

    public void startGame() {
    	background.requestFocus();

        // Create a fade-in transition
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.3), root); // 1-second fade
        fadeIn.setFromValue(0.0); // Start fully transparent
        fadeIn.setToValue(1.0);   // End fully visible
        fadeIn.setOnFinished(e -> timeline.play()); // Start the game timeline after the fade-in
        fadeIn.play(); // Play the fade-in animation
    }

    public void goToNextLevel(String levelName) {
        timeline.stop();
        setChanged();
        notifyObservers(levelName);
    }

    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);

        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                pressedKeys.add(kc); // Add key to the pressed keys set
                
                if (kc == KeyCode.ESCAPE) {
                    togglePause(); // Pause or unpause the game
                }
                
                if (!paused) { // Only process inputs when not paused
                    if (pressedKeys.contains(KeyCode.UP)) {
                        user.moveUp();
                    }
                    if (pressedKeys.contains(KeyCode.DOWN)) {
                        user.moveDown();
                    }
                    if (pressedKeys.contains(KeyCode.LEFT)) {
                        user.moveLeft();
                    }
                    if (pressedKeys.contains(KeyCode.RIGHT)) {
                        user.moveRight();
                    }
                    if (pressedKeys.contains(KeyCode.SPACE)) {
                        fireProjectile();
                    }
                    if (kc == KeyCode.DIGIT1) {
                        user.setGunType(UserPlane.GunType.PISTOL);
                    } else if (kc == KeyCode.DIGIT2) {
                        user.setGunType(UserPlane.GunType.SHOTGUN);
                    } else if (kc == KeyCode.DIGIT3) {
                        user.setGunType(UserPlane.GunType.MINIGUN);
                    }
                }

            }
        });

        background.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                pressedKeys.remove(kc); // Remove key from the pressed keys set
                
                if (!paused && (kc == KeyCode.UP || kc == KeyCode.DOWN || kc == KeyCode.LEFT || kc == KeyCode.RIGHT)) {
                    user.stop();
                }
            }
        });

        root.getChildren().add(background);
    }

    private void togglePause() {
        if (paused) {
            timeline.play(); // Resume game
            pauseOverlay.setVisible(false); // Hide the overlay
            pauseMenu.setVisible(false); // Hide the menu
        } else {
            timeline.stop(); // Pause game
            pauseOverlay.toFront();
            pauseOverlay.setVisible(true); // Show the overlay
            pauseMenu.toFront();
            pauseMenu.setVisible(true); // Show the menu
        }
        paused = !paused; // Toggle paused state
    }


    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
        timeline.getKeyFrames().add(gameLoop);
    }


    private void fireProjectile() {
        List<ActiveActorDestructible> projectiles = user.fireProjectile(); // Overloaded method
        for (ActiveActorDestructible projectile : projectiles) {
            root.getChildren().add(projectile);
            userProjectiles.add(projectile);
        }
    }

    private void generateEnemyFire() {
    	enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile(user)));
    }

    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    private void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    

    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }
    
    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
        removeDestroyedActors(enemyUnits);
    }

    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    private void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    private void handleCollisions(List<ActiveActorDestructible> actors1,
                                  List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    private void updateLevelView() {
        if (levelView != null) { // Only update if levelView is initialized
            levelView.removeHearts(user.getHealth());
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    protected void winGame() {
        timeline.stop();
        levelView.showWinImage();
        showRetryButton();
    }

    protected void loseGame() {
        timeline.stop();
        levelView.showGameOverImage();
        showRetryButton();
    }

    protected UserPlane getUser() {
        return user;
    }

    protected Group getRoot() {
        return root;
    }

    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    protected Timeline getTimeline() {
        return timeline; // Allow subclasses to access the timeline
    }

    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    public double getScreenWidth() {
        return screenWidth;
    }
    
    private void updateNumberOfEnemies() {
    	currentNumberOfEnemies = enemyUnits.size();
    }
    
    protected boolean userIsDestroyed() {
    	return user.isDestroyed();
    }
    
    protected double getScreenHeight() {
        return screenHeight;
    }

    protected List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    protected List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }
    
    public void updateProjectiles() {
        // Remove projectiles that are out of bounds
        userProjectiles.removeIf(projectile -> !projectile.isAlive());
        enemyProjectiles.removeIf(projectile -> !projectile.isAlive());
        
        // Optionally, you can also remove projectiles based on position directly, like this:
        // userProjectiles.removeIf(projectile -> projectile.getTranslateX() > screenWidth || projectile.getTranslateY() > screenHeight);
    }
    
    private void showRetryButton() {
        retryButton.setVisible(true);
        retryButton.toFront(); // Ensure it appears on top
    }
    
    private void resetGame() {
        // Reset game state
        root.getChildren().clear(); // Clear all nodes from the scene
        friendlyUnits.clear();
        enemyUnits.clear();
        userProjectiles.clear();
        enemyProjectiles.clear();
        
        // Reinitialize components
        root.getChildren().addAll(background, pauseOverlay, retryButton);
        pauseOverlay.setVisible(false);
        retryButton.setVisible(false);

        // Start from level 1
        setChanged();
        notifyObservers("Level 1"); // Notify observers to reload level 1

        timeline.play(); // Restart the game
    }
    
    private void updateKillCount() {
        if (killcounter != null) {
            for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
                user.incrementKillCount();
                killcount++;
            }
            killcounter.setText("Enemies Defeated: " + killcount + "/" + levelView.getKillsToAdvance());
        }
    }
    
    private void updateScene() {
        if (!paused) { // Only update the scene if not paused
            spawnEnemyUnits();
            updateActors();
            generateEnemyFire();
            updateNumberOfEnemies();
            handleEnemyPenetration();
            handleUserProjectileCollisions();
            handleEnemyProjectileCollisions();
            handlePlaneCollisions();
            removeAllDestroyedActors();
            updateLevelView();
            checkIfGameOver();
            updateKillCount();
        }
    }
    
    private void initializePauseMenu() {
        // Create the pause menu
        pauseMenu = new VBox(20); // Spacing of 20 between buttons
        pauseMenu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20; -fx-border-color: white; -fx-border-width: 2;"); // Styling
        pauseMenu.setAlignment(Pos.CENTER); // Center the menu contents
        pauseMenu.setPrefSize(400, 300); // Set size of the menu box
        pauseMenu.setLayoutX((screenWidth - 200) / 2); // Center horizontally
        pauseMenu.setLayoutY((screenHeight - 300) / 2); // Center vertically
        pauseMenu.setVisible(false); // Initially hidden

        // Create buttons
        Button resumeButton = new Button("Resume Game");
        resumeButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        resumeButton.setOnAction(e -> togglePause());

        Button restartButton = new Button("Restart");
        restartButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        restartButton.setOnAction(e -> {
        	setChanged();
            notifyObservers("com.example.demo.Levels.LevelOne"); // Notify observers to load the main menu
            togglePause(); // Close the pause menu after restarting
        });

        Button quitButton = new Button("Quit to Main Menu");
        quitButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        quitButton.setOnAction(e -> {
            // Notify observers to load the main menu
            setChanged();
            notifyObservers("com.example.demo.MainMenu");

            // Stop any active timelines
            timeline.stop();
            resetGameLogic();
            goToMainMenu();
       
            stage.setMaximized(true);
            stage.centerOnScreen();  // Recenter window on the screen
        });
        // Add buttons to the menu
        pauseMenu.getChildren().addAll(resumeButton, restartButton, quitButton);

        // Add the pause menu to the root
        root.getChildren().add(pauseMenu);
    }
    
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();  // Stop the current music
        }
    }
    
    private void goToMainMenu() {
        try {
            // Reuse the existing MainMenu scene instead of creating a new one
            // Assuming you have a method in MainMenu that can reset its state if necessary

            Class<?> myClass = Class.forName("com.example.demo.MainMenu");
            Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
            Scene_Properties mainMenu = (Scene_Properties) constructor.newInstance(stage.getHeight(), stage.getWidth());

            // Stop any active timelines or game processes
            timeline.stop();  // Stop the current game timeline

            // Optionally, reset game logic if needed (e.g., player, enemies, projectiles)

            // Initialize the main menu scene
            Scene scene = mainMenu.initializeScene();

            // Set the scene on the stage (replace the current scene)
            stage.setScene(scene);
            stage.show();

            // Maximize and center the window again
            stage.setMaximized(true);
            stage.centerOnScreen();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateGunDisplay(GunType currentGun) {
        gunDisplay.updateGunDisplay(currentGun.name());  // Display the current gun name
    }
    
    

    // Method to reset any game logic
    private void resetGameLogic() {
        // Add logic to reset game variables, pause any animations, etc.
        // For example, stop the timeline, clear enemies, reset player position, etc.
        // Example:
        // player.reset(); // Your logic to reset the player
        // enemyManager.clear(); // Reset or clear any enemies
    }
}
