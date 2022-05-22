package org.ntnu.vsbugge.wargames.gui.guielements.infoelements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.ntnu.vsbugge.wargames.gui.decorators.IconLabelDecorator;
import org.ntnu.vsbugge.wargames.gui.decorators.PaddingDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.GUIElementFactory;
import org.ntnu.vsbugge.wargames.models.units.Unit;
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
        HBox unitName = new HBox(new Label("Unit name: "), unitNameLabel);
        unitName.setAlignment(Pos.CENTER);

        // Add padding
        PaddingDecorator.padLight(unitTypeLabel);
        PaddingDecorator.padLight(unitName);

        // Add to VBox
        HBox top = GUIElementFactory.createHBoxWithCenteredElements(true, unitTypeLabel, unitName);
        this.getChildren().add(0, top);
    }

    /**
     * Sets the type of the unit in the UnitInfoElement.
     *
     * @param simpleName
     *            The name of the unit type (eg. CavalryUnit, ect.)
     */
    private void setUnitType(String simpleName) {
        UnitEnum unitEnum = UnitEnum.fromString(simpleName);
        setUnitType(unitEnum);
    }

    /**
     * Sets the type of the unit in the UnitInfoElement using the unit enum.
     *
     * @param unitEnum
     *            The unit enum constant that corresponds to the desired Unit subclass.
     */
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

    /**
     * Sets the name of the unit represented by this UnitInfoElement.
     *
     * @param unitName
     *            The name of the Unit.
     */
    protected void setUnitName(String unitName) {
        unitNameLabel.setText(unitName);
    }

    /**
     * Getter for the unit name label.
     *
     * @return The unit name label.
     */
    public Label getUnitNameLabel() {
        return unitNameLabel;
    }

    /**
     * Getter for the unit type label.
     *
     * @return The unit type label.
     */
    public Label getUnitTypeLabel() {
        return unitTypeLabel;
    }
}
