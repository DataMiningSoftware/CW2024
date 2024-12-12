package com.example.demo;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GunDisplay {
    private final VBox gunBox;
    private final Text gunText;

    public GunDisplay() {
        this.gunBox = new VBox();
        this.gunText = new Text("Gun: PISTOL"); // Default value
        gunText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gunText.setFill(Color.WHITE);
        gunBox.getChildren().add(gunText);
    }

    public void updateGunDisplay(String gunName) {
        gunText.setText("Gun: " + gunName);
    }

    public VBox getGunBox() {
        return gunBox;
    }

    public Text getGunText() {
        return gunText;
    }
}


