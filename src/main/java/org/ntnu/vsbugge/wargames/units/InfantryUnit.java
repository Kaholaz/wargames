package org.ntnu.vsbugge.wargames.units;

import org.ntnu.vsbugge.wargames.enums.TerrainEnum;

/**
 * A class that represent a single infantry unit
 */
public class InfantryUnit extends Unit {
    protected static final int RESIST_BONUS = 1;
    protected static final int ATTACK_BONUS = 2;

    protected static final int DEFAULT_ATTACK = 15, DEFAULT_ARMOR = 10;

    /**
     * Shorthand constructor that sets attack and armor to default values (15 and 10)
     *
     * @param name
     *            The name of the unit
     * @param health
     *            Total health of the unit
     *
     * @throws IllegalArgumentException
     *             Throws an exception if either health, attack, or armor is negative.
     */
    public InfantryUnit(String name, int health) {
        this(name, health, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    /**
     * Calls the Unit constructor function
     *
     * @param name
     *            Name of the unit
     * @param health
     *            Total health of the unit
     * @param attack
     *            Total Attack-damage of the unit
     * @param armor
     *            Total armor of the unit
     *
     * @throws IllegalArgumentException
     *             Throws an exception if either health, attack, or armor is negative.
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Takes an existing InfantryUnit instance and creates a new instance with the same attributes.
     *
     * @param infantryUnit
     *            An instance of InfantryUnit
     */
    public InfantryUnit(InfantryUnit infantryUnit) {
        this(infantryUnit.getName(), infantryUnit.getHealth(), infantryUnit.getAttack(), infantryUnit.getArmor());
    }

    @Override
    public InfantryUnit copy() {
        return new InfantryUnit(this);
    }

    @Override
    public void resetStats() {
    }

    /**
     * The base resist bonus of the unit without taking the terrain into account.
     * @return The resist bonus of the unit.
     */
    @Override
    public int getResistBonus() {
        return RESIST_BONUS;
    }

    /**
     * The resist bonus with terrain considerations. If the unit is fighting in a forrest, the unit is given a bonus of 2.
     * @param terrain The terrain.
     * @return The resist bonus.
     */
    @Override
    public int getResistBonus(TerrainEnum terrain) {
        if (terrain == TerrainEnum.FORREST) {
            return getResistBonus() + 2;
        }
        return getResistBonus();
    }

    /**
     * The base attack bonus of the unit without taking the terrain into account.
     * @return The attack bonus of the unit.
     */
    @Override
    public int getAttackBonus() {
        return ATTACK_BONUS;

    }

    /**
     * The attack bonus of the unit with terrain considerations. If the unit is fighting in a forrest, the unit is given a bonus of 2.
     * @param terrain The terrain.
     * @return The attack bonus.
     */
    @Override
    public int getAttackBonus(TerrainEnum terrain) {
        return switch (terrain) {
            case FORREST -> getAttackBonus() + 2;
            default -> getAttackBonus();
        };
    }
}
