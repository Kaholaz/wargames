package org.ntnu.vsbugge.wargames.units;

public abstract class Unit {
    private String name;
    private int health, attack, armor;

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
        if (damage > 0) {
            opponent.setHealth(opponent.getHealth() - damage);
        }
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
     * Sets the current health of the unit
     * @param health The new health of the unit
     */
    public void setHealth(int health) {
        this.health = health;
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

    public abstract int getAttackBonus();
    public abstract int getResistBonus();
}
