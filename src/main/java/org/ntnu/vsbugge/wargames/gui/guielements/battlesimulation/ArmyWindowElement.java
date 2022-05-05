package org.ntnu.vsbugge.wargames.gui.guielements.battlesimulation;

import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.units.Unit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * ArmyWindowElement is a class that inherits from VBox. Instances of this class are used to
 */
public class ArmyWindowElement extends VBox {
    Map<Unit, UnitInfoElement> unitElements = new HashMap<>();
    Army army = null;
    ArmyInfoElement armyInfoElement = new ArmyInfoElement(null);

    /**
     * Creates a new ArmyWindowElement using the VBox constructor.
     */
    public ArmyWindowElement() {
        super();
    }

    /**
     * Clears any unit elements and removes the army info if army is set to null
     */
    public void clear() {
        this.getChildren().clear();
        unitElements.clear();

        if (army != null) {
            this.getChildren().add(armyInfoElement);
        }
    }

    /**
     * Updates the contents of the army window element to reflect the set army.
     */
    public void update() {
        armyInfoElement.updateTotalStats();
        updateCounts();
    }

    /**
     * Updates the counts of all UnitInfoElements in the army window to reflect the state of the set army. If a unit is
     * not represented in the army window, a UnitInfoElement is added to reflect that unit.
     */
    private void updateCounts() {
        // To avoid NullPointerException, the window is just cleared when the army is null.
        if (army == null) {
            clear();
            return;
        }

        Map<Unit, Integer> nonCombatArmyTemplate = army.getCondensedNonCombatUnitArmyTemplate();

        // All nonCombatUnits either in the unitWindow or in the army.
        HashSet<Unit> allNonCombatUnits = new HashSet<>(unitElements.keySet());
        allNonCombatUnits.addAll(nonCombatArmyTemplate.keySet());

        List<Unit> units = allNonCombatUnits.stream().sorted(Unit::compareTo).toList();
        for (Unit unit : units) {
            // Unit in both unitElements and army
            if (unitElements.containsKey(unit) && nonCombatArmyTemplate.containsKey(unit)) {
                updateElementCount(unit, nonCombatArmyTemplate.get(unit));
                continue;
            }
            // Unit in unitElements not in army
            if (!nonCombatArmyTemplate.containsKey(unit)) {
                updateElementCount(unit, 0);
                continue;
            }
            // Unit in army not in unitElements
            else if (!unitElements.containsKey(unit)) {
                // Find the similar unit with the greatest HP and add it to unitElements
                Map<Unit, Integer> condensedArmyTemplate = army.getArmyTemplate();
                for (Unit fullUnit : condensedArmyTemplate.keySet()) {
                    if (unit.differsOnlyInCombatState(fullUnit)) {
                        addNewUnitInfoElement(fullUnit, condensedArmyTemplate.get(fullUnit));
                        break; // The unit has been found, and the inner for loop is exited.
                    }
                }
            }
        }
    }

    /**
     * Updates the count of one unit element. If the element's count is set to 0, the element is deleted.
     *
     * @param unit
     *            The unit whose element to change the count of.
     * @param count
     *            The new count of the UnitInfoElement
     */
    private void updateElementCount(Unit unit, int count) {
        Unit nonCombatUnit = unit.getNonCombatUnit();
        if (!unitElements.containsKey(nonCombatUnit)) {
            return;
        }

        if (count == 0) {
            removeUnitInfoElement(unit);
            return;
        }

        unitElements.get(nonCombatUnit).setCount(count);
    }

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
    private void addNewUnitInfoElement(Unit unit, int count) {
        // Gets the nonCombatUnit and checks for presence
        Unit nonCombatUnit = unit.getNonCombatUnit();
        if (unitElements.containsKey(nonCombatUnit)) {
            // Removes existing UnitInfoElement if there is a conflict
            removeUnitInfoElement(unit);
        }

        // Creates the info element and adds it to the window.
        UnitInfoElement unitInfoElement = new UnitInfoElement(unit, count);
        this.getChildren().add(unitInfoElement);
        unitElements.put(nonCombatUnit, unitInfoElement);
    }

    /**
     * Removes a unit info element from the army window.
     *
     * @param unit
     *            THe unit whose UnitInfoElement to remove
     */
    private void removeUnitInfoElement(Unit unit) {
        // Gets values
        Unit nonCombatUnit = unit.getNonCombatUnit();
        UnitInfoElement infoElement = unitElements.get(nonCombatUnit);

        if (infoElement == null) {
            // Don't want a NullPointerException!
            // Since there was not found infoElement to be found,
            // there is no infoElement to remove anyway.
            return;
        }

        // Remove from the VBox and element map
        this.getChildren().remove(infoElement);
        unitElements.remove(nonCombatUnit);
    }

    /**
     * Sets the army that should be displayed in this ArmyWindowElement.
     *
     * @param army
     *            The army that should be displayed in this ArmyWindowElements.
     */
    public void setArmy(Army army) {
        this.army = army;
        armyInfoElement.setArmy(army);

        clear();
        update();
    }
}
