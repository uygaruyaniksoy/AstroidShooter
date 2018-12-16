package com.group6.frontend.view;

import com.group6.frontend.controller.LeaderBoardController;
import com.group6.frontend.controller.SigninController;
import com.group6.frontend.controller.SignupController;
import com.group6.frontend.view.AbstractView;
import com.group6.frontend.view.FormTabs.SigninTab;
import com.group6.frontend.view.FormTabs.SignupTab;
import com.group6.frontend.view.LeaderBoardTabs.GeneralLeaderBoardTab;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LeaderBoardTabView extends AbstractView {
    public LeaderBoardTabView(Stage stage) {
        super(stage);
    }

    @Override
    public Scene getScene() {
        Scene scene = super.getScene();
        Pane root = (Pane) scene.getRoot();

        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();

        LeaderBoardController leaderBoardController = new LeaderBoardController(stage);
        GeneralLeaderBoardTab generalLeaderBoardTab = new GeneralLeaderBoardTab(tabPane,leaderBoardController);
        generalLeaderBoardTab.handle();

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);

        return scene;
    }
}
