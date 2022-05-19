package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.Util;
import org.ntnu.vsbugge.wargames.gui.guielements.windowelement.EditableArmyWindowElement;

public class EditArmiesPageController {
    private EditableArmyWindowElement armyWindow = new EditableArmyWindowElement();
    @FXML
    private VBox armyWrapper;

    @FXML
    private Button homeButton;

    @FXML
    private Button importButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button saveAsButton;

    @FXML
    private Button newButton;

    @FXML
    void onHome(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "launchPage.fxml");
    }

    @FXML
    void onImport(ActionEvent event) {
        Army army = Util.pickArmy(((Node) event.getSource()).getScene().getWindow());
        armyWindow.setArmy(army);
        armyWindow.reset();
    }

    @FXML
    void onSave(ActionEvent event) {

    }

    @FXML
    void onSaveAs(ActionEvent event) {

    }

    @FXML
    void onNew(ActionEvent event) {
        Army army = new Army("Army name...");
        armyWindow.setArmy(army);
        armyWindow.reset();
    }

    @FXML
    void initialize() {
        armyWrapper.getChildren().add(armyWindow);
    }
}
