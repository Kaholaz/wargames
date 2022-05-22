package org.ntnu.vsbugge.wargames.gui.guielements.infoelements;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.ntnu.vsbugge.wargames.gui.decorators.EditableDecorator;
import org.ntnu.vsbugge.wargames.gui.decorators.IconLabelDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
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

        if (!tryNotifyUpdate()) {
            this.health = rollback;
            return;
        }

        super.setHealth(health);
    }

    /** {@inheritDoc} */
    @Override
    public void setCount(int count) {
        int rollback = this.count;
        this.count = count;

        if (!tryNotifyUpdate()) {
            this.count = rollback;
            return;
        }

        super.setCount(count);
    }

    /** {@inheritDoc} */
    @Override
    public void setUnitName(String unitName) {
        String rollback = this.unitName;
        this.unitName = unitName;

        if (unitName.isBlank() || !tryNotifyUpdate()) {
            this.unitName = rollback;

            // Unit was a new unit before the conflict.
            if (rollback.equals("New unit...")) {
                setCount(0); // Remove unit
            }

            if (unitName.isBlank()) {
                throw new IllegalArgumentException("New name cannot be blank");
            }

            return;
        }

        super.setUnitName(unitName);
    }

    /** {@inheritDoc} */
    @Override
    public void setUnitType(UnitEnum unitType) {
        UnitEnum rollback = this.unitType;
        this.unitType = unitType;

        if (!tryNotifyUpdate()) {
            this.unitType = rollback;
            return;
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
     * Tries to notify the observers and notifies the user of any errors thrown by the operation.
     *
     * @return True if the operation was successful, false if not.
     */
    private boolean tryNotifyUpdate() {
        try {
            notifyEventListeners(EventType.UPDATE);
        } catch (IllegalStateException | IllegalArgumentException e) {
            Platform.runLater(() -> AlertFactory.createExceptionErrorAlert(e).show());
            return false;
        }
        return true;
    }
}
