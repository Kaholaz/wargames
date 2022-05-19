package org.ntnu.vsbugge.wargames.gui.guielements.windowelement;

import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.units.Unit;

public abstract class AbstractArmyWindowElement extends VBox {
    protected Army army = null;

    /**
     * Creates a new ArmyWindowElement using the VBox constructor.
     */
    public AbstractArmyWindowElement() {
        super();
    }

    /**
     * Clears any unit elements and removes the army info if army is set to null
     */
    abstract void clear();

    protected abstract void update();

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
