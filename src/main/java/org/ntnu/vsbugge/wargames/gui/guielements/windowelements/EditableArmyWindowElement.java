package org.ntnu.vsbugge.wargames.gui.guielements.windowelements;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.AbstractDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.StringInputDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.ArmyInfoElement;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.EditableArmyInfoElement;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.EditableUnitInfoElement;
import org.ntnu.vsbugge.wargames.models.army.Army;
import org.ntnu.vsbugge.wargames.models.units.InfantryUnit;
import org.ntnu.vsbugge.wargames.models.units.Unit;
import org.ntnu.vsbugge.wargames.utils.eventlisteners.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An editable ArmyWindowElement.
 *
 * @author vsbugge
 */
public class EditableArmyWindowElement extends AbstractArmyWindowElement {
    private final List<EditableUnitInfoElement> unitElements = new ArrayList<>();

    protected final ArmyInfoElement armyInfoElement = new EditableArmyInfoElement(null);

    /**
     * Creates a button to add units to the army.
     *
     * @return The button used to add units to the army.
     */
    private Button createAddButton() {
        Button button = new Button(" + ");
        button.getStyleClass().addAll("round-button", "padded-medium");

        button.setOnAction((event -> {
            addNewUnitInfoElement(new InfantryUnit("New unit...", 100), 1);
            EditableUnitInfoElement unitElement = unitElements.get(unitElements.size() - 1);
            Pane topRow = (Pane) unitElement.getChildren().get(0);
            Label nameLabel = (Label) topRow.getChildren().get(3);

            AbstractDoubleClickSwapper onDoubleClick = new StringInputDoubleClickSwapper(nameLabel,
                    unitElement::setUnitName);
            onDoubleClick.runsIfDoubleClick(); // Selects the name field of the new unit.
        }));

        return button;
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        this.getChildren().clear();
        unitElements.clear();

        if (army != null) {
            this.getChildren().add(armyInfoElement);
            this.getChildren().add(createAddButton());
        }
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public void reset() {
        clear();

        if (army != null) {
            // Units are sorted to make order consistent between loads
            Map<Unit, Integer> template = army.getCondensedArmyTemplate();
            army.getCondensedArmyTemplate().keySet().stream().sorted().forEach(unit -> {
                addNewUnitInfoElement(unit, template.get(unit));
            });
        }

        update();
    }

    /** {@inheritDoc} */
    @Override
    protected void addNewUnitInfoElement(Unit unit, int count) {
        Unit nonCombatUnit = unit.getNonCombatUnit();

        for (EditableUnitInfoElement unitElement : unitElements) {
            if (unitElement.getUnit().differsOnlyInCombatState(nonCombatUnit)) {
                AlertFactory
                        .createExceptionErrorAlert(
                                new IllegalArgumentException("Could not add unit as similar unit already exists."))
                        .show();
                return;
            }
        }

        // Creates the info element and adds it to the window.
        EditableUnitInfoElement unitInfoElement = new EditableUnitInfoElement(unit, count);
        unitInfoElement.attach(eventType -> {
            if (eventType == EventType.UPDATE) {
                update();
            }
        });
        this.getChildren().add(unitElements.size() + 1, unitInfoElement);
        unitElements.add(unitInfoElement);
    }

    /** {@inheritDoc} */
    @Override
    protected void removeUnitInfoElement(Unit unit) {
        // Reversed search to remove the latest added element.
        for (int i = unitElements.size() - 1; i >= 0; i--) {
            EditableUnitInfoElement unitElement = unitElements.get(i);
            if (unitElement.getUnit().differsOnlyInCombatState(unit)) {
                unitElements.remove(i);
                this.getChildren().remove(i + 1);
                return;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setArmy(Army army) {
        super.setArmy(army);
        armyInfoElement.setArmy(army);
    }

    /**
     * Gets the Army represented by this ArmyWindowElement.
     *
     * @return The Army.
     */
    public Army getArmy() {
        return army;
    }
}
