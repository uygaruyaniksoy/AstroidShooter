package com.group6.frontend.view;

import com.group6.frontend.controller.MainMenuViewController;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenuView extends AbstractView {
    private MainMenuViewController controller;
    public MainMenuView(Stage stage) {
        super(stage);

        controller = new MainMenuViewController(stage);
    }

    @Override
    public Scene getScene() {
        Scene scene = super.getScene();

        Pane root = (Pane) scene.getRoot();

        TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();



        SignupScreen signupScreen = new SignupScreen(tabPane, controller );
        signupScreen.handle();

//        for (int i = 0; i < 2; i++) {
//            Tab tab = new Tab();
//            tab.setText("Tab" + i);
//            tab.setClosable(false);
//
//            HBox hbox = new HBox();
//            hbox.getChildren().add(new Label("Tabbing" + i));
//            hbox.setAlignment(Pos.CENTER);
//            tab.setContent(hbox);
//            tabPane.getTabs().add(tab);
//        }
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);


        return scene;
    }
}
