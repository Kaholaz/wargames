package org.ntnu.vsbugge.wargames.models.units;

import org.ntnu.vsbugge.wargames.utils.enums.TerrainEnum;
import org.ntnu.vsbugge.wargames.utils.factories.UnitFactory;

import java.util.Objects;

/**
 * An abstract class for all units
 *
 * @author vsbugge
 */
public abstract class Unit implements Comparable<Unit> {
    private final String name;
    private final int attack, armor;
    private int health;

    /**
     * Constructor for the abstract class.
     *
     * @param name
     *            Name of the unit
     * @param health
     *            Total health of the unit. 0 health encodes a dead unit.
     * @param attack
     *            Total Attack-damage of the unit
     * @param armor
     *            Total armor of the unit
     *
     * @throws java.lang.IllegalArgumentException
     *             Throws an exception if either health, attack, or armor is negative.
     */
    public Unit(String name, int health, int attack, int armor) throws IllegalArgumentException {
        this.name = name;

        if (health < 0)
            throw new IllegalArgumentException("Health cannot be negative!");
        this.health = health;

        if (attack < 0)
            throw new IllegalArgumentException("Attack cannot be negative!");
        this.attack = attack;

        if (armor < 0)
            throw new IllegalArgumentException("Armor cannot be negative!");
        this.armor = armor;
    }

    /**
     * Simulates an engagement and deals damage to the opponent according to the stats of the unit and the opponent.
     * This method uses the attack(Unit, TerrainEnum) with the terrain enum set to DEFAULT. This method is kept for
     * backwards compatibility.
     *
     * @param opponent
     *            The opposing unit in the engagement.
     */
    public void attack(Unit opponent) {
        this.attack(opponent, TerrainEnum.DEFAULT);
    }

    /**
     * Simulates an engagement and deals damage to the opponent according to the stats of the unit and the opponent.
     *
     * <br>
     * <br>
     * Total damage is calculated by this formula: <br>
     * {@code ((this.getAttack() + this.getAttackBonus()) - (opponent.getArmor() + opponent.getResistBonus())}
     *
     * @param opponent
     *            The opposing unit in the engagement
     * @param terrain
     *            a {@link org.ntnu.vsbugge.wargames.utils.enums.TerrainEnum} object
     */
    public void attack(Unit opponent, TerrainEnum terrain) {
        int damage = (this.getAttack() + this.getAttackBonus(terrain))
                - (opponent.getArmor() + opponent.getResistBonus(terrain));
        opponent.takeDamage(Integer.max(damage, 0)); // no negative damage
    }

    /**
     * Getter for the field 'name'.
     *
     * @return The name of the unit
     */
    public final String getName() {
        return name;
    }

    /**
     * Getter for the field 'health'.
     *
     * @return The remaining health of the unit. Zero health signifies that the unit is dead.
     */
    public final int getHealth() {
        return health;
    }

    /**
     * Getter for the field 'attack'.
     *
     * @return The attack of the unit
     */
    public final int getAttack() {
        return attack;
    }

    /**
     * Getter for the field 'armor'.
     *
     * @return The armor of the unit
     */
    public final int getArmor() {
        return armor;
    }

    /**
     * Makes the unit take damage to health-points. A negative or zero health-value signifies that the unit is dead.
     *
     * <br>
     * <br>
     * This method is included in favor of a setHealth method; both to protect it from being changed in an unforeseen
     * way, and to create an interface to make it possible to track the number of times a unit has been attacked without
     * introducing unexpected behaviour (ie incrementing a variable tracking the number of times a unit has taken damage
     * in a setHealth method, or in the getResistBonus method)
     *
     * <br>
     * <br>
     * If there exists a need to change the health of a unit in other ways (ie healing or resetting health), other
     * methods should be added to deal with these actions instead of implementing a setHealth method. This is to ensure
     * that setHealth (which arguably has a name that is more likely to get used if the user is not familiar with the
     * source code) is not used instead of takeDamage when dealing damage to a unit.
     *
     * <br>
     * <br>
     * If the amount of damage dealt to this unit is greater than the remaining health, the unit's health wil be set to
     * 0 instead of a negative number. This indicates that the unit is dead.
     *
     * @param damage
     *            The damage inflicted on the unit.
     */
    public void takeDamage(int damage) {
        // No negative health
        this.health = Integer.max(health - damage, 0);
    }

