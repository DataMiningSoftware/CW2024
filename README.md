# Coursework Project Documentation (COMP2024)

## GitHub Repository
[Repository Link](https://github.com/DataMiningSoftware/CW2024) - Muhammad Nawfal Bin Nadzrim (20614242)

---

## Compilation Instructions
1. **Clone the Repository**  
   Run the following command to clone the repository:
   ```bash
   git clone https://github.com/DataMiningSoftware/CW2024
2. Open the project in an IDE that supports Java such Eclipse, IntelliJ IDEA, __ETC__.
3. Ensure Maven is installed and the dependencies are properly configured in the `pom.xml` file.
4. Build the project: mvn clean install
5. Run the application: mvn javafx:run
6. Java version: Ensure you have **Java 17** or later installed.

---

## Implemented and Working Properly
1. **Main Menu**:  
   - Fully functional main menu with clearly defined options, including "Start" and "Quit" buttons for seamless navigation.
2. **Level Advancement**:  
   - Smooth transitions between levels, each with unique gameplay mechanics. The final level introduces boss-specific mechanics, increasing the challenge.
3. **Enemy Behavior**:  
   - Enemies follow detailed attack patterns, behaviors, and collision detection rules. Enhanced logic ensures that enemies do not fire when the player’s plane is directly beneath them or has passed them.
4. **Projectile System**:  
   - Player and enemy projectiles are fully functional with accurate collision detection, lifespan limits, and dynamic homing behavior for specific projectiles.
5. **Boss Behavior**:  
   - Complete implementation of the boss system, including shield activation/deactivation, projectile firing, and smooth movement. The shield dynamically follows the boss during gameplay.
6. **Game Over and Victory Screens**:  
   - Customizable screens that appear based on win or loss conditions. These screens feature visually appealing animations and a centralized layout for clear presentation.
7. **Player Health Bar**:  
   - Dynamic health bar represented by heart icons. Health depletes when hit by enemy projectiles, providing instant visual feedback.
8. **Pause Menu**:  
   - Allows players to pause the game, resume gameplay, restart, or return to the main menu without disrupting the gaming experience.
9. **Background Music and Sound Effects**:  
   - Fully integrated sound system with music and sound effects, though the loudness controls remain unimplemented.
10. **Player Movement Animation**:  
    - Smooth player movement with weighted acceleration and deceleration, offering a more realistic and responsive control system.
11. **Alternative Shooting Methods**:  
    - Three unique fire modes for the player: Pistol, Shotgun, and Minigun. Each fire mode is accessible via keybinds (1, 2, and 3), offering varied shooting styles.
12. **Kill Counter**:  
    - A fully functional kill counter that tracks the number of enemies defeated and displays how many are left before advancing to the next level.
13. **Smarter Enemies**:  
    - Two enemy types equipped with homing projectiles that track the player’s last known location when fired.
14. **Faster Frame Rates**:  
    - The game runs at up to 30 frames per second (FPS), ensuring a smoother experience.
15. **Spinning Enemies**:  
    - Most enemies feature a combat animation that includes a 360-degree spin while attacking, adding visual flair and dynamic movement to encounters.
16. **Damage Indicator**:  
    - A damage indicator appears when the player’s projectiles hit an enemy, providing feedback that attacks are successfully connecting.
17. **Level Transitions**:  
    - A brief animation plays during level transitions, enhancing the flow between levels and immersing the player in the game's world.
18. **Retry Option**:  
    - A retry button appears on both the win and loss screens, giving players the option to immediately replay the level.

---

## Implemented but Not Working Properly
- **Boss Sheild mechanic**: The boss's shield is present and is functional however it does not appear unfortunatly.
- **Enemy Death Animation**: Enemy planes do have their death animations like an yellow colored fading effect, but wont function under normal circumstances. The only time it played was when i removed the removeDestroyedActors(enemyUnits); line number 367 in Scene_Properties.java. doing so let the enemy live on forever
-**Level-Border**: The level border is where the player cannot pass and where the projectiles will dissapear after going past it. The player is blocked from going too far too the right but the projectiles dont dissapear.
- **Player Shield mechanic**: A limited shield that can negate enemy projectiles but its charge will be consumed for each second it is used. Overtime it will recover its charge for the player to use it again. This mechanic was implemented but each time it is called in the game loop, it fails to appear when pressing the keybind.
- **Equipped Gun Indicator**: A box with a text that will show which gun the player is currently using. The box would just not appear despite being inside the game loop.
-**Pause Menu Screen**: The buttons, resume, restart and quit to main menu are presented to the player. Only resume and Quit to main menu did not function as intended. If the player presses resume, only the enemies will move and the player remains statis, only when the user presses space does the game restart back to the first level. On the other hand, quit to main menu made 2 instances of the main menu. Initially the first time I coded this, the alignment of the main menu screen was off resulting in the main menu buttons to not be visible. Even with the main menu aligned now, the buttons of the second instance do not work, but the buttons on the first do.

---

## Features Not Implemented
- **Multiplayer Mode**: A feature to allow multiple players to join the game was planned but not implemented due to time constraints.
- **Settings Menu**: I wanted to make one, but i only had volume and difficulty adjuster in mind, so i didnt prioritize it as mcuh as the other features. Hence it was forgotten.
- **Endless Mode**: A mode of constant enemy aggression without a way to win. Because of that I thought it had no purpose and scrapped the idea.
- **Advanced Shield Animations**: While the shield follows the boss correctly, planned animations for shield activation and deactivation were not fully implemented.
- **Cutscenes**: A short video or gif to immerse the player in the narrative of the game. Due to time constraints I decided agaisnt it.
- **Power ups**: Initially the Player would be abe to access 3 transformtion with each transformation having an individual buff, yellow was the base form, red was the power form which gave you extra 2 hearts, green was speed form which would boost your traversal speed and blue Tiny mode making it easier to dodge enemy projectiles. However due to the difference in design it lead to difference in proportions which made it hard to scale and even harder code. The real problem was the class imageview could not instiate a new instance of another image. Why? I don't know either. But as if that werent enough images just failed to load entirely despite them being able to load before. The same constant error of exception errors drove me to insane for awhile before going back to work on other features instead.
- **Random Pickups** Random floating objects that the player can collect to receive hearts, buffs and/or money. I thought about it but decided agaisnt it as the screen would be too overstimulating especially in the first 2 levels.
- **Boss Health bar and shield bar** An indicator of how much health and shield the boss had before depeltion. I had spent too much time on it for it not being able to work. For some apparent reason it couldnt initialize because it was null. Despite all my efforts, I decided to go with damage indicators and console messages.

---

## New Java Classes
The following Java classes were introduced:
1. **`GunDisplay.java`**:
   - Purpose: Handles the display and update of the current gun name in the game's UI. Provides a VBox container and methods to dynamically update the gun's name and styling.
   - Location: `src/main/java/com/example/demo/Interfaces/`
   - Key Features:
     - Displays the current gun name with customizable font, size, weight, and color.
     - Allows dynamic updates to the displayed gun name.
     - Provides a VBox container for integrating the gun display into the game's UI.
     - Ensures consistent and visually appealing text styling.

2. **`LevelTwo.java`**:
   - Purpose: Implements the second level of the game, managing gameplay mechanics such as enemy spawning, kill tracking, background music, and transitions to the next level.
   - Location: `src/main/java/com/example/demo/Levels/`
   - Key Features:
     - Spawns enemies with configurable probabilities and positions.
     - Tracks player kills and transitions to the next level upon meeting the kill target.
     - Plays level-specific background music, looping indefinitely during gameplay.
     - Handles game-over scenarios, including stopping music and triggering appropriate actions.
     - Customizes the level with a unique background, player health settings, and enemy behavior.
     - Provides smooth transitions to the next level while stopping current music playback.

3. **`EnemyPlaneV2.java`**:
   - Purpose: Represents an upgraded version of an enemy plane with spinning animation and the ability to fire advanced projectiles. Manages movement, firing logic, and visual effects during gameplay.
   - Location: `src/main/java/com/example/demo/Entities/`
   - Key Features:
     - **Projectile System**: Fires advanced projectiles (`EnemyProjectileV2`) towards the player's plane with a probabilistic fire rate.
     - **Movement**: Moves horizontally at a fixed speed, simulating a dynamic flight path.
     - **Damage Handling**: Reduces health when hit and triggers a damage visual effect (e.g., flashing red).
     - **Spinning Animation**: Continuously rotates with a smooth animation for enhanced visual appeal.
     - **Customizable Parameters**: Configurable properties like health, fire rate, image dimensions, and projectile offsets.
     - **Integration with User**: Targets the player's position to calculate precise projectile direction.

4. **`EnemyProjectileV2.java`**:
   - Purpose: Represents an advanced enemy projectile designed to track and follow the player's plane dynamically, adjusting its trajectory based on the player's movements.
   - Location: `src/main/java/com/example/demo/Projectiles/`
   - Key Features:
     - **Target Tracking**: Calculates and adjusts velocity to follow the player's plane in real time.
     - **Customizable Properties**: Configurable image, size, and base velocity for unique gameplay behaviors.
     - **Directional Movement**: Moves towards the calculated direction with both horizontal and vertical components.
     - **Dynamic Velocity Calculation**: Determines the direction vector and normalizes it to maintain consistent speed.
     - **Integration with User Plane**: Actively tracks the position of the player's plane for precise targeting.
     - **Inheritance**: Extends the base `Projectile` class, reusing its foundational properties and methods.

5. **`MainMenu.java`**:
   - Purpose: Implements the main menu scene for the game, including a title, start/exit buttons, background, and music. It provides the initial user interface for starting or exiting the game.
   - Location: `src/main/java/com/example/demo/`
   - Key Features:
     - **Title Animation**: A pulsating effect for the game title ("Sky Survivor") to attract attention.
     - **Background Image**: Displays an animated background image to enhance visual appeal.
     - **Start and Exit Buttons**:
       - **Start Button**: Transitions to the first game level and stops background music.
       - **Exit Button**: Closes the application.
       - Both buttons include hover effects for a polished UI.
     - **Background Music**:
       - Plays a looping soundtrack (`MainMenuOST.mp3`) for the main menu.
       - Music stops when transitioning to the game or exiting the menu.
     - **Scene Integration**:
       - Extends `Scene_Properties` for seamless compatibility with the game’s scene management system.
       - Defines methods like `initializeScene` and placeholders for unused gameplay logic (e.g., enemy spawns).
     - **Responsiveness**:
       - Adapts the layout to fit specified screen dimensions.
       - Ensures interactive elements are styled and positioned for usability.

---

## Unit Tests

1. **`LevelBossTest.java`**:
   - **Purpose**: Tests the functionality of the `LevelBoss` class, including friendly unit initialization, game-over conditions, enemy spawning, and music playback.
   - **Details**:
     - **Validates the initialization of friendly units**, ensuring that the player is added to the scene correctly.
     - **Tests game-over conditions** by checking if the game ends when the user or the boss is destroyed.
     - **Ensures the boss is spawned properly** when there are no enemies present, confirming that the boss is added to the level.
     - **Verifies music playback** by ensuring background music starts and stops correctly during level transitions.
     - **Validates proper functioning of the `stopMusic` method**, ensuring that music stops when necessary.

2. **`MainMenuTest.java`**:
   - **Purpose**: Tests the functionality of the `MainMenu` class, including scene setup, button actions, background music, and layout.
   - **Details**:
     - **Validates the scene initialization**, ensuring the background image and buttons are correctly displayed.
     - **Tests the "Start Game" button action**, ensuring that it stops the music and notifies the controller to load the next level.
     - **Verifies the "Exit" button action**, ensuring that the application exits when clicked.
     - **Confirms the background music starts correctly** and loops indefinitely when the main menu is displayed.
     - **Tests the pulsating effect** on the title text, ensuring it appears and fades in a smooth loop.
     - **Validates button styling**, checking hover effects and ensuring buttons have consistent appearance.

3. **`UserPlaneTest.java`**:
   - **Purpose**: Tests the functionality of the `UserPlane` class, including movement, projectile firing, gun switching, and boundary enforcement.
   - **Details**:
     - **Validates plane movement**, ensuring the plane moves correctly in all four directions and that it obeys screen boundaries.
     - **Tests projectile firing** for each gun type (Pistol, Shotgun, Minigun), checking that projectiles are fired with the correct cooldown.
     - **Verifies gun switching functionality**, ensuring that the user can switch between different gun types and that the corresponding projectiles are fired.
     - **Confirms boundary enforcement**, ensuring that the plane doesn't move outside the predefined screen boundaries.
     - **Checks cooldown timing**, ensuring the plane's guns do not fire too frequently based on the specified cooldown times.
     - **Validates sound playing** for each type of gun fire, ensuring the correct sound is played when firing.

4. **`EnemyProjectileV2Test.java`**:
   - **Purpose**: Tests the functionality of the `EnemyProjectileV2` class, including trajectory calculations, movement, and updates based on the user plane's position.
   - **Details**:
     - **Validates projectile tracking**, ensuring that the projectile correctly calculates the direction towards the user plane and updates its velocity accordingly.
     - **Tests trajectory calculations**, ensuring the target velocity is calculated based on the difference in positions between the projectile and the user plane.
     - **Verifies movement towards the user plane**, ensuring the projectile moves in the correct direction (both horizontally and vertically) towards the user plane.
     - **Confirms position update functionality**, ensuring the projectile's position updates correctly based on its calculated velocity every frame.
     - **Validates constructor behavior**, ensuring that the `EnemyProjectileV2` constructor correctly initializes the projectile with the proper starting position and calculates the necessary target velocity.

5. **`BossTest.java`**:
   - **Purpose**: Tests the functionality of the `Boss` class, including its movement patterns, projectile firing, shield toggling, and damage handling.
   - **Details**:
     - **Validates movement pattern**, ensuring that the boss moves according to the predefined pattern, shuffling the movement directions, and respecting the movement bounds.
     - **Tests shield activation and deactivation**, ensuring that the shield toggles every 5 seconds and correctly prevents damage when active.
     - **Verifies projectile firing**, ensuring that the boss fires projectiles at the user plane at a rate determined by the fire rate.
     - **Checks damage handling**, ensuring that the boss takes damage only when the shield is inactive, and no damage is taken when the shield is active.
     - **Confirms health management**, ensuring that the boss's health is correctly initialized and updated during gameplay.
     - **Tests the visual shield indicator**, ensuring that it displays the correct shield status (ON/OFF) based on the current shield state.
     - **Verifies interaction with `LevelBoss_Interface`**, ensuring that the boss interacts correctly with the level system for game progression.


6. **`EnemyPlaneV2Test.java`**:
   - **Purpose**: Tests the functionality of the `EnemyPlaneV2` class, including its spinning animation, movement, projectile firing, and damage handling.
   - **Details**:
     - **Validates spinning animation**, ensuring that the enemy plane rotates continuously in a smooth and infinite loop.
     - **Tests horizontal movement**, ensuring the plane moves left at a fixed speed, consistent with the specified velocity.
     - **Verifies projectile firing**, ensuring that the enemy plane fires projectiles towards the player’s plane based on the fire rate probability.
     - **Checks damage handling**, ensuring that the plane's health is reduced when it takes damage, and that visual effects like flashing are triggered.
     - **Confirms the health initialization**, ensuring that the enemy plane starts with the correct health value.
     - **Validates fire rate**, ensuring that the probability of firing a projectile follows the specified fire rate.

---

## The Important Tests!

- **`EnemyPlaneV2Test.java`**:
   - **Verifies projectile firing**, ensuring that the enemy plane fires projectiles towards the player’s plane based on the fire rate probability.
     - **Checks damage handling**, ensuring that the plane's health is reduced when it takes damage, and that visual effects like flashing are triggered.
     - **Confirms the health initialization**, ensuring that the enemy plane starts with the correct health value.
     - **Validates fire rate**, ensuring that the probability of firing a projectile follows the specified fire rate.

**`Level_Interface.java`**:
   - **Purpose**: Manages and updates the user interface (UI) elements related to a specific game level, including heart displays, win/loss images, and kill count updates.
   - **Location**: `src/main/java/com/example/demo/Interfaces/`
   
   - **Key Functions**:
     - **Heart Display**: Displays the number of remaining hearts (lives) the player has.
     - **Win Image**: Shows a win image when the player completes the level.
     - **Game Over Image**: Shows a game over image when the player loses the game.
     - **Kill Counter**: Displays the number of kills required to advance to the next level and updates this count as the player progresses.
     - **Heart Removal**: Updates the heart display when the player loses hearts.
     - **Kill Count Update**: Reflects the player’s progress towards the kill goal.
     - **Interactive UI Management**: Adds or removes UI elements dynamically based on the level's status and the player's actions.

   - **Usage**:
     - This class is used to track the player's progress through the level, display necessary UI elements, and interact with the root scene. It ensures that key game metrics like kills and health are visible and updated.

- **`BossTest.java`**:
   - **Confirms health management**, ensuring that the boss's health is correctly initialized and updated during gameplay.
     - **Tests the visual shield indicator**, ensuring that it displays the correct shield status (ON/OFF) based on the current shield state.
     - **Verifies interaction with `LevelBoss_Interface`**, ensuring that the boss interacts correctly with the level system for game progression.

- **`MainMenuTest.java`**:
   - **Validates the scene initialization**, ensuring the background image and buttons are correctly displayed.
  
- **`UserPlaneTest.java`**:
   - **Validates plane movement**, ensuring the plane moves correctly in all four directions and that it obeys screen boundaries.
   - **Tests projectile firing** for each gun type (Pistol, Shotgun, Minigun), checking that projectiles are fired with the correct cooldown.
   - **Verifies gun switching functionality**, ensuring that the user can switch between different gun types and that the corresponding projectiles are fired.

---

## Modified Java Classes
The following Java classes were modified, along with reasons for the changes:

1. **`Scene_Properties.java`**:
- **Purpose**: This file is responsible for initializing the fundamental aspects of each game level. It establishes the core properties that define how the game behaves at each level, ensuring consistency across different stages. This includes setting up game loops, the environment, and gameplay rules.
- **Key Changes**:
  - Defined the standard behavior and layout for all levels, creating a clear structure for future level development.
  - Allowed easy accommodation of additional features, such as new enemies, obstacles, or level-specific mechanics.
  - Centralized where the most crucial gameplay logic occurs, ensuring easy scalability.

2. **`Main.java`**:
- **Purpose**: The main class initializes the game by giving it a title and launching the first level. This is the entry point of the game where the game window is configured, and the overall setup takes place.
- **Key Changes**:
  - Set the name of the game, which will be visible in the window title bar.
  - Established the main game loop and entry point for launching the game.

3. **`Controller.java`**:
- **Purpose**: The controller manages level transitions, game state handling, and window setup. It ensures the game progresses logically from one level to the next without issues, while also handling key gameplay functions like window resizing and full-screen mode.
- **Key Changes**:
  - Ensured that the game window remains maximized in full-screen mode throughout gameplay for an immersive experience.
  - Automatically rescales the background image when transitioning between different screen resolutions, ensuring the game visuals remain consistent.

4. **`EnemyPlaneV1.java`**:
- **Purpose**: This class defines the behavior and attributes of enemy planes, which act as obstacles for the player. The main goal is to make the enemy planes behave in a way that challenges the player without being overly simplistic.
- **Key Changes**:
  - Integrated a continuous spinning animation to give the enemies a more dynamic and unpredictable movement pattern.
  - Enhanced the firing mechanism with more varied shooting patterns, making the gameplay more challenging and engaging.
  - Ensured that the enemies follow a constant forward motion, creating a predictable but challenging path for the player to avoid.

5. **`FighterPlane.java`**:
- **Purpose**: The FighterPlane class acts as the base class for all entities within the game, providing the foundation for common behavior such as movement and collision detection.
- **Key Changes**:
  - Implemented collision detection to track and handle interactions between entities.
  - Added visual indicators (such as flashing effects) to show when an entity has been hit, enhancing feedback during gameplay.

6. **`UserPlane.java`**:
- **Purpose**: This class represents the player's plane, giving them control over their movements and attacks. It also introduces different weapons and shooting methods, allowing the player to customize their gameplay experience.
- **Key Changes**:
  - Added an alternate fire mode to provide variety in attack strategies, allowing the player to choose the best method of offense depending on the situation.

7. **`Level_Interface.java`**:
- **Purpose**: This file is responsible for ensuring that all user interface (UI) elements, such as the kill counter and heart display, function properly within the game. It manages UI components based on the player's progress in the game.
- **Key Changes**:
  - Ensured that the kill counter and heart display elements are updated correctly as the game progresses, providing real-time feedback on the player's performance.

8. **`LevelBoss.java`**:
- **Purpose**: This class is responsible for increasing the difficulty of the game during the boss level. It creates a challenging experience for the player by introducing a boss encounter with unique mechanics and visual/audio effects.
- **Key Changes**:
  - Introduced special music and sound effects to highlight the intensity of the boss fight and provide a more immersive experience.
  - Added unique behavior for the boss to create a more difficult and engaging encounter compared to previous levels.

9. **`LevelTwo.java`**:
- **Purpose**: This level introduces a more challenging set of enemies and gameplay mechanics to increase the difficulty and encourage strategic thinking. The enemies also become more intelligent, enhancing the overall challenge.
- **Key Changes**:
  - Introduced a new type of enemy with more advanced behavior, creating a more challenging environment for the player.
  - The layout and enemy patterns were modified to force the player to adapt and think more strategically.

10. **`LevelOne.java`**:
- **Purpose**: This is the introductory level, designed to familiarize players with the types of enemies they will encounter throughout the game. The first level is usually easier to hook the player into the game.
- **Key Changes**:
  - Introduced basic enemy types to give players a taste of the challenge to come while keeping the gameplay manageable and fun.

11. **`EnemiesProjectileV2.java`**:
- **Purpose**: This file introduces a new type of projectile that seeks out the player's position, unlike the traditional straight-moving projectiles. It adds variety to the enemy attacks and enhances gameplay by increasing the unpredictability of projectiles.
- **Key Changes**:
  - Enabled projectiles to actively track and home in on the player's last known position, making the game more challenging as players need to continuously move to avoid being hit.

12. **`UserProjectile.java`**:
- **Purpose**: This file introduces the player's various weapon types and shooting methods, offering more flexibility and strategy in combat.
- **Key Changes**:
  - Implemented different weapon modes with distinct characteristics, such as rate of fire, projectile speed, and damage output, allowing players to select the most suitable weapon for different scenarios.

13. **`Module-info.java`**:
    - **Purpose**: Updated to include new modules and dependencies.
    - **Key Changes**:
      - Added JavaFX module configurations.
      - Updated exports for newly introduced classes and packages.

14. **`HeartDisplay.java`**:
   - **Purpose**: Provides a visual representation of the player's health using heart icons.
   - **Key Changes**:
     - Dynamically updates the heart icons to reflect the player's current health status.
     - Implemented an `HBox` container for efficient layout and rendering of heart images.
     - Introduced methods to remove hearts as the player takes damage.
     - Adjusted the size and positioning of the heart images to fit within the game's UI seamlessly.

15. **`Boss.java`**:
   - **Purpose**: Enhanced to support advanced boss behaviors such as, projectile firing, and improved movement patterns.
   - **Key Changes**:
     - Integrated projectile firing mechanism with fast tracking projectiles.
     - Enhanced movement patterns to handle edge cases.

---

## Unexpected Problems

1. **Shield Mechanism**:
    - The shield mechanism wasn't working properly with the boss. Despite reinitializing the shield class and method, I encountered issues with its functionality. While I was able to make the shield work in terms of its mechanics, the visual aspect of the shield not appearing was a challenge. Changing the boss's image also didn't resolve the issue. As a workaround, I implemented a solution where the damage indicator wouldn't activate while the shield was active, ensuring that no damage would be reflected until the shield was gone.

2. **Boss Health Bar**:
    - The boss health bar became a persistent problem, often resulting in null exceptions despite numerous attempts to implement it. Instead of continuing to face these errors, I introduced a damage indicator as a workaround, allowing players to still see if they were dealing damage to the boss even though the health bar itself wasn't functioning.

3. **Different Gun Types**:
    - This feature posed a significant challenge but was ultimately one of the aspects I am most proud of. Initially, understanding how projectiles worked and how they were fired was difficult. I struggled with configuring their direction, as my early attempts resulted in projectiles shooting upwards from below the window border. After much trial and error, I was able to achieve the desired functionality, enabling different gun types to behave as intended.

4. **Gun Box for Current Gun Indicator**:
    - Initially, I was able to implement the feature where the active gun type was displayed in a box at the bottom center of the screen. However, an issue arose when I used an event handler for the gun keybinds, which inadvertently affected the movement controls as well. Since the keybinds were initialized simultaneously, this resulted in choppy movement, non-functional projectiles, and difficulty navigating the game. I had to address this problem, sacrificing gameplay fluidity in the process.

5. **Screen Resolution**:
    - Screen resolution and fullscreen mode posed an unexpected challenge. Whenever the player advanced to the next level, the fullscreen mode would reinitialize, causing the screen to exit fullscreen and resize, accompanied by the annoying "press F11 to exit fullscreen" message. After considerable effort, I found a solution by reinitializing the fullscreen windowed mode, which resolved the issue and ensured a smoother transition between levels.

6. **Pause Overlay and Main Menu**:
    - Initially, the pause overlay was implemented with a keybind to pause and unpause the game. I later added a mini main menu within the pause screen to give users more control. While the implementation itself was straightforward, I encountered several issues. The most notable problem was that when resuming the game, only certain entities resumed their behavior while the player’s plane was immobile. Additionally, the "Quit to Main Menu" button caused the background image to distort, rendering the main menu screen inoperable. I tried multiple techniques to prevent the background image from running off-screen, but it didn't work, so I resorted to launching a second instance of the main menu, which was not ideal.

7. **Projectiles**:
    - I aimed to create projectiles that could do more than just move in a straight line, and I succeeded in developing projectiles that actively tracked the player's position. This was a challenge, and I was one of the first among my peers to achieve this. The most difficult part was figuring out why the position of the active actor couldn't be passed to the enemy class. After considerable effort, I was able to make this functionality work, and while I'm not entirely sure how I accomplished it, I am proud of the outcome.

8. **UserPlane Movement**:
    - I wanted to introduce more dynamic movement for the player, incorporating vertical movement as well as horizontal. Initially, I didn't expect this to be such a crucial element. Passing coordinates for the active actors turned out to be difficult, and I ran into issues where certain methods could access the coordinates while others couldn't. After resolving this, I focused on smoothing out the movement, but I inadvertently made it too smooth, causing the plane to glide. After much adjustment, I left it as an intended feature, providing a unique gameplay experience.

9. **Scene_Properties**:
    - The `Scene_Properties` class, which controls the game loop, became more integral to the game’s structure than I initially realized. It was challenging to keep track of what methods were being passed or received, and I didn’t realize I had accumulated over 600 lines of code. Refactoring this class to manage the game’s flow properly took two full days, but I eventually got it working as it is today. This experience taught me the importance of careful planning and managing data efficiently in the future.

By addressing these unexpected issues, the game achieved a stable and functional state, though further refinement could improve some aspects.


---

## Summary
This project introduces several new features and enhances the existing codebase to improve scalability and maintainability. Although some features are still incomplete, the implemented functionalities fulfill the primary requirements of the coursework. Future iterations can focus on addressing the remaining issues and implementing further improvements.
