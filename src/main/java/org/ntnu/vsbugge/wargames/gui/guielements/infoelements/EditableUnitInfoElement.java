package org.ntnu.vsbugge.wargames.gui.guielements.infoelements;

import javafx.application.Platform;
import org.ntnu.vsbugge.wargames.gui.decorators.EditableDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.units.Unit;
import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;
import org.ntnu.vsbugge.wargames.utils.eventlisteners.EventType;
import org.ntnu.vsbugge.wargames.utils.eventlisteners.Subject;
import org.ntnu.vsbugge.wargames.utils.factories.UnitFactory;

import java.util.List;

public class EditableUnitInfoElement extends UnitInfoElement implements Subject {
    private int health;
    private int count;
    private UnitEnum unitType;
    private String unitName;

    public EditableUnitInfoElement(Unit unit, int count) {
        super(unit, count);

        EditableDecorator.makeEditable(healthLabel, this::setHealth);
        EditableDecorator.makeEditable(countLabel, this::setCount);

        EditableDecorator.makeEditable(unitTypeLabel, this::setUnitType);
        EditableDecorator.makeEditable(unitNameLabel, this::setUnitName);
    }

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

    @Override
    public void setUnitName(String unitName) {
        String rollback = this.unitName;
        this.unitName = unitName;

        if (!tryNotifyUpdate()) {
            this.unitName = rollback;
            return;
        }

        super.setUnitName(unitName);
    }

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

    public List<Unit> getUnits() {
        return UnitFactory.getUnits(unitType.toString(), unitName, health, count);
    }

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
