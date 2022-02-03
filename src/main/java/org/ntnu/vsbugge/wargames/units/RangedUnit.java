package org.ntnu.vsbugge.wargames.units;

public class RangedUnit extends Unit{
    private final int attackBonus = 3;

    private final int initialResistBonus = 6; // Initial resist bonus
    private final int resistBonusPenalty = 2; // Penalty in resist bonus per time this unit has taken damage
    private final int minimumResistBonus = 2; // Minimum resist bonus

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

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        timesTakenDamage += 1;
    }

    @Override
    public int getResistBonus() {
        int calculatedResistBonus = initialResistBonus - timesTakenDamage * resistBonusPenalty;
        return Integer.max(calculatedResistBonus, minimumResistBonus);
    }


    @Override
    public int getAttackBonus() {
        return attackBonus;
    }
}
