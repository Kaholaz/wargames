package org.ntnu.vsbugge.wargames.gui.cotrollers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.battle.Battle;
import org.ntnu.vsbugge.wargames.utils.enums.TerrainEnum;
import org.ntnu.vsbugge.wargames.utils.files.ArmyFileUtil;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.decorators.StatusDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.gui.guielements.battlesimulation.ArmyWindowElement;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * The controller for to simulate battle page.
 *
 * @author vsbugge
 */
public class SimulateBattlePageController {
    private final Battle battle = new Battle();
    private Battle originalBattle = new Battle();
    private long lastUpdate = new Date().getTime();
    /**
     * The time in milliseconds between GUI updates during a battle simulation.
     */
    private final int updateDelta = 33;

    @FXML
    private CheckBox animateCheck;

    @FXML
    private VBox attackerUnitWindowParent;

    private ArmyWindowElement attackerUnitWindow;

    @FXML
    private VBox defenderUnitWindowParent;

    private ArmyWindowElement defenderUnitWindow;

    @FXML
    private Button importAttacker;

    @FXML
    private Button importDefender;

    @FXML
    private Button startButton;

    @FXML
    private Button homeButton;

    @FXML
    private ComboBox<TerrainEnum> terrainDropDown;

    /**
     * The event listener for when the 'home' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    @FXML
    void onHome(ActionEvent event) {
        GUI.setSceneFromActionEvent(event, "launchPage.fxml");
    }

    /**
     * The event listener for when the 'start' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    @FXML
    void onStart(ActionEvent event) {
        StatusDecorator.makeDisabled(importAttacker);
        StatusDecorator.makeDisabled(importDefender);
        StatusDecorator.makeDisabled(terrainDropDown);

        homeButton.setText("Reset");
        homeButton.setOnAction(this::onReset);

        originalBattle = new Battle(battle);
        onResume(event);
    }

    /**
     * The event listener for when the 'resume' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    void onResume(ActionEvent event) {
        // Hide animate check box
        animateCheck.setDisable(true);
        animateCheck.setVisible(false);

        startButton.setText("Pause");
        startButton.setOnAction(this::onPause);

        StatusDecorator.makeDisabled(homeButton);

        Thread thread = new Thread(() -> {
            if (animateCheck.isSelected()) {
                battle.simulate(1);
            } else {
                battle.simulate();
            }
        });
        thread.setDaemon(true); // Makes the simulation thread close whenever the application exits.
        thread.start();
    }

    /**
     * The event listener for when the 'reset' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    void onReset(ActionEvent event) {
        battle.setArmyOne(originalBattle.getArmyOne());
        battle.setArmyTwo(originalBattle.getArmyTwo());
        battle.setAttackTurn(originalBattle.getAttackTurn());

        defenderUnitWindow.setArmy(battle.getArmyTwo());
        attackerUnitWindow.setArmy(battle.getArmyOne());

        resetButtons();
    }

    /**
     * Reset the startButton, homeButton, animationCheck and the import buttons to their original values.
     */
    void resetButtons() {
        // Show animate check box
        animateCheck.setDisable(false);
        animateCheck.setVisible(true);

        // Change buttons to default
        startButton.setText("Start");
        startButton.setOnAction(this::onStart);

        homeButton.setText("Home");
        homeButton.setOnAction(this::onHome);

        // Enable buttons.
        StatusDecorator.makeEnabled(startButton);
        StatusDecorator.makeEnabled(homeButton);
        StatusDecorator.makeEnabled(importAttacker);
        StatusDecorator.makeEnabled(importDefender);
        StatusDecorator.makeEnabled(terrainDropDown);
    }

    /**
     * The event listener for when the 'pause' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    void onPause(ActionEvent event) {
        battle.pauseSimulation();

        startButton.setText("Resume");
        startButton.setOnAction(this::onResume);

        StatusDecorator.makeEnabled(homeButton);
    }

    @FXML
    void onTerrainChange(ActionEvent ignoredEvent) {
        battle.setTerrain(terrainDropDown.getSelectionModel().getSelectedItem());
    }

    /**
     * Creates and shows an information alert announcing the winner of a battle.
     */
    void announceWinner() {
        updateUnits();

        Army winner = battle.getWinner();
        if (battle.getWinner() == null) {
            Exception e = new RuntimeException("Could not announce winner as there is no winner.");
            AlertFactory.createExceptionErrorAlert(e).show();
        }

        StatusDecorator.makeDisabled(startButton);
        StatusDecorator.makeEnabled(homeButton);

        AlertFactory
                .createAlert(Alert.AlertType.INFORMATION, String.format("%s won!", winner.getName()), "Battle results")
                .show();
    }

    /**
     * The event listener for when the importAttacker button is pressed.
     *
     * @param ignoredEvent
     *            The action event from the button press.
     */
    @FXML
    void onImportAttacker(ActionEvent ignoredEvent) {
        Army army = pickArmy();
        if (army == null) {
            return;
        }

        importAttacker.setText(army.getName());

        battle.setArmyOne(army);
        attackerUnitWindow.setArmy(battle.getArmyOne());
    }

    /**
     * The event listener for when the importDefender button is pressed.
     *
     * @param ignoredEvent
     *            The action event from the button press.
     */
    @FXML
    void onImportDefender(ActionEvent ignoredEvent) {
        Army army = pickArmy();
        if (army == null) {
            return;
        }

        importDefender.setText(army.getName());

        battle.setArmyTwo(army);
        defenderUnitWindow.setArmy(battle.getArmyTwo());
    }

    /**
     * Opens a dialog for picking an army file, and shows any file parsing errors to the user.
     *
     * @return Returns the picked army if it was successfully parsed. If the file-picker was closed, or the file could
     *         not be parsed, {@code null} is returned.
     */
    private Army pickArmy() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick an army");

        try {
            ArmyFileUtil files = new ArmyFileUtil();
            fileChooser.setInitialDirectory(files.getDefaultPath());
            File armyFile = fileChooser.showOpenDialog(animateCheck.getScene().getWindow());
            return files.loadFromPath(armyFile);
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
            return null;
        } catch (NullPointerException ignore) {
            return null;
        }
    }

    /**
     * Updates the UnitInfoElements and ArmyInfoElements of both armies' ArmyWindowElement.
     */
    void updateUnits() {
        attackerUnitWindow.update();
        defenderUnitWindow.update();
    }

    /**
     * Sets the initial state of the 'simulate battle' page.
     */
    @FXML
    void initialize() {
        // Set attacker unit window
        attackerUnitWindowParent.getChildren().clear();
        attackerUnitWindow = new ArmyWindowElement();
        attackerUnitWindowParent.getChildren().add(attackerUnitWindow);

        // Set defender unit window
        defenderUnitWindowParent.getChildren().clear();
        defenderUnitWindow = new ArmyWindowElement();
        defenderUnitWindowParent.getChildren().add(defenderUnitWindow);

        terrainDropDown.getItems().addAll(TerrainEnum.values());
        terrainDropDown.getSelectionModel().select(TerrainEnum.DEFAULT);

        battle.attach(eventType -> {
            switch (eventType) {
            case UPDATE -> {
                long now = new Date().getTime();
                if (now - updateDelta >= lastUpdate) {
                    Platform.runLater(this::updateUnits);
                    lastUpdate = now;
                }
            }
            case FINISH -> Platform.runLater(this::announceWinner);
            }
        });
    }
}
