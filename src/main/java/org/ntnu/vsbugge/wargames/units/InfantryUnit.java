package org.ntnu.vsbugge.wargames.units;

import org.ntnu.vsbugge.wargames.utils.enums.TerrainEnum;

/**
 * A class that represent a single infantry unit
 *
 * @author vsbugge
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
     * @throws java.lang.IllegalArgumentException
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
     * @throws java.lang.IllegalArgumentException
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

    /** {@inheritDoc} */
    @Override
    public InfantryUnit copy() {
        return new InfantryUnit(this);
    }

    /** {@inheritDoc} */
    @Override
    public void resetStats() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getResistBonus() {
        return RESIST_BONUS;
    }

    /**
     * {@inheritDoc}
     *
     * If the unit is fighting in a forest, the unit is given a bonus of 2.
     */
    @Override
    public int getResistBonus(TerrainEnum terrain) {
        if (terrain == TerrainEnum.FOREST) {
            return getResistBonus() + 2;
        }
        return getResistBonus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAttackBonus() {
        return ATTACK_BONUS;

    }

    /**
     * {@inheritDoc}
     *
     * If the unit is fighting in a forest, the unit is given a bonus of 2.
     */
    @Override
    public int getAttackBonus(TerrainEnum terrain) {
        return switch (terrain) {
        case FOREST -> getAttackBonus() + 2;
        default -> getAttackBonus();
        };
    }
}
