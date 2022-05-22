package org.ntnu.vsbugge.wargames.gui.guielements.infoelements;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.ntnu.vsbugge.wargames.gui.decorators.EditableDecorator;
import org.ntnu.vsbugge.wargames.gui.decorators.IconLabelDecorator;
import org.ntnu.vsbugge.wargames.models.units.Unit;
import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;
import org.ntnu.vsbugge.wargames.utils.eventlisteners.EventType;
import org.ntnu.vsbugge.wargames.utils.eventlisteners.Subject;
import org.ntnu.vsbugge.wargames.utils.factories.UnitFactory;

import java.util.List;

/**
 * An editable version of the UnitInfoElement. Notifies any observers if the state of the unit changes.
 *
 * @author vsbugge
 */
public class EditableUnitInfoElement extends UnitInfoElement implements Subject {
    private int health;
    private int count;
    private UnitEnum unitType;
    private String unitName;

    /**
     * The constructor for the EditableUnitInfoElement.
     *
     * @param unit
     *            The unit to display in the UnitInfoElement.
     * @param count
     *            The initial count of this unit.
     */
    public EditableUnitInfoElement(Unit unit, int count) {
        super(unit, count);

        EditableDecorator.makeEditable(healthLabel, this::setHealth);
        EditableDecorator.makeEditable(countLabel, this::setCount);

        EditableDecorator.makeEditable(unitTypeLabel, this::setUnitType);
        EditableDecorator.makeEditable(unitNameLabel, this::setUnitName);
    }

    /** {@inheritDoc} */
    @Override
    protected void createTopRow() {
        super.createTopRow();

        // Add a red X to remove the unit.
        Label xLabel = new Label("");
        xLabel.setOnMouseClicked(observable -> this.setCount(0));
        IconLabelDecorator.setIcon(xLabel, "delete");
        xLabel.getStyleClass().add("clickable");

        ((Pane) this.getChildren().get(0)).getChildren().add(xLabel);
    }

    /** {@inheritDoc} */
    @Override
    public void setHealth(int health) {
        int rollback = this.health;
        this.health = health;

        RuntimeException exception = tryNotifyUpdateAndReturnException();
        if (exception != null) {
            this.health = rollback;
            throw exception;
        }

        super.setHealth(health);
    }

    /** {@inheritDoc} */
    @Override
    public void setCount(int count) {
        int rollback = this.count;
        this.count = count;

        RuntimeException exception = tryNotifyUpdateAndReturnException();
        if (exception != null) {
            this.count = rollback;
            throw exception;
        }

        super.setCount(count);
    }

    /** {@inheritDoc} */
    @Override
    public void setUnitName(String unitName) {
        String rollback = this.unitName;
        this.unitName = unitName;

        RuntimeException exception;
        if (unitName.isBlank()) {
            exception = new IllegalArgumentException("New name cannot be blank");
        } else {
            exception = tryNotifyUpdateAndReturnException();
        }

        if (exception != null) {
            this.unitName = rollback;
            // Unit was a new unit before the conflict.
            if (rollback.equals("New unit...")) {
                setCount(0); // Remove unit
            }
            throw exception;
        }

        super.setUnitName(unitName);
    }

    /** {@inheritDoc} */
    @Override
    public void setUnitType(UnitEnum unitType) {
        UnitEnum rollback = this.unitType;
        this.unitType = unitType;

        RuntimeException exception = tryNotifyUpdateAndReturnException();
        if (exception != null) {
            this.unitType = rollback;
            throw exception;
        }

        super.setUnitType(unitType);

        Unit displayedUnit = UnitFactory.getUnit(unitType.toString(), unitName, health);
        setArmor(displayedUnit.getArmor());
        setAttack(displayedUnit.getAttack());
    }

    /**
     * Gets the unit displayed in the UnitInfoElement in its current state.
     *
     * @return The unit displayed in the UnitInfoElement.
     */
    public Unit getUnit() {
        return UnitFactory.getUnit(unitType.toString(), unitName, health);
    }

    /**
     * Gets a list all units represented by this UnitInfoElement. This is a list of size equal to the count in the
     * UnitInfoElement where every unit is the unit returned from the getUnit method.
     *
     * @return A list of all units represented by this UnitInfoElement.
     */
    public List<Unit> getUnits() {
        return UnitFactory.getUnits(unitType.toString(), unitName, health, count);
    }

    /**
     * Tries to notify the observers and returns any exceptions encountered.
     *
     * @return The exception encountered. If no exception was encountered, null is returned.
     */
    private RuntimeException tryNotifyUpdateAndReturnException() {
        try {
            notifyEventListeners(EventType.UPDATE);
        } catch (RuntimeException e) {
            return e;
        }
        return null;
    }
}
