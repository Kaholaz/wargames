package org.ntnu.vsbugge.wargames.units;

/**
 * A class that represent a single ranged unit
 */
public class RangedUnit extends Unit{
    private final int ATTACK_BONUS = 3;

    private final int INITIAL_RESIST_BONUS = 6; // Initial resist bonus
    private final int RESIST_BONUS_PENALTY = 2; // Penalty in resist bonus per time this unit has taken damage
    private final int MINIMUM_RESIST_BONUS = 2; // Minimum resist bonus

    private int timesTakenDamage = 0;

    private static final int DEFAULT_ATTACK = 15, DEFAULT_ARMOR = 8;

    /**
     * Shorthand constructor that sets attack and armor to default values (15 and 10)
     * @param name The name of the unit
     * @param health Total health of the unit
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
     */
    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Takes an existing RangedUnit instance and creates a new instance with the same attributes.
     *
     * Please note that stats regarding number of times taken damage and number of times attacked are reset,
     * so the new instance will not necessarily be exactly the same as the original.
     * @param rangedUnit An instance of RangedUnit
     */
    public RangedUnit(RangedUnit rangedUnit) {
        this(
                rangedUnit.getName(),
                rangedUnit.getHealth(),
                rangedUnit.getAttack(),
                rangedUnit.getArmor()
        );
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
