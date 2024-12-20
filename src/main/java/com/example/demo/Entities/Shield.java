package com.example.demo.Entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Shield extends ImageView {

    private static final String IMAGE_NAME = "/com/example/demo/images/energyShield1.png";
    private static final int SHIELD_SIZE = 200;

    public Shield(double xPosition, double yPosition) {
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        try {
            String resourcePath = getClass().getResource(IMAGE_NAME).toExternalForm();
            this.setImage(new Image(resourcePath));
        } catch (NullPointerException e) {
            throw new IllegalStateException("Resource not found: " + IMAGE_NAME, e);
        }
        this.setVisible(false);
        this.setFitHeight(SHIELD_SIZE);
        this.setFitWidth(SHIELD_SIZE);
    }


    public void showShield() {
        this.setVisible(true);
    }

    public void hideShield() {
        this.setVisible(false);
    }

}