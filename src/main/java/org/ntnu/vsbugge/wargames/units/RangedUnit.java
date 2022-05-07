package org.ntnu.vsbugge.wargames.units;

import org.ntnu.vsbugge.wargames.utils.enums.TerrainEnum;

/**
 * A class that represent a single ranged unit
 *
 * @author vsbugge
 */
public class RangedUnit extends Unit {
    /** Constant <code>ATTACK_BONUS=3</code> */
    protected static final int ATTACK_BONUS = 3;

    /** Constant <code>INITIAL_RESIST_BONUS=6</code> */
    protected static final int INITIAL_RESIST_BONUS = 6; // Initial resist bonus
    /** Constant <code>RESIST_BONUS_PENALTY=2</code> */
    protected static final int RESIST_BONUS_PENALTY = 2; // Penalty in resist bonus per time this unit has taken damage
    /** Constant <code>MINIMUM_RESIST_BONUS=2</code> */
    protected static final int MINIMUM_RESIST_BONUS = 2; // Minimum resist bonus

    private int numberOfTimesTakenDamage = 0;

    /** Constant <code>DEFAULT_ATTACK=15</code> */
    /** Constant <code>DEFAULT_ARMOR=8</code> */
    protected static final int DEFAULT_ATTACK = 15, DEFAULT_ARMOR = 8;

    /**
     * Shorthand constructor that sets attack and armor to default values (15 and 10)
     *
     * @param name
     *            The name of the unit
     * @param health
     *            Total health of the unit
     *
     * @throws java.lang.IllegalArgumentException
     *             Throws an exception if either health is negative.
     */
    public RangedUnit(String name, int health) {
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
    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Takes an existing RangedUnit instance and creates a new instance with the same attributes.
     *
     * @param rangedUnit
     *            An instance of RangedUnit
     */
    public RangedUnit(RangedUnit rangedUnit) {
        this(rangedUnit.getName(), rangedUnit.getHealth(), rangedUnit.getAttack(), rangedUnit.getArmor());
        this.numberOfTimesTakenDamage = rangedUnit.numberOfTimesTakenDamage;
    }

    /**
     * {@inheritDoc}
     *
     * When this method is called, {@code super.takeDamage} is first called, then the private field
     * {@code timesTakenDamage} is incremented by one. This is important for the calculation of resist bonus, which is
     * variable depending on the number of times the unit has taken damage.
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        numberOfTimesTakenDamage += 1;
    }

    /** {@inheritDoc} */
    @Override
    public RangedUnit copy() {
        return new RangedUnit(this);
    }

    /** {@inheritDoc} */
    @Override
    public void resetStats() {
        numberOfTimesTakenDamage = 0;
    }

    /**
     * {@inheritDoc}
     *
     * <br>
     * <br>
     * This is the formula used: {@code INITIAL_RESIST_BONUS - timesTakenDamage * RESIST_BONUS_PENALTY}<br>
     * The resist bonus is first set to an initial value. After each time this unit takes damage, the resist bonus is
     * lowered by a set amount until the minimum resist bonus is reached.
     */
    @Override
    public int getResistBonus() {
        int calculatedResistBonus = INITIAL_RESIST_BONUS - numberOfTimesTakenDamage * RESIST_BONUS_PENALTY;
        return Integer.max(calculatedResistBonus, MINIMUM_RESIST_BONUS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAttackBonus() {
        return ATTACK_BONUS;
    }

    /**
     * {@inheritDoc} If the terrain of the unit is TerrainEnum.HILL, the unit is given a bonus of 3. If the terrain of
     * the unit is TerrainEnum.FOREST, the unit is given a penalty of 2.
     */
    @Override
    public int getAttackBonus(TerrainEnum terrain) {
        return switch (terrain) {
        case HILL -> getAttackBonus() + 3;
        case FOREST -> getAttackBonus() - 2;
        default -> getAttackBonus();
        };
    }
}
