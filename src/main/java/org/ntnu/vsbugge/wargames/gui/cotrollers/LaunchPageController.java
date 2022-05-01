package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.ntnu.vsbugge.wargames.gui.GUI;

public class LaunchPageController {

    @FXML
    void onBattleSimulation(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "simulateBattle.fxml");
    }

    @FXML
    void onEditArmies(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "editArmies.fxml");
    }
}
