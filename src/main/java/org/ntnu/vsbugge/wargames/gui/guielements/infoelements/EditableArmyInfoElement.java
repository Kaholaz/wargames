package org.ntnu.vsbugge.wargames.gui.guielements.infoelements;

import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.gui.decorators.EditableDecorator;

public class EditableArmyInfoElement extends ArmyInfoElement {
    public EditableArmyInfoElement(Army army) {
        super(army);
        EditableDecorator.makeEditable(topLabel, this::setArmyName);
    }

    public void setArmyName(String name) {
        army.setName(name);
        topLabel.setText(name);
    }

    @Override
    public void setArmy(Army army) {
        super.setArmy(army);
        topLabel.setText(army.getName());
    }
}
