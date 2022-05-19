package org.ntnu.vsbugge.wargames.gui.guielements.windowelements;

import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.ArmyInfoElement;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.EditableArmyInfoElement;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.EditableUnitInfoElement;
import org.ntnu.vsbugge.wargames.units.Unit;
import org.ntnu.vsbugge.wargames.utils.eventlisteners.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditableArmyWindowElement extends AbstractArmyWindowElement {
    private final List<EditableUnitInfoElement> unitElements = new ArrayList<>();

    protected ArmyInfoElement armyInfoElement = new EditableArmyInfoElement(null);

    @Override
    void clear() {
        this.getChildren().clear();
        unitElements.clear();

        if (army != null) {
            this.getChildren().add(armyInfoElement);
        }
    }

    @Override
    protected void update() {
        Army army = new Army(this.army.getName());
        for (EditableUnitInfoElement unitElement : new ArrayList<>(unitElements)) {
            List<Unit> units = unitElement.getUnits();
            if (units.size() == 0) {
                removeUnitInfoElement(unitElement.getUnit());
            } else {
                army.addAll(units);
            }
        }

        if (army.getCondensedArmyTemplate().size() != unitElements.size()) {
            throw new IllegalStateException("Two or more units are too similar!");
        }

        setArmy(army);
        armyInfoElement.updateTotalStats();
    }

    @Override
    public void reset() {
        clear();

        // Units are sorted to make order consistent between loads
        Map<Unit, Integer> template = army.getCondensedArmyTemplate();
        army.getCondensedArmyTemplate().keySet().stream().sorted().forEach(unit -> {
            addNewUnitInfoElement(unit, template.get(unit));
        });
        
        update();
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
    @Override
    protected void addNewUnitInfoElement(Unit unit, int count) {
        Unit nonCombatUnit = unit.getNonCombatUnit();

        for (EditableUnitInfoElement unitElement : unitElements) {
            if (unitElement.getUnit().differsOnlyInCombatState(nonCombatUnit)) {
                throw new IllegalArgumentException("Could not add unit as similar unit already exists.");
            }
        }

        // Creates the info element and adds it to the window.
        EditableUnitInfoElement unitInfoElement = new EditableUnitInfoElement(unit, count);
        unitInfoElement.attach(eventType -> {
            if (eventType == EventType.UPDATE) {
                update();
            }
        });
        this.getChildren().add(unitInfoElement);
        unitElements.add(unitInfoElement);
    }

    @Override
    protected void removeUnitInfoElement(Unit unit) {
        for (EditableUnitInfoElement unitElement : unitElements) {
            if (unitElement.getUnit().differsOnlyInCombatState(unit)) {
                unitElements.remove(unitElement);
                this.getChildren().remove(unitElement);
                return;
            }
        }
    }

    @Override
    public void setArmy(Army army) {
        super.setArmy(army);
        armyInfoElement.setArmy(army);
    }
}
