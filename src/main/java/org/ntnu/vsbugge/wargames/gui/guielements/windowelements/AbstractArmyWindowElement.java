package org.ntnu.vsbugge.wargames.gui.guielements.windowelements;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.models.army.Army;
import org.ntnu.vsbugge.wargames.models.units.Unit;

/**
 * The super class for all windows that displays an army. This class extends the VBox class.
 *
 * @author vsbugge
 */
public abstract class AbstractArmyWindowElement extends VBox {
    protected Army army = null;

    /**
     * Creates a new ArmyWindowElement using the VBox constructor.
     */
    public AbstractArmyWindowElement() {
        super();
        this.setAlignment(Pos.CENTER);
    }

    /**
     * Clears any unit elements and removes the army info if army is set to null
     */
    public abstract void clear();

    /**
     * Updates the state of the army window to reflect changes to the set army.
     */
    protected abstract void update();

    /**
     * Resets the window to its original state solely using the set army.
     */
    public abstract void reset();

    /**
     * Adds a unit element to the army window.
     *
     * This method is used by updateCounts to add units not represented in the army window.
     *
     * @param unit
     *            The unit to add.
     * @param count
     *            The count of this unit.
     */
    protected abstract void addNewUnitInfoElement(Unit unit, int count);

    /**
     * Removes a unit info element from the army window.
     *
     * @param unit
     *            THe unit whose UnitInfoElement to remove
     */
    protected abstract void removeUnitInfoElement(Unit unit);

    /**
     * Sets the army that should be displayed in this ArmyWindowElement.
     *
     * @param army
     *            The army that should be displayed in this ArmyWindowElements.
     */
    public void setArmy(Army army) {
        this.army = army;
    }
}
