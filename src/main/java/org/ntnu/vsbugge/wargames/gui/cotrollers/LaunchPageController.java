package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;

/**
 * The controller for the launch page.
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
        AlertFactory.createAlert(Alert.AlertType.ERROR, "This page has not yet been implemented", "Not implemented!")
                .show();
    }
}
