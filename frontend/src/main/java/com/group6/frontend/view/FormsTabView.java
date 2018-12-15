package com.group6.frontend.view;

import com.group6.frontend.controller.SigninController;
import com.group6.frontend.controller.SignupController;
import com.group6.frontend.view.AbstractView;
import com.group6.frontend.view.FormTabs.SigninTab;
import com.group6.frontend.view.FormTabs.SignupTab;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FormsTabView extends AbstractView {
    public FormsTabView(Stage stage) {
        super(stage);
    }

    @Override
    public Scene getScene() {
        Scene scene = super.getScene();
        Pane root = (Pane) scene.getRoot();

        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();

        SignupController signupController = new SignupController(stage);
        SignupTab signupTab = new SignupTab(tabPane,signupController);
        signupTab.handle();

        SigninController signinController = new SigninController(stage);
        SigninTab signinTab = new SigninTab(tabPane,signinController);
        signinTab.handle();

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);

        return scene;
    }
}
