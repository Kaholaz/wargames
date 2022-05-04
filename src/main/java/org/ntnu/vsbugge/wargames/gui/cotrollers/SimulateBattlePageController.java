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
import org.ntnu.vsbugge.wargames.Army;
import org.ntnu.vsbugge.wargames.Battle;
import org.ntnu.vsbugge.wargames.enums.TerrainEnum;
import org.ntnu.vsbugge.wargames.files.ArmyFileUtil;
import org.ntnu.vsbugge.wargames.gui.GUI;
import org.ntnu.vsbugge.wargames.gui.decorators.StatusDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.gui.guielements.battlesimulation.ArmyWindowElement;

import java.io.File;
import java.io.IOException;

/**
 * The controller for to simulate battle page.
 */
public class SimulateBattlePageController {
    private Battle battle = new Battle();
    private Battle originalBattle = new Battle();
    private static boolean pauseAnimation = false;
    private static int animationRenderFrequency = 10;

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
        pauseAnimation = false;

        // Hide animate check box
        animateCheck.setDisable(true);
        animateCheck.setVisible(false);

        startButton.setText("Pause");
        startButton.setOnAction(this::onPause);

        StatusDecorator.makeDisabled(homeButton);

        // Animate the battle.
        if (animateCheck.isSelected()) {
            animateBattle(1, 0);
            return;
        }

        // Simulate without animation.
        try {
            announceWinner(battle.simulate());
        } catch (RuntimeException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
            resetButtons();
        }

    }

    /**
     * The event listener for when the 'reset' button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    void onReset(ActionEvent event) {
        battle = originalBattle;
        attackerUnitWindow.setArmy(battle.getArmyOne());
        defenderUnitWindow.setArmy(battle.getArmyTwo());

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
        pauseAnimation = true;

        startButton.setText("Resume");
        startButton.setOnAction(this::onResume);

        StatusDecorator.makeEnabled(homeButton);
    }

    @FXML
    void onTerrainChange(ActionEvent event) {
        battle.setTerrain(terrainDropDown.getSelectionModel().getSelectedItem());
    }

    /**
     * Starts a thread that simulates an army battle, step by step. The simulation is stopped when the
     * {@code pauseAnimation} boolean is set to true, or if the simulation concludes.
     *
     * @param ms
     *            The millisecond delay between each simulation step.
     * @param ns
     *            The nanosecond delay between each simulation step.
     */
    void animateBattle(int ms, int ns) {
        new Thread(() -> {
            Army winner = null;
            for (int i = 0; winner == null && !pauseAnimation; i = (i + 1) % animationRenderFrequency) {
                try {
                    winner = battle.simulateStep();
                } catch (RuntimeException e) {
                    Platform.runLater(() -> {
                        AlertFactory.createExceptionErrorAlert(e).show();
                        resetButtons();
                    });
                    pauseAnimation = true;
                    break; // Exit loop if there is something wrong with the battle being simulated.
                }

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
                Platform.runLater(() -> announceWinner(finalWinner));
            }
        }).start();
    }

    /**
     * Creates and shows an information alert announcing the winner of a battle.
     *
     * @param winner
     *            The winner of the battle to announce.
     */
    void announceWinner(Army winner) {
        updateUnits();

        StatusDecorator.makeDisabled(startButton);
        StatusDecorator.makeEnabled(homeButton);

        AlertFactory
                .createAlert(Alert.AlertType.INFORMATION, String.format("%s won!", winner.getName()), "Battle results")
                .show();
    }

    /**
     * The event listener for when the importAttacker button is pressed.
     *
     * @param event
     *            The action event from the button press.
     */
    @FXML
    void onImportAttacker(ActionEvent event) {
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
     * @param event
     *            The action event from the button press.
     */
    @FXML
    void onImportDefender(ActionEvent event) {
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
    }

    /**
     * Updates the UnitInfoElements and ArmyInfoElements of both armies' ArmyWindowElement.
     */
    void updateUnits() {
        attackerUnitWindow.update();
        defenderUnitWindow.update();
    }
}
