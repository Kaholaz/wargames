package org.ntnu.vsbugge.wargames.units;

/**
 * A class that represent a single ranged unit
 */
public class RangedUnit extends Unit{
    protected static final int ATTACK_BONUS = 3;

    protected static final int INITIAL_RESIST_BONUS = 6; // Initial resist bonus
    protected static final int RESIST_BONUS_PENALTY = 2; // Penalty in resist bonus per time this unit has taken damage
    protected static final int MINIMUM_RESIST_BONUS = 2; // Minimum resist bonus

    private int timesTakenDamage = 0;

    protected static final int DEFAULT_ATTACK = 15, DEFAULT_ARMOR = 8;

    /**
     * Shorthand constructor that sets attack and armor to default values (15 and 10)
     * @param name The name of the unit
     * @param health Total health of the unit
     *
     * @throws IllegalArgumentException Throws an exception if either health is negative.
     */
    public RangedUnit(String name, int health) {
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
    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Takes an existing RangedUnit instance and creates a new instance with the same attributes.
     *
     * @param rangedUnit An instance of RangedUnit
     */
    public RangedUnit(RangedUnit rangedUnit) {
        this(
                rangedUnit.getName(),
                rangedUnit.getHealth(),
                rangedUnit.getAttack(),
                rangedUnit.getArmor()
        );
        this.timesTakenDamage = rangedUnit.timesTakenDamage;
    }

    /**
     * When this method is called, {@code super.takeDamage} is first called, then the private field
     * {@code timesTakenDamage} is incremented by one. This is important for the calculation of resist bonus,
     * which is variable depending on the number of times the unit has taken damage.
     * @param damage The damage inflicted on the unit
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        timesTakenDamage += 1;
    }

    @Override
    public RangedUnit copy() {
        return new RangedUnit(this);
    }

    @Override
    public void resetStats() {
        timesTakenDamage = 0;
    }

    /**
     * Calculates the resist bonus of this unit.
     *
     * <br><br>
     * This is the formula used: {@code INITIAL_RESIST_BONUS - timesTakenDamage * RESIST_BONUS_PENALTY}<br>
     * The resist bonus is first set to an initial value. After each time this unit takes damage, the resist bonus
     * is lowered by a set amount until the minimum resist bonus is reached.
     * @return The resist bonus of the unit
     */
    @Override
    public int getResistBonus() {
        int calculatedResistBonus = INITIAL_RESIST_BONUS - timesTakenDamage * RESIST_BONUS_PENALTY;
        return Integer.max(calculatedResistBonus, MINIMUM_RESIST_BONUS);
    }


    @Override
    public int getAttackBonus() {
        return ATTACK_BONUS;
    }
}
