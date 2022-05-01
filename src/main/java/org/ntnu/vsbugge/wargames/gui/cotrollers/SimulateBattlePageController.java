package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.ntnu.vsbugge.wargames.Army;
import org.ntnu.vsbugge.wargames.Battle;
import org.ntnu.vsbugge.wargames.files.ArmyFileUtil;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.gui.guielements.UnitInfoElement;
import org.ntnu.vsbugge.wargames.units.Unit;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SimulateBattlePageController {
    private Battle battle = new Battle();

    @FXML
    private CheckBox animateCheck;

    @FXML
    private VBox attackerUnitWindow;

    @FXML
    private VBox defenderUnitWindow;

    @FXML
    private Button importAttacker;

    @FXML
    private Button importDefender;

    @FXML
    void onHome(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "launchPage.fxml");
    }

    @FXML
    void onStart(ActionEvent event) {
        Army winner = battle.simulate();
        updateUnits();
        AlertFactory.createInfo(String.format("%s won!", winner.getName()), "Battle results!").show();
    }

    @FXML
    void onImportAttacker(ActionEvent event) {
        Army army = pickArmy();

        if (army == null) {
            return;
        }

        battle.setArmyOne(army);
        importAttacker.setText(army.getName());
        updateUnits();
    }

    @FXML
    void onImportDefender(ActionEvent event) {
        Army army = pickArmy();

        if (army == null) {
            return;
        }

        battle.setArmyTwo(army);
        importDefender.setText(army.getName());
        updateUnits();
    }

    private Army pickArmy() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick an army");

        try {
            ArmyFileUtil files = new ArmyFileUtil();
            fileChooser.setInitialDirectory(files.getDefaultPath());
            File armyFile = fileChooser.showOpenDialog(animateCheck.getScene().getWindow());
            return files.loadFromPath(armyFile);
        } catch (IOException e) {
            AlertFactory.createError("Could not parse army file!\n" + e.getMessage(), e.getClass().getSimpleName())
                    .show();
            return null;
        }
    }

    @FXML
    void initialize() {
        ArmyFileUtil files = new ArmyFileUtil();
        List<Army> armies = files.getArmiesFromDefaultPath();
    }

    void updateUnits() {
        fillUnitWindow(attackerUnitWindow, battle.getArmyOne());
        fillUnitWindow(defenderUnitWindow, battle.getArmyTwo());
    }

    void fillUnitWindow(VBox unitWindow, Army army) {
        unitWindow.getChildren().clear();
        if (army == null) {
            return;
        }

        Map<Unit, Integer> armyTemplate = army.getArmyTemplate();
        List<Unit> sortedUnits = armyTemplate.keySet().stream().sorted(Unit::compareTo).toList();
        for (Unit unit : sortedUnits) {
            UnitInfoElement unitElement = new UnitInfoElement(unit, armyTemplate.get(unit));
            unitWindow.getChildren().add(unitElement);
        }
    }
}
