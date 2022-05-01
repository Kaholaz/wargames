package org.ntnu.vsbugge.wargames.gui.guielements;

import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.Army;
import org.ntnu.vsbugge.wargames.units.Unit;

import java.util.HashMap;
import java.util.Map;

public class ArmyElement extends VBox {
    Map<Unit, UnitInfoElement> unitElements = new HashMap<>();
    Army army = null;

    public ArmyElement() {
        super();
    }
    
    public ArmyElement(Army army) {
        this();
        this.army = army;
    }

    public void clear() {
        this.getChildren().clear();
        unitElements.clear();
    }

    public void updateElementCount(Unit unit, int count) {
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

    public void add(Unit unit, int count) {
        // Creates the info element
        UnitInfoElement unitInfoElement = new UnitInfoElement(unit, count);
        this.getChildren().add(unitInfoElement);

        // Fills the map with the info element
        Unit nonCombatUnit = unit.getNonCombatUnit();
        unitElements.put(nonCombatUnit, unitInfoElement);
    }

    public void remove(Unit unit) {
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
}
