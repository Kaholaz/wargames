package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.gui.decorators.PaddingDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.EditableArmyInfoElement;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.EditableUnitInfoElement;
import org.ntnu.vsbugge.wargames.units.CavalryUnit;
import org.ntnu.vsbugge.wargames.units.Unit;
import org.ntnu.vsbugge.wargames.utils.config.Settings;

import java.io.IOException;

/**
 * The controller class for the edit armies page.
 *
 * @author vsbugge
 */
public class EditArmiesTutorialController {

    /**
     * Marks the edit armies tutorial as completed.
     */
    public static void markTutorialComplete() {
        try {
            Settings config = Settings.readConfig();
            config.setEditArmiesTutorial(false);
            config.saveConfig();
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
        }
    }

    @FXML
    private VBox content;

    @FXML
    void onOK(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, event.getEventType()));
    }

    @FXML
    void initialize() {
        Army displayedArmy = new Army("Your army name...");
        displayedArmy.add(new CavalryUnit("", 100), 100);
        content.getChildren().add(1, new EditableArmyInfoElement(displayedArmy));

        Unit displayedUnit = new CavalryUnit("Your unit name...", 100);
        content.getChildren().add(3, new EditableUnitInfoElement(displayedUnit, 100));

        content.getChildren().forEach(PaddingDecorator::padMedium);
    }
}
