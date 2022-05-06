package org.ntnu.vsbugge.wargames.gui.guielements.battlesimulation;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;
import org.ntnu.vsbugge.wargames.gui.decorators.IconLabelDecorator;
import org.ntnu.vsbugge.wargames.gui.decorators.PaddingDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.GUIElementFactory;
import org.ntnu.vsbugge.wargames.units.Unit;

public class UnitInfoElement extends AbstractInfoElement {
    protected Label unitTypeLabel;
    protected Label unitNameLabel;

    public UnitInfoElement(Unit unit, int count) {
        super();

        // Set fields
        setUnitType(unit.getClass().getSimpleName());
        setUnitName(unit.getName());
        setHealth(unit.getHealth());
        setArmor(unit.getArmor());
        setAttack(unit.getAttack());
        setCount(count);

        this.getStyleClass().add("unit-info-element");
    }

    @Override
    protected void createTopRow() {
        unitTypeLabel = new Label();
        unitNameLabel = new Label();

        // Add padding
        PaddingDecorator.padLight(unitTypeLabel);
        PaddingDecorator.padLight(unitNameLabel);

        // Add to VBox
        HBox top = GUIElementFactory.createHBoxWithCenteredElements(true, unitTypeLabel, unitNameLabel);
        this.getChildren().add(0, top);
    }

    private void setUnitType(String simpleName) {
        UnitEnum unitEnum = UnitEnum.fromString(simpleName);
        String unitIcon = switch (unitEnum) {
        case CavalryUnit -> "cavalry";
        case CommanderUnit -> "commander";
        case InfantryUnit -> "infantry";
        case RangedUnit -> "ranged";
        };

        unitTypeLabel.setText(unitEnum.getProperName());
        IconLabelDecorator.setIcon(unitTypeLabel, unitIcon);
    }

    private void setUnitName(String unitName) {
        unitNameLabel.setText(unitName);
    }
}
