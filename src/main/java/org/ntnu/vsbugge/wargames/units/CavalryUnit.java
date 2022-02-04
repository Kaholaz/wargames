package org.ntnu.vsbugge.wargames.units;

public class CavalryUnit extends Unit {
    private final int resistBonus = 1;
    private final int attackBonus = 2; // The general attack bonus
    private final int firstAttackBonus = 6; // The attack bonus for the first strike
    private boolean hasAttacked = false;

    private static final int DEFAULT_ATTACK = 20, DEFAULT_ARMOR = 12;

    /**
     * Shorthand constructor that sets attack and armor to default values (15 and 10)
     * @param name The name of the unit
     * @param health Total health of the unit
     */
    public CavalryUnit(String name, int health) {
        this(name, health, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    /**
     * Calls the Unit constructor function
     * @param name Name of the unit
     * @param health Total health of the unit
     * @param attack Total Attack-damage of the unit
     * @param armor Total armor of the unit
     */
    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    @Override
    public void attack(Unit opponent) {
        super.attack(opponent);
        hasAttacked = true;
    }

    @Override
    public int getResistBonus() {
        return resistBonus;
    }

    @Override
    public int getAttackBonus() {
        if (!hasAttacked) {
            return firstAttackBonus;
        }

        return attackBonus;
    }
}
