package com.group6.frontend.view.LeaderBoardTabs;

import com.group6.frontend.controller.LeaderBoardController;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class GeneralLeaderBoardTab {
    private TabPane tabPane;
    private LeaderBoardController controller;

    public GeneralLeaderBoardTab(TabPane tabPane, LeaderBoardController controller) {
        this.tabPane = tabPane;
        this.controller = controller;
    }

    public void handle() {
        Tab tab = new Tab();
        tab.setText("Sign in");
        tab.setClosable(false);

    }

}
