package org.ntnu.vsbugge.wargames.gui.guielements.infoelements;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.ntnu.vsbugge.wargames.gui.factories.GUIElementFactory;
import org.ntnu.vsbugge.wargames.models.army.Army;
import org.ntnu.vsbugge.wargames.models.units.Unit;

/**
 * An ArmyInfoElement object displays aggregate stats of an army.
 *
 * @author vsbugge
 */
public class ArmyInfoElement extends AbstractInfoElement {
    protected Label topLabel;
    protected Army army;

    /**
     * Constructor for ArmyInfoElement.
     *
     * @param army
     *            The army to display in the ArmyInfoElement instance.
     */
    public ArmyInfoElement(Army army) {
        super();
        this.army = army;
        updateTotalStats();

        this.getStyleClass().add("army-info-element");
    }

    /**
     * {@inheritDoc}
     *
     * The tow row consists of the text "Total army stats:")
     */
    @Override
    protected void createTopRow() {
        topLabel = new Label("Total army stats:");
        HBox hBox = GUIElementFactory.createHBoxWithCenteredElements(true, topLabel);
        this.getChildren().add(0, hBox);
    }

    /**
     * Updates the info fields based on the state of the army by summing the different attributes of each unit in the
     * army.
     */
    public void updateTotalStats() {
        int health = 0;
        int attack = 0;
        int armor = 0;
        int count = 0;

        if (army != null) {
            // Sum the properties of the whole army.
            for (Unit unit : army.getAllUnits()) {
                health += unit.getHealth();
                attack += unit.getAttack();
                armor += unit.getArmor();
                ++count;
            }
        }

        setHealth(health);
        setAttack(attack);
        setArmor(armor);
        setCount(count);
    }

    /**
     * Sets the army to display in this ArmyInfoElement object.
     *
     * @param army
     *            The army to display.
     */
    public void setArmy(Army army) {
        this.army = army;
    }

    /**
     * Getter for the top label.
     *
     * @return The top label.
     */
    public Label getTopLabel() {
        return topLabel;
    }
}
