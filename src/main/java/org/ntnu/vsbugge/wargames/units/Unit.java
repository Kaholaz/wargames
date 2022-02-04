package org.ntnu.vsbugge.wargames.units;

/**
 * An abstract class for all units
 */
public abstract class Unit {
    private final String name;
    private final int attack, armor;
    private int health;

    /**
     * Constructor for the abstract class
     * @param name Name of the unit
     * @param health Total health of the unit
     * @param attack Total Attack-damage of the unit
     * @param armor Total armor of the unit
     */
    public Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * Simulates an engagement and deals damage to the opponent according to the stats of the unit and the opponent
     *
     * Total damage is calculated by this formula: <br>
     * {@code ((this.getAttack() + this.getAttackBonus()) - (opponent.getArmor() + opponent.getResistBonus())}
     * @param opponent The opposing unit in the engagement
     */
    public void attack(Unit opponent) {
        int damage = (this.getAttack() + this.getAttackBonus()) - (opponent.getArmor() + opponent.getResistBonus());
        opponent.takeDamage(Integer.max(damage, 0)); // no negative damage
    }

    /**
     * @return The name of the unit
     */
    public String getName() {
        return name;
    }

    /**
     * @return The remaining health of the unit
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return The attack of teh unit
     */
    public int getAttack() {
        return attack;
    }

    /**
     * @return The armor of the unit
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Makes the unit take damage to health-points
     * @param damage The damage inflicted on the unit
     */
    public void takeDamage(int damage) {
        this.health = health - damage;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", attack=" + attack +
                ", armor=" + armor +
                '}';
    }

    /**
     * Calculates and returns the attack bonus of the unit
     */
    public abstract int getAttackBonus();

    /**
     * Calculates and returns the resist bonus of the unit
     */
    public abstract int getResistBonus();
}
