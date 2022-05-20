package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.Util;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.StringInputDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.EditableArmyInfoElement;
import org.ntnu.vsbugge.wargames.gui.guielements.windowelements.EditableArmyWindowElement;
import org.ntnu.vsbugge.wargames.utils.files.ArmyFileUtil;

import java.io.File;
import java.io.IOException;

public class EditArmiesPageController {
    private final EditableArmyWindowElement armyWindow = new EditableArmyWindowElement();
    private Army army = null;
    private File armyFile = null;
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
        armyFile = Util.pickArmyFileToOpen(((Node) event.getSource()).getScene().getWindow());

        if (armyFile == null) {
            army = null;
        } else {
            army = Util.parseArmyFileAndAlertUserIfUnsuccessful(armyFile);
        }

        armyWindow.setArmy(army);
        armyWindow.reset();
    }

    @FXML
    void onSave(ActionEvent event) {
        army = armyWindow.getArmy();

        if (armyFile == null) {
            onSaveAs(event);
            return;
        }

        ArmyFileUtil fileUtil = new ArmyFileUtil();
        try {
            fileUtil.saveArmyToPath(army, armyFile, true);
            AlertFactory.createAlert(
                    Alert.AlertType.INFORMATION, "The army '" + army.getName() + "' was saved successfully!",
                    "Army saved!").show();
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
        }
    }

    @FXML
    void onSaveAs(ActionEvent event) {
        if (army == null) {
            var e = new IllegalArgumentException("Cannot save non-existent army..");
            AlertFactory.createExceptionErrorAlert(e).show();
            return;
        }

        armyFile = Util.pickSaveAsArmyFile(((Node) event.getSource()).getScene().getWindow());
        if (armyFile == null) {
            return;
        }

        onSave(event);
    }

    @FXML
    void onNew(ActionEvent event) {
        army = new Army("Army name...");
        armyWindow.setArmy(army);
        armyWindow.reset();

        // Selects army name;
        var armyInfo = (EditableArmyInfoElement) armyWindow.getChildren().get(0);
        var topRow = (HBox) armyInfo.getChildren().get(0);
        var armyName = (Label) topRow.getChildren().get(1);

        var onDoubleClick = new StringInputDoubleClickSwapper(armyName, armyInfo::setArmyName);
        onDoubleClick.runsIfDoubleClick();
    }

    @FXML
    void initialize() {
        armyWrapper.getChildren().add(armyWindow);
    }
}
