package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.utils.config.Settings;

import java.io.IOException;

/**
 * The controller for the launch page.
 *
 * @author vsbugge
 */
public class LaunchPageController {

    /**
     * A method called when the 'simulate battle' button is pressed.
     *
     * @param event
     *            The action event form the button pres.
     */
    @FXML
    void onBattleSimulation(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "simulateBattle.fxml");
    }

    /**
     * A method called when the 'simulate battle' button is pressed.
     *
     * @param event
     *            The action event form the button pres.
     */
    @FXML
    void onEditArmies(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "editArmies.fxml");

        // Display tutorial if it has not been shown already.
        Settings config = null;
        try {
            config = Settings.readConfig();
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
        } finally {
            if (config == null || config.isEditArmiesTutorial()) {
                Stage stage = new Stage();
                GUI.setInitialSceneOfStage(stage, "editArmiesTutorial.fxml", false);
                stage.setOnCloseRequest(windowEvent -> {
                    EditArmiesTutorialController.markTutorialComplete();
                    stage.close();
                });
            }
        }
    }

    @FXML
    void onSettings(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "settingsPage.fxml");
    }
}
