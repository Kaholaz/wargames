package org.ntnu.vsbugge.wargames.units;

import org.ntnu.vsbugge.wargames.enums.TerrainEnum;

/**
 * A class that represent a single cavalry unit
 */
public class CavalryUnit extends Unit {
    protected static final int RESIST_BONUS = 1;

    protected static final int INITIAL_ATTACK_BONUS = 6; // The initial attack bonus.
    protected static final int ATTACK_BONUS_PENALTY = 4; // A penalty that is applied each time this unit attacks.
    protected static final int MINIMUM_ATTACK_BONUS = 2; // The minimum attack bonus.
    protected int numberOfTimesAttacked;

    protected static final int DEFAULT_ATTACK = 20, DEFAULT_ARMOR = 12;

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
    public CavalryUnit(String name, int health) {
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
    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Takes an existing CavalryUnit instance and creates a new instance with the same attributes.
     *
     * @param cavalryUnit
     *            An instance of CavalryUnit
     */
    public CavalryUnit(CavalryUnit cavalryUnit) {
        this(cavalryUnit.getName(), cavalryUnit.getHealth(), cavalryUnit.getAttack(), cavalryUnit.getArmor());
        this.numberOfTimesAttacked = cavalryUnit.numberOfTimesAttacked;
    }

    @Override
    public void attack(Unit opponent, TerrainEnum terrain) {
        super.attack(opponent, terrain);
        ++numberOfTimesAttacked;
    }

    @Override
    public CavalryUnit copy() {
        return new CavalryUnit(this);
    }

    @Override
    public void resetStats() {
        numberOfTimesAttacked = 0;
    }

    /**
     * @return The base resist bonus of the unit without terrain considerations.
     */
    @Override
    public int getResistBonus() {
        return RESIST_BONUS;

    }

    /**
     * The resist bonus of the unit after terrain considerations.If the terrain of the unit is TerrainEnum.FORREST, the resist bonus is 0.
     * @param terrain The terrain.
     * @return The resist bonus.
     */
    @Override
    public int getResistBonus(TerrainEnum terrain) {
        if (terrain == TerrainEnum.FORREST) {
            return 0;
        }
        return getResistBonus();
    }

    /**
     * Calculates the base attack bonus of the unit. If the unit has not attacked, this returns the value of the private
     * constant INITIAL_ATTACK_BONUS. Each time this unit attacks, An attack value equal to ATTACK_BONUS_PENALTY is
     * subtracted each time this unit attacks to a minimum of MINIMUM_ATTACK_BONUS.
     *
     * If the terrain of the unit is TerrainEnum.PLAINS, the unit is given an additional attack bonus of 2.
     *
     * @return The attack bonus of the unit.
     */
    @Override
    public int getAttackBonus() {
        return Integer.max(MINIMUM_ATTACK_BONUS,
                INITIAL_ATTACK_BONUS - numberOfTimesAttacked * ATTACK_BONUS_PENALTY);
    }

    /**
     * Calculates the attack bonus of the unit after terrain considerations. This method uses getAttackBonus() as a baseline. If the terrain of the unit is TerrainEnum.PLAINS, the unit is given an additional attack bonus of 2.
     * @param terrain The terrain.
     * @return The attack bonus of the unit.
     */
    @Override
    public int getAttackBonus(TerrainEnum terrain) {
        if (terrain == TerrainEnum.PLAINS) {
            return getAttackBonus() + 2;
        }
        return getAttackBonus();
    }
}
