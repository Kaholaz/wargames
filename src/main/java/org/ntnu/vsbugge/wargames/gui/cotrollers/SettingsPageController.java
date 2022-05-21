package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.utils.config.RenderFrequencyEnum;
import org.ntnu.vsbugge.wargames.utils.config.Settings;
import org.ntnu.vsbugge.wargames.utils.config.SimulationSpeedEnum;

import java.io.IOException;

/**
 * The controller for the simulation setting page.
 *
 * @author vsbugge
 */
public class SettingsPageController {
    private Settings config;

    @FXML
    private Button homeButton;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<SimulationSpeedEnum> simulationSelector;

    @FXML
    private ComboBox<RenderFrequencyEnum> refreshSelector;

    @FXML
    void onHome(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "launchPage.fxml");
    }

    @FXML
    void onSave(ActionEvent event) {
        config.setRenderFrequency(refreshSelector.getValue());
        config.setSimulationSpeed(simulationSelector.getValue());

        try {
            config.saveConfig();
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
            return;
        }

        AlertFactory.createAlert(Alert.AlertType.INFORMATION, "Settings saved!", "Success!").show();
    }

    @FXML
    void initialize() {
        try {
            config = Settings.readConfig();
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
            config = new Settings();
        }

        simulationSelector.getItems().addAll(SimulationSpeedEnum.values());
        simulationSelector.getSelectionModel().select(config.getSimulationSpeed());

        refreshSelector.getItems().addAll(RenderFrequencyEnum.values());
        refreshSelector.getSelectionModel().select(config.getRenderFrequency());
    }
}
