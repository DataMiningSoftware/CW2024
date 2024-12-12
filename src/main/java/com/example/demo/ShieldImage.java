package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	private static final int SHIELD_SIZE = 200;
	private static final String DEFAULT_IMAGE_PATH = "/com/example/demo/images/spacecircle_red.png";

	public ShieldImage(double xPosition, double yPosition) {
	    this(xPosition, yPosition, DEFAULT_IMAGE_PATH);
	}

	public ShieldImage(double xPosition, double yPosition, String imagePath) {
	    this.setLayoutX(xPosition);
	    this.setLayoutY(yPosition);
	    this.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
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
