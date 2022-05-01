package org.ntnu.vsbugge.wargames.gui.guielements;

import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.Army;
import org.ntnu.vsbugge.wargames.units.Unit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ArmyWindowElement extends VBox {
    Map<Unit, UnitInfoElement> unitElements = new HashMap<>();
    Army army = null;
    ArmyInfoElement armyInfoElement = new ArmyInfoElement(null);

    public ArmyWindowElement() {
        super();
    }

    public void clear() {
        this.getChildren().clear();
        unitElements.clear();

        this.getChildren().add(armyInfoElement);
    }

    public void update() {
        armyInfoElement.updateTotalStats();
        updateCounts();
    }

    private void updateCounts() {
        Map<Unit, Integer> nonCombatArmyTemplate = army.getCondensedNonCombatUnitArmyTemplate();

        // All nonCombatUnits either in the unitWindow or in the army.
        HashSet<Unit> allNonCombatUnits = new HashSet<>(getUnitElements().keySet());
        allNonCombatUnits.addAll(nonCombatArmyTemplate.keySet());

        for (Unit unit : allNonCombatUnits) {
            // Unit in both unitElements and army
            if (getUnitElements().containsKey(unit) && nonCombatArmyTemplate.containsKey(unit)) {
                updateElementCount(unit, nonCombatArmyTemplate.get(unit));
            }
            // Unit in unitElements not in army
            else if (!nonCombatArmyTemplate.containsKey(unit)) {
                updateElementCount(unit, 0);
            }
            // Unit in army not in unitElements
            else if (!getUnitElements().containsKey(unit)) {
                // Find the similar unit with the greatest HP and add it to unitElements
                Map<Unit, Integer> condensedArmyTemplate = army.getArmyTemplate();
                for (Unit fullUnit : condensedArmyTemplate.keySet()) {
                    if (unit.differsOnlyInCombatState(fullUnit)) {
                        add(fullUnit, condensedArmyTemplate.get(fullUnit));
                        break;
                    }
                }
            }
        }
    }

    private void updateElementCount(Unit unit, int count) {
        Unit nonCombatUnit = unit.getNonCombatUnit();
        if (!unitElements.containsKey(nonCombatUnit)) {
            return;
        }

        if (count == 0) {
            remove(unit);
            return;
        }

        unitElements.get(nonCombatUnit).setCount(count);
    }

    private void add(Unit unit, int count) {
        // Creates the info element
        UnitInfoElement unitInfoElement = new UnitInfoElement(unit, count);
        this.getChildren().add(unitInfoElement);

        // Fills the map with the info element
        Unit nonCombatUnit = unit.getNonCombatUnit();
        unitElements.put(nonCombatUnit, unitInfoElement);
    }

    private void remove(Unit unit) {
        // Gets values
        Unit nonCombatUnit = unit.getNonCombatUnit();
        UnitInfoElement infoElement = unitElements.get(nonCombatUnit);

        // Remove from the VBox and element map
        this.getChildren().remove(infoElement);
        unitElements.remove(nonCombatUnit);
    }

    public Map<Unit, UnitInfoElement> getUnitElements() {
        return unitElements;
    }

    public void setArmy(Army army) {
        this.army = army;
        armyInfoElement.setArmy(army);

        if (army == null) {
            this.getChildren().clear();
            return;
        }

        clear();
        update();
    }
}