    /**
     * Returns a similar unit to the unit instance just with zero health and reset resist and attack bonus.
     *
     * @return A similar unit to the unit instance just with zero health and reset resist and attack bonus.
     */
    public Unit getNonCombatUnit() {
        return UnitFactory.getUnit(this.getClass().getSimpleName(), this.getName(), 0);
    }

    /**
     * Checks if two units only differ in attributes that only change during combat (resist / attack bonus and health)
     *
     * @param o
     *            The other unit.
     *
     * @return True if the only differences between the units are health, and attack / resist bonuses, false if not.
     */
    public boolean differsOnlyInCombatState(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Unit unit = (Unit) o;
        return this.getNonCombatUnit().equals(unit.getNonCombatUnit());
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "name='" + name + '\'' + ", health=" + health + ", attack="
                + attack + ", armor=" + armor + '}';
    }

    /**
     * {@inheritDoc} <br>
     * <br>
     * Check if a Unit equals another instance of Unit. <br>
     * <br>
     * This method does not take into account subclass specific traits such as number of times attacked, or how many
     * times the unit has been attacked. Unit specific stats are compared by comparing the resist and attack bonuses.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Unit unit = (Unit) o;
        return attack == unit.attack && armor == unit.armor && health == unit.health && name.equals(unit.name)
                && getAttackBonus() == unit.getAttackBonus() && getResistBonus() == unit.getResistBonus();
    }

    /**
     * {@inheritDoc} <br>
     * <br>
     * This method hashes name, attack, armor, health, and class name
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, attack, armor, health, getClass(), getResistBonus(), getAttackBonus());
    }

    /**
     * {@inheritDoc} <br>
     * <br>
     * Compares two units according to the specification of Comparable.compareTo()
     *
     * <br>
     * <br>
     * This is the order of the comparisons:
     * <ol>
     * <li>Class name (lexically ascending)</li>
     * <li>Given name (lexically ascending)</li>
     * <li>Attack (ascending)</li>
     * <li>Armor (ascending)</li>
     * <li>Health (ascending)</li>
     * <li>Attack Bonus (descending)</li>
     * <li>Resist Bonus (descending)</li>
     * </ol>
     */
    @Override
    public int compareTo(Unit other) {
        if (!this.getClass().equals(other.getClass())) {
            return this.getClass().getName().compareTo(other.getClass().getName());
        }
        if (!this.getName().equals(other.getName())) {
            return this.getName().compareTo(other.getName());
        }
        if (this.getAttack() != other.getAttack()) {
            return Integer.compare(this.getAttack(), other.getAttack());
        }
        if (this.getArmor() != other.getArmor()) {
            return Integer.compare(this.getArmor(), other.getArmor());
        }
        if (this.getHealth() != other.getHealth()) {
            return Integer.compare(this.getHealth(), other.getHealth());
        }
        if (this.getAttackBonus() != other.getAttackBonus()) {
            return Integer.compare(this.getAttackBonus(), other.getAttackBonus());
        }
        if (this.getResistBonus() != other.getResistBonus()) {
            return Integer.compare(this.getResistBonus(), other.getResistBonus());
        }
        return 0;
    }

    /**
     * Copies a class using its copy constructor.
     *
     * @return A copy of the instance using the class' copy constructor
     */
    public abstract Unit copy();

    /**
     * Resets stats about how many times the unit has been attacked or has attacked others.
     */
    public abstract void resetStats();

    /**
     * Calculates the base attack bonus of the unit with no terrain considerations.
     *
     * @return The attack bonus of the unit
     */
    public abstract int getAttackBonus();

    /**
     * Calculates the attack bonus of a unit with the terrain taken into account. The default method of any unit does
     * not apply any penalties or bonuses to the resist bonus.
     *
     * @param terrain
     *            The terrain.
     *
     * @return The attack bonus with terrain considerations.
     */
    public int getAttackBonus(TerrainEnum terrain) {
        return getResistBonus();
    }

    /**
     * Calculates the base resist bonus of the unit with no terrain considerations.
     *
     * @return The resist bonus of the unit
     */
    public abstract int getResistBonus();

    /**
     * Calculates the resist bonus of a unit with the terrain taken int account.
     *
     * @param terrain
     *            The terrain.
     *
     * @return The resist bonus with terrain considerations.
     */
    public int getResistBonus(TerrainEnum terrain) {
        return getResistBonus();
    }
}
