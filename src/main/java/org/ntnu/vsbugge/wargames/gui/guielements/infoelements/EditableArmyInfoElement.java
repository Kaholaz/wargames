package org.ntnu.vsbugge.wargames.gui.guielements.infoelements;

import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.gui.decorators.EditableDecorator;

/**
 * An editable ArmyInfoElement where the user can set the name of the army.
 */
public class EditableArmyInfoElement extends ArmyInfoElement {
    /**
     * Constructor for the EditableArmyInfoElement.
     *
     * @param army
     *            The army to initialize the EditableArmyInfoElement with.
     */
    public EditableArmyInfoElement(Army army) {
        super(army);
        EditableDecorator.makeEditable(topLabel, this::setArmyName);
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

    /**
     * Set the army of the ArmyInfoElement.
     *
     * @param army
     *            The army to display.
     */
    @Override
    public void setArmy(Army army) {
        super.setArmy(army);
        if (army != null) {
            topLabel.setText(army.getName());
        }
    }
}
