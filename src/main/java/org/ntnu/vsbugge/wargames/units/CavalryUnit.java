package org.ntnu.vsbugge.wargames.units;

/**
 * A class that represent a single cavalry unit
 */
public class CavalryUnit extends Unit {
    protected static final int RESIST_BONUS = 1;
    protected static final int ATTACK_BONUS = 2; // The general attack bonus
    protected static final int FIRST_ATTACK_BONUS = 6; // The attack bonus for the first strike
    protected boolean hasAttacked = false;

    protected static final int DEFAULT_ATTACK = 20, DEFAULT_ARMOR = 12;

    /**
     * Shorthand constructor that sets attack and armor to default values (15 and 10)
     * @param name The name of the unit
     * @param health Total health of the unit
     *
     * @throws IllegalArgumentException Throws an exception if either health, attack, or armor is negative.
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
     *
     * @throws IllegalArgumentException Throws an exception if either health, attack, or armor is negative.
     */
    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Takes an existing CavalryUnit instance and creates a new instance with the same attributes.
     *
     * @param cavalryUnit An instance of CavalryUnit
     */
    public CavalryUnit(CavalryUnit cavalryUnit) {
        this(
                cavalryUnit.getName(),
                cavalryUnit.getHealth(),
                cavalryUnit.getAttack(),
                cavalryUnit.getArmor()
        );
        this.hasAttacked = cavalryUnit.hasAttacked;
    }


    @Override
    public void attack(Unit opponent) {
        super.attack(opponent);
        hasAttacked = true;
    }

    @Override
    public CavalryUnit copy() {
        return new CavalryUnit(this);
    }

    @Override
    public void resetStats() {
        hasAttacked = false;
    }

    @Override
    public int getResistBonus() {
        return RESIST_BONUS;
    }

    /**
     * Calculates the attack bonus of the unit. If the unit has not attacked,
     * this returns the value of the private constant FIRST_ATTACK_BONUS. If the unit already has attacked,
     * the value of the private constant ATTACK_BONUS is returned instead.
     * @return The attack bonus of the unit
     */
    @Override
    public int getAttackBonus() {
        if (!hasAttacked) {
            return FIRST_ATTACK_BONUS;
        }
        return ATTACK_BONUS;
    }
}
