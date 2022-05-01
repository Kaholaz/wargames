package org.ntnu.vsbugge.wargames.gui.guielements;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.ntnu.vsbugge.wargames.Army;
import org.ntnu.vsbugge.wargames.gui.factories.GUIElementFactory;
import org.ntnu.vsbugge.wargames.units.Unit;

public class ArmyInfoElement extends AbstractInfoElement {
    private  Army army;
    public ArmyInfoElement(Army army) {
        super();
        this.army = army;
        updateTotalStats();

        this.getStyleClass().add("army-info-element");
    }

    @Override
    protected void createTopRow() {
        HBox hBox = GUIElementFactory.createHBoxWithCenteredElements(true, new Label("Total army stats:"));
        this.getChildren().add(0, hBox);
    }

    protected void updateTotalStats() {
        int health = 0;
        int attack = 0;
        int armor = 0;
        int count = 0;

        if (army != null) {
            for (Unit unit : army.getAllUnits()) {
                health += unit.getHealth();
                attack += unit.getAttack();
                armor += unit.getArmor();
                count++;
            }
        }

        setHealth(health);
        setAttack(attack);
        setArmor(armor);
        setCount(count);
    }

    public void setArmy(Army army) {
        this.army = army;
    }
}
