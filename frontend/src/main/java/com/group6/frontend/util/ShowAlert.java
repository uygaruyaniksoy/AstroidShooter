package com.group6.frontend.util;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class ShowAlert {

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
