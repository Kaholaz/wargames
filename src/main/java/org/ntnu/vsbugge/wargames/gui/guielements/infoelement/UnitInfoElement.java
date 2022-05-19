package org.ntnu.vsbugge.wargames.gui.guielements.infoelement;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.ntnu.vsbugge.wargames.gui.decorators.IconLabelDecorator;
import org.ntnu.vsbugge.wargames.gui.decorators.PaddingDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.GUIElementFactory;
import org.ntnu.vsbugge.wargames.units.Unit;
import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;

/**
 * A unit element displays stats about one or more similar units.
 *
 * @author vsbugge
 */
public class UnitInfoElement extends AbstractInfoElement {
    protected Label unitTypeLabel;
    protected Label unitNameLabel;

    /**
     * Constructor for UnitInfoElement.
     *
     * @param unit
     *            The unit to display in this UnitInfoElement
     * @param count
     *            The amount units this UnitInfoElement represents.
     */
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

    /** {@inheritDoc} */
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
        setUnitType(unitEnum);
    }

    protected void setUnitType(UnitEnum unitEnum) {
        String unitIcon = switch (unitEnum) {
        case CavalryUnit -> "cavalry";
        case CommanderUnit -> "commander";
        case InfantryUnit -> "infantry";
        case RangedUnit -> "ranged";
        };

        unitTypeLabel.setText(unitEnum.getProperName());
        IconLabelDecorator.setIcon(unitTypeLabel, unitIcon);
    }

    protected void setUnitName(String unitName) {
        unitNameLabel.setText(unitName);
    }
}