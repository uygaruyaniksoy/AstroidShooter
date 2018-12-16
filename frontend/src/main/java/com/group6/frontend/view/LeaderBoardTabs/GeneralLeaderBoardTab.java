package com.group6.frontend.view.LeaderBoardTabs;

import com.group6.frontend.controller.LeaderBoardController;
import com.group6.frontend.model.entities.webConsumer.LeaderBoardDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;

public class GeneralLeaderBoardTab {
    private TabPane tabPane;
    private LeaderBoardController controller;
    private ObservableList<LeaderBoardDTO> data;

    public GeneralLeaderBoardTab(TabPane tabPane, LeaderBoardController controller) {
        this.tabPane = tabPane;
        this.controller = controller;
        data = FXCollections.observableArrayList();
    }


    public void handle() {
        Tab tab = new Tab();
        tab.setText("Leader Board");
        tab.setClosable(false);

        TableView table = new TableView();

        final Label label = new Label("General Leader Board");
        label.setFont(new Font("Arial", 20));
        table.setEditable(false);

        TableColumn usernameCol = new TableColumn("Username");
        usernameCol.setMinWidth(100);

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setMinWidth(100);

        usernameCol.setCellValueFactory(
                new PropertyValueFactory<LeaderBoardDTO,String>("Username")
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

        tab.setContent(vbox);


        tab.setOnSelectionChanged(e->{
            if(tab.isSelected()) {
                controller.tabChangedHandler(data,"scoreboard/");
            }
        });

        tabPane.getTabs().add(tab);
    }

}
