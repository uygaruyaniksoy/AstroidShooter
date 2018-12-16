package com.group6.frontend.view.LeaderBoardTabs;

import com.group6.frontend.controller.LeaderBoardController;
import com.group6.frontend.model.entities.webConsumer.LeaderBoardDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GeneralLeaderBoardTab {
    private final TabPane tabPane;
    private final LeaderBoardController controller;
    private final ObservableList<LeaderBoardDTO> data;

    public GeneralLeaderBoardTab(TabPane tabPane, LeaderBoardController controller) {
        this.tabPane = tabPane;
        this.controller = controller;
        data = FXCollections.observableArrayList();
    }


    public void handle() {
        Tab tab = new Tab();
        tab.setText("Leader Board");
        tab.setClosable(false);

        tab.setOnSelectionChanged(e->{




            if(tab.isSelected()) {
                controller.tabChangedHandler(data,"scoreboard/");
                TableView table = new TableView();

                final Label label = new Label("General Leader Board");
                label.setFont(new Font("Arial", 20));
                table.setEditable(false);

                TableColumn usernameCol = new TableColumn("NickName");
                usernameCol.setMinWidth(100);

                TableColumn scoreCol = new TableColumn("Score");
                scoreCol.setMinWidth(100);

                usernameCol.setCellValueFactory(
                        new PropertyValueFactory<LeaderBoardDTO,String>("NickName")
                );

                scoreCol.setCellValueFactory(
                        new PropertyValueFactory<LeaderBoardDTO,String>("Score")
                );

                table.setItems(data);
                table.getColumns().addAll(usernameCol, scoreCol);

                final VBox vbox = new VBox();
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(10, 0, 0, 10));
                vbox.getChildren().addAll(label, table);

                Button button =  new Button("Back");
                button.setOnMouseClicked(mouseEvent -> {
                    controller.back();
                });
                vbox.getChildren().add(button);

                tab.setContent(vbox);
            }
        });
        tabPane.getTabs().add(tab);
    }
}
