package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.application.Platform;
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
import org.ntnu.vsbugge.wargames.gui.guielements.ArmyElement;
import org.ntnu.vsbugge.wargames.units.Unit;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SimulateBattlePageController {
    private Battle battle = new Battle();
    private Battle originalBattle = new Battle();
    private static boolean pauseAnimation;
    private static int animationRenderFrequency = 10;

    @FXML
    private CheckBox animateCheck;

    @FXML
    private VBox attackerUnitWindowParent;

    private ArmyElement attackerUnitWindow;

    @FXML
    private VBox defenderUnitWindowParent;

    private ArmyElement defenderUnitWindow;

    @FXML
    private Button importAttacker;

    @FXML
    private Button importDefender;

    @FXML
    private Button startButton;

    @FXML
    void onHome(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "launchPage.fxml");
    }

    @FXML
    void onStart(ActionEvent event) {
        originalBattle = new Battle(battle);
        onResume(event);
    }

    void onResume(ActionEvent event) {
        pauseAnimation = false;

        // Hide animate check box
        animateCheck.setDisable(true);
        animateCheck.setVisible(false);


        if (animateCheck.isSelected()) {
            animateBattle(1, 0);
            return;
        }

        announceWinner(battle.simulate());
    }

    void onReset(ActionEvent event) {
        battle = originalBattle;
        resetUnits();

        // Hide animate check box
        animateCheck.setDisable(false);
        animateCheck.setVisible(true);

        // Change button
        startButton.setText("Start");
        startButton.setOnAction(this::onStart);
    }

    void onPause(ActionEvent event) {
        pauseAnimation = true;
        startButton.setText("Resume");
        startButton.setOnAction(this::onResume);
    }

    void animateBattle(int ms, int ns) {
        startButton.setText("Pause");
        startButton.setOnAction(this::onPause);
        new Thread(() -> {
            Army winner = null;
            for (int i = 0; winner == null && !pauseAnimation; i = (i + 1) % animationRenderFrequency) {
                winner = battle.simulateStep();
                try {
                    Thread.sleep(ms, ns);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (i == 0) {
                    Platform.runLater(this::updateUnits);
                }
            }

            if (!pauseAnimation) {
                Army finalWinner = winner;
                Platform.runLater(() -> {
                    announceWinner(finalWinner);
                });
            }
        }).start();
    }

    void announceWinner(Army winner) {
        updateUnits();

        // Change button
        startButton.setText("Reset");
        startButton.setOnAction(this::onReset);

        AlertFactory.createInfo(String.format("%s won!", winner.getName()), "Battle results").show();
    }

    @FXML
    void onImportAttacker(ActionEvent event) {
        Army army = pickArmy();

        if (army == null) {
            return;
        }

        battle.setArmyOne(army);
        importAttacker.setText(army.getName());
        resetUnits();
    }

    @FXML
    void onImportDefender(ActionEvent event) {
        Army army = pickArmy();

        if (army == null) {
            return;
        }

        battle.setArmyTwo(army);
        importDefender.setText(army.getName());
        resetUnits();
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

        attackerUnitWindowParent.getChildren().clear();
        attackerUnitWindow = new ArmyElement();
        attackerUnitWindowParent.getChildren().add(attackerUnitWindow);

        defenderUnitWindowParent.getChildren().clear();
        defenderUnitWindow = new ArmyElement();
        defenderUnitWindowParent.getChildren().add(defenderUnitWindow);
    }

    void updateUnits() {
        updateUnitWindow(attackerUnitWindow, battle.getArmyOne());
        updateUnitWindow(defenderUnitWindow, battle.getArmyTwo());
    }

    void clearUnits() {
        attackerUnitWindow.clear();
        defenderUnitWindow.clear();
    }

    void resetUnits() {
        clearUnits();

        fillUnitWindow(attackerUnitWindow, battle.getArmyOne());
        fillUnitWindow(defenderUnitWindow, battle.getArmyTwo());
    }

    void updateUnitWindow(ArmyElement unitWindow, Army army) {
        Map<Unit, Integer> nonCombatArmyTemplate = army.getCondensedNonCombatUnitArmyTemplate();

        // All nonCombatUnits either in the unitWindow or in the army.
        HashSet<Unit> allNonCombatUnits = new HashSet<>(unitWindow.getUnitElements().keySet());
        allNonCombatUnits.addAll(nonCombatArmyTemplate.keySet());
        for (Unit unit : allNonCombatUnits) {
            if (unitWindow.getUnitElements().containsKey(unit) && nonCombatArmyTemplate.containsKey(unit)) {
                unitWindow.updateElementCount(unit, nonCombatArmyTemplate.get(unit));
            }
            else if (!nonCombatArmyTemplate.containsKey(unit)) {
                unitWindow.updateElementCount(unit, 0);
            }
        }
    }

    void fillUnitWindow(ArmyElement unitWindow, Army army) {
        unitWindow.clear();
        if (army == null) {
            return;
        }

        Map<Unit, Integer> armyTemplate = army.getCondensedArmyTemplate();
        List<Unit> sorted = armyTemplate.keySet().stream().sorted(Unit::compareTo).toList();
        for (Unit unit :sorted) {
            unitWindow.add(unit, armyTemplate.get(unit));
        }
    }
}
