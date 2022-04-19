package org.ntnu.vsbugge.wargames.units;

/**
 * A class that represent a single infantry unit
 */
public class InfantryUnit extends Unit{
    protected static final int RESIST_BONUS = 1;
    protected static final int ATTACK_BONUS = 2;

    protected static final int DEFAULT_ATTACK = 15, DEFAULT_ARMOR = 10;

    /**
     * Shorthand constructor that sets attack and armor to default values (15 and 10)
     * @param name The name of the unit
     * @param health Total health of the unit
     *
     * @throws IllegalArgumentException Throws an exception if either health, attack, or armor is negative.
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
     *
     * @throws IllegalArgumentException Throws an exception if either health, attack, or armor is negative.
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Takes an existing InfantryUnit instance and creates a new instance with the same attributes.
     *
     * @param infantryUnit An instance of InfantryUnit
     */
    public InfantryUnit(InfantryUnit infantryUnit) {
        this(
                infantryUnit.getName(),
                infantryUnit.getHealth(),
                infantryUnit.getAttack(),
                infantryUnit.getArmor()
        );
    }

    @Override
    public InfantryUnit copy() {
        return new InfantryUnit(this);
    }

    @Override
    public void resetStats() {}

    /**
     * @return The resist bonus of the unit. If the unit is fighting in a forrest, the unit is given a bonus of 2.
     */
    @Override
    public int getResistBonus() {
        return switch (getTerrain()) {
            case FORREST -> RESIST_BONUS + 2;
            default -> RESIST_BONUS;
        };
    }

    /**
     * @return The attack bonus of the unit. If the unit is fighting in a forrest, the unit is given a bonus of 2.
     */
    @Override
    public int getAttackBonus() {
        return switch (getTerrain()) {
            case FORREST -> ATTACK_BONUS + 2;
            default -> ATTACK_BONUS;
        };
    }
}
