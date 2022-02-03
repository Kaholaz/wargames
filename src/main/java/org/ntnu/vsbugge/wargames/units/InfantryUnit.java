package org.ntnu.vsbugge.wargames.units;

/**
 * A class that represent a single infantry unit
 */
public class InfantryUnit extends Unit{
    private final int resistBonus = 1;
    private final int attackBonus = 2;

    private static final int DEFAULT_ATTACK = 15, DEFAULT_ARMOR = 10;

    /**
     * Shorthand constructor that sets attack and armor to default values (15 and 10)
     * @param name The name of the unit
     * @param health Total health of the unit
     */
    public InfantryUnit(String name, int health) {
        this(name, health, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    /**
     * Calls the Unit constructor function
     * @param name Name of the unit
     * @param health Total health of the unit
     * @param attack Total Attack-damage of the unit
     * @param armor Total armor of the unit
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    @Override
    public int getResistBonus() {
        return resistBonus;
    }

    @Override
    public int getAttackBonus() {
        return attackBonus;
    }
}
