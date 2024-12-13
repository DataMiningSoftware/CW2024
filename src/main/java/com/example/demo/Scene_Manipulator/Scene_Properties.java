package com.example.demo.Scene_Manipulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.demo.Entities.ActiveActorDestructible;
import com.example.demo.Entities.FighterPlane;
import com.example.demo.Entities.UserPlane;
import com.example.demo.Interfaces.GunDisplay;
import com.example.demo.Interfaces.Level_Interface;

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
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Abstract class that defines the properties and behaviors for a game scene. 
 * It handles the initialization of game elements, user and enemy interactions, 
 * background setup, pause functionality, and transitions between game states.
 */
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
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private Set<KeyCode> pressedKeys = new HashSet<>(); // Track pressed keys

    
    /**
     * Constructor that initializes the basic properties of the game scene, including background,
     * user plane, and game elements such as friendly and enemy units, projectiles, and pause settings.
     * 
     * @param backgroundImageName the name of the background image
     * @param screenHeight the height of the game screen
     * @param screenWidth the width of the game screen
     * @param playerInitialHealth the initial health of the user plane
     */
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

    /**
     * Abstract method for initializing the friendly units (player and allies).
     */
    protected abstract void initializeFriendlyUnits();

    /**
     * Abstract method to check if the game is over.
     */
    protected abstract void checkIfGameOver();

    /**
     * Abstract method for spawning enemy units.
     */
    protected abstract void spawnEnemyUnits();

    /**
     * Abstract method for instantiating the level-specific view.
     * 
     * @return the level-specific view
     */
    protected abstract Level_Interface instantiateLevelView();

    /**
     * Initializes the scene, including background, friendly units, and level-specific UI.
     * 
     * @return the game scene
     */
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

    /**
     * Starts the game by playing a fade-in animation and starting the timeline.
     */
    public void startGame() {
        background.requestFocus();

        // Create a fade-in transition
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.3), root); // 1-second fade
        fadeIn.setFromValue(0.0); // Start fully transparent
        fadeIn.setToValue(1.0);   // End fully visible
        fadeIn.setOnFinished(e -> timeline.play()); // Start the game timeline after the fade-in
        fadeIn.play(); // Play the fade-in animation
    }

    /**
     * Goes to the next level by notifying observers with the level name.
     * 
     * @param levelName the name of the next level
     */
    public void goToNextLevel(String levelName) {
        timeline.stop();
        setChanged();
        notifyObservers(levelName);
    }

    /**
     * Initializes the background image and sets up key event listeners for player movement and actions.
     */
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

    /**
     * Toggles the pause state of the game.
     * If the game is paused, it resumes the game and hides the pause overlay and menu.
     * If the game is running, it pauses the game and shows the pause overlay and menu.
     */
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

    /**
     * Initializes the game timeline for the game loop.
     * The game loop updates the scene at a fixed interval (MILLISECOND_DELAY).
     */
    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
        timeline.getKeyFrames().add(gameLoop);
    }

    /**
     * Fires a projectile from the user plane.
     * The projectiles fired are added to the scene and the userProjectiles list.
     */
    private void fireProjectile() {
        List<ActiveActorDestructible> projectiles = user.fireProjectile(); // Overloaded method
        for (ActiveActorDestructible projectile : projectiles) {
            root.getChildren().add(projectile);
            userProjectiles.add(projectile);
        }
    }

    /**
     * Generates enemy fire by iterating through all enemy units and spawning their projectiles.
     */
    private void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile(user)));
    }

    /**
     * Spawns a projectile for an enemy and adds it to the scene and enemy projectiles list.
     * @param projectile The projectile to spawn.
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    /**
     * Updates all actors in the game, including friendly units, enemy units, user projectiles, and enemy projectiles.
     */
    private void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    /**
     * Removes any destroyed actors from the scene and clears them from the actor lists.
     * @param actors The list of actors to check for destruction.
     */
    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    /**
     * Removes all destroyed actors from the scene and clears them from the actor lists.
     */
    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
        removeDestroyedActors(enemyUnits);
    }

    /**
     * Handles collisions between friendly units and enemy units.
     */
    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     */
    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     */
    private void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Generic collision handler between two lists of actors.
     * When two actors intersect, both take damage.
     * @param actors1 The first list of actors.
     * @param actors2 The second list of actors.
     */
    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    /**
     * Handles situations where enemy units have penetrated the user's defenses.
     * The user takes damage when an enemy unit passes beyond the screen.
     */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    /**
     * Updates the level view, including removing hearts if the user's health decreases.
     */
    private void updateLevelView() {
        if (levelView != null) { // Only update if levelView is initialized
            levelView.removeHearts(user.getHealth());
        }
    }

    /**
     * Checks if an enemy unit has penetrated the user's defenses.
     * @param enemy The enemy unit to check.
     * @return True if the enemy has penetrated the defenses, otherwise false.
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    /**
     * Handles winning the game, including stopping the timeline and displaying the win image.
     */
    protected void winGame() {
        timeline.stop();
        levelView.showWinImage();
        showRetryButton();
    }

    /**
     * Handles losing the game, including stopping the timeline and displaying the game over image.
     */
    protected void loseGame() {
        timeline.stop();
        levelView.showGameOverImage();
        showRetryButton();
    }

    /**
     * Returns the user's plane object.
     * @return The UserPlane object.
     */
    protected UserPlane getUser() {
        return user;
    }

    /**
     * Returns the root group for the scene.
     * @return The root group.
     */
    protected Group getRoot() {
        return root;
    }

    /**
     * Returns the current number of enemy units in the game.
     * @return The size of the enemy units list.
     */
    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    /**
     * Adds an enemy unit to the list and scene.
     * @param enemy The enemy unit to add.
     */
    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    /**
     * Provides access to the game timeline for subclass use.
     * @return The timeline object.
     */
    protected Timeline getTimeline() {
        return timeline; // Allow subclasses to access the timeline
    }

    /**
     * Returns the maximum Y position for enemy units, which is the screen height minus an adjustment.
     * @return The enemy's maximum Y position.
     */
    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    /**
     * Returns the screen width.
     * @return The screen width.
     */
    public double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Updates the current number of enemy units.
     */
    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = enemyUnits.size();
    }

    /**
     * Checks if the user has been destroyed.
     * @return True if the user is destroyed, otherwise false.
     */
    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    /**
     * Returns the screen height.
     * @return The screen height.
     */
    protected double getScreenHeight() {
        return screenHeight;
    }

    /**
     * Returns the list of enemy units in the game.
     * @return The list of enemy units.
     */
    protected List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    /**
     * Returns the list of friendly units in the game.
     * @return The list of friendly units.
     */
    protected List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    /**
     * Updates the projectiles in the game, removing any that are no longer alive.
     */
    public void updateProjectiles() {
        // Remove projectiles that are out of bounds
        userProjectiles.removeIf(projectile -> !projectile.isAlive());
        enemyProjectiles.removeIf(projectile -> !projectile.isAlive());
        
        // Optionally, you can also remove projectiles based on position directly, like this:
        // userProjectiles.removeIf(projectile -> projectile.getTranslateX() > screenWidth || projectile.getTranslateY() > screenHeight);
    }

    /**
     * Displays the retry button on the screen.
     */
    private void showRetryButton() {
        retryButton.setVisible(true);
        retryButton.toFront(); // Ensure it appears on top
    }

    /**
     * Resets the game state, clearing all actors and starting over from level 1.
     */
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

    /**
     * Updates the kill count, showing the number of enemies defeated.
     */
    private void updateKillCount() {
        if (killcounter != null) {
            for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
                user.incrementKillCount();
                killcount++;
            }
            killcounter.setText("Enemies Defeated: " + killcount + "/" + levelView.getKillsToAdvance());
        }
    }

    /**
     * Updates the game scene by spawning enemy units, updating actors, handling collisions,
     * and checking if the game is over.
     */
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

    /**
     * Initializes the pause menu, which includes resume, restart, and quit buttons.
     */
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
            setChanged();
            notifyObservers("com.example.demo.MainMenu"); // Notify observers to go to the main menu
            System.exit(0); // Exit the game
        });

        // Add buttons to menu
        pauseMenu.getChildren().addAll(resumeButton, restartButton, quitButton);
    }
}
