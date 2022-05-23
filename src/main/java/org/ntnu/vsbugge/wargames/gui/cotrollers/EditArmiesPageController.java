package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ntnu.vsbugge.wargames.gui.ArmyFilePickerUtil;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.StringInputDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.EditableArmyInfoElement;
import org.ntnu.vsbugge.wargames.gui.guielements.windowelements.EditableArmyWindowElement;
import org.ntnu.vsbugge.wargames.models.army.Army;
import org.ntnu.vsbugge.wargames.utils.config.Settings;
import org.ntnu.vsbugge.wargames.utils.files.ArmyFileUtil;

import java.io.File;
import java.io.IOException;

/**
 * The controller for the edit armies page.
 *
 * @author vsbugge
 */
public class EditArmiesPageController {
    private final EditableArmyWindowElement armyWindow = new EditableArmyWindowElement();
    private Army army = null;
    private File armyFile = null;

    @FXML
    private VBox armyWrapper;

    /**
     * Method called when the 'home' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    @FXML
    void onHome(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "launchPage.fxml");
    }

    /**
     * Method called when the 'import' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    @FXML
    void onImport(ActionEvent event) {
        armyFile = ArmyFilePickerUtil.pickArmyFileToOpen(((Node) event.getSource()).getScene().getWindow());

        if (armyFile == null) {
            return;
        }

        army = ArmyFilePickerUtil.parseArmyFileAndAlertUserIfUnsuccessful(armyFile);

        armyWindow.setArmy(army);
        armyWindow.reset();
    }

    /**
     * Method that is called when the 'save' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    @FXML
    void onSave(ActionEvent event) {
        army = armyWindow.getArmy(); // Gets the currently displayed army

        if (armyFile == null) {
            onSaveAs(event);
            return;
        }

        ArmyFileUtil fileUtil = new ArmyFileUtil();
        try {
            fileUtil.saveArmyToPath(army, armyFile, true);
            AlertFactory.createAlert(Alert.AlertType.INFORMATION,
                    "The army '" + army.getName() + "' was saved successfully!", "Army saved!").show();
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
        }
    }

    /**
     * The method called when the 'save as...' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    @FXML
    void onSaveAs(ActionEvent event) {
        if (army == null) {
            var e = new IllegalArgumentException("Cannot save non-existent army..");
            AlertFactory.createExceptionErrorAlert(e).show();
            return;
        }

        armyFile = ArmyFilePickerUtil.pickSaveAsArmyFile(((Node) event.getSource()).getScene().getWindow());
        if (armyFile == null) {
            return;
        }

        onSave(event);
    }

    /**
     * The method called when the 'new' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    @FXML
    void onNew(ActionEvent event) {
        army = new Army("Army name...");
        armyWindow.setArmy(army);
        armyWindow.reset();

        var armyInfo = (EditableArmyInfoElement) armyWindow.getChildren().get(0);
        Label armyName = armyInfo.getTopLabel();

        var onDoubleClick = new StringInputDoubleClickSwapper(armyName, armyInfo::setArmyName);
        onDoubleClick.runsIfDoubleClick();
    }

    /**
     * Launches the tutorial
     */
    void launchTutorial() {
        Stage stage = new Stage();
        GUI.setInitialSceneOfStage(stage, "editArmiesTutorial.fxml", false);
        stage.setOnCloseRequest(windowEvent -> {
            EditArmiesTutorialController.markTutorialComplete();
            stage.close();
        });
    }

    /**
     * Method called directly after the FXML page is loaded.
     */
    @FXML
    void initialize() {
        armyWrapper.getChildren().add(armyWindow);

        Settings config = null;
        try {
            config = Settings.readConfig();
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
            config = Settings.getDefaultConfig();
        }

        // Display tutorial if it has not been shown already.
        if (config.isEditArmiesTutorial()) {
            Platform.runLater(this::launchTutorial);
        }
    }
}
