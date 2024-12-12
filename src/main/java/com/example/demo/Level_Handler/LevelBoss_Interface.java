package com.example.demo.Level_Handler;

import com.example.demo.ShieldImage;
import javafx.scene.Group;

public class LevelBoss_Interface extends Level_Interface {

    private static final int SHIELD_X_POSITION = 1150;
    private static final int SHIELD_Y_POSITION = 500;
    
    private final Group root;
    private final ShieldImage shieldImage;

    public LevelBoss_Interface(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay, heartsToDisplay);
        this.root = root;
        this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
        initializeShield();
    }

    // Adds the shield image to the root
    private void initializeShield() {
        root.getChildren().add(shieldImage);
    }

    // Shows the shield by making it visible
    public void showShield() {
        shieldImage.showShield();
    }

    // Hides the shield by making it invisible
    public void hideShield() {
        shieldImage.hideShield();
    }
}
