package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;

public class LaunchPageController {

    @FXML
    void onBattleSimulation(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "simulateBattle.fxml");
    }

    @FXML
    void onEditArmies(ActionEvent event) {
        AlertFactory.createWarning("This page has not yet been implemented", "Not implemented!").show();
    }
}
