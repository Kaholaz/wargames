package org.ntnu.vsbugge.wargames.gui.guielements.infoelements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.ntnu.vsbugge.wargames.gui.decorators.EditableDecorator;
import org.ntnu.vsbugge.wargames.models.army.Army;

/**
 * An editable ArmyInfoElement where the user can set the name of the army.
 *
 * @author vsbugge
 */
public class EditableArmyInfoElement extends ArmyInfoElement {

    /** {@inheritDoc} */
    @Override
    protected void createTopRow() {
        super.createTopRow();

        HBox topRow = (HBox) this.getChildren().get(0);
        HBox armyName = new HBox(new Label("Army name: "), topLabel);
        armyName.setAlignment(Pos.CENTER);
        topRow.getChildren().add(1, armyName);
    }

    /**
     * Constructor for the EditableArmyInfoElement.
     *
     * @param army
     *            The army to initialize the EditableArmyInfoElement with.
     */
    public EditableArmyInfoElement(Army army) {
        super(army);
        EditableDecorator.makeEditable(topLabel, this::setArmyName);
        if (army != null) {
            setArmyName(army.getName());
        }
    }

    /**
     * Sets the name of the army in the ArmyInfoElement.
     *
     * @param name
     *            The new name of the army.
     */
    public void setArmyName(String name) {
        army.setName(name);
        topLabel.setText(name);
    }

    /** {@inheritDoc} */
    @Override
    public void setArmy(Army army) {
        super.setArmy(army);
        if (army != null) {
            topLabel.setText(army.getName());
        }
    }
}
