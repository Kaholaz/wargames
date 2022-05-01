package org.ntnu.vsbugge.wargames.gui.guielements;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.enums.UnitEnum;
import org.ntnu.vsbugge.wargames.factories.UnitFactory;
import org.ntnu.vsbugge.wargames.gui.decorators.PaddingDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.GUIElementFactory;
import org.ntnu.vsbugge.wargames.gui.factories.IconLabelFactory;
import org.ntnu.vsbugge.wargames.units.Unit;

public class UnitInfoElement extends VBox {
    private int health;
    private int attack;
    private int armor;
    private int count;

    private Label healthLabel;
    private Label attackLabel;
    private Label armorLabel;
    private Label countLabel;

    /**
     * A unit where health is set to 0 and attack and resist bonus is default;
     */
    private final Unit noCombatUnit;

    public UnitInfoElement(Unit unit, int count) {
        super();
        noCombatUnit = UnitFactory.getUnit(unit.getClass().getSimpleName(), unit.getName(), 0);

        // Populate element
        createTopRow(unit);
        createBottomRow(unit, count);

        // Add style
        this.getStyleClass().add("bordered-box");
        this.getStyleClass().add("unit-info-element");
        PaddingDecorator.padMedium(this);
    }

    private void createTopRow(Unit unit) {
        // Find unit type and set correct icon
        UnitEnum unitEnum = UnitEnum.fromString(unit.getClass().getSimpleName());
        String unitIcon = switch (unitEnum) {
        case CavalryUnit -> "cavalry";
        case CommanderUnit -> "commander";
        case InfantryUnit -> "infantry";
        case RangedUnit -> "ranged";
        };

        // Create labels
        Label unitTypeLabel = IconLabelFactory.createIconLabel(unitEnum.getProperName(), unitIcon);
        Label unitNameLabel = new Label(unit.getName());

        // Add padding
        PaddingDecorator.padLight(unitTypeLabel);
        PaddingDecorator.padLight(unitNameLabel);

        // Add to VBox
        HBox top = GUIElementFactory.createHBoxWithCenteredElements(true, unitTypeLabel, unitNameLabel);
        this.getChildren().add(0, top);
    }

    private void createBottomRow(Unit unit, int count) {
        // Create labels
        healthLabel = IconLabelFactory.createIconLabel("", "health");
        PaddingDecorator.padLight(healthLabel);
        setHealth(unit.getHealth());

        attackLabel = IconLabelFactory.createIconLabel("", "attack");
        PaddingDecorator.padLight(attackLabel);
        setAttack(unit.getAttack());

        armorLabel = IconLabelFactory.createIconLabel("", "armor");
        PaddingDecorator.padLight(armorLabel);
        setArmor(unit.getArmor());

        countLabel = new Label();
        PaddingDecorator.padLight(countLabel);
        setCount(count);

        // Add to VBox
        HBox bottom = GUIElementFactory.createHBoxWithCenteredElements(true, healthLabel, attackLabel, armorLabel,
                countLabel);
        this.getChildren().add(bottom);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        healthLabel.setText(String.format("%d hp", health));
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
        attackLabel.setText(String.format("%d atk", attack));
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
        armorLabel.setText(String.format("%d arm", armor));
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        countLabel.setText(String.format("x%d", count));
    }

    /**
     * Gets the unit represented by this element with health set to 0 and combat altered stats rest (resist and attack bonus)
     * @return The unit represented by this element.
     */
    public Unit getNoCombatUnit() {
        return noCombatUnit;
    }
}
