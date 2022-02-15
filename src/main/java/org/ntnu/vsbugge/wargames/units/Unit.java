package org.ntnu.vsbugge.wargames.units;

import java.util.Objects;

/**
 * An abstract class for all units
 */
public abstract class Unit implements Comparable<Unit>{
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
     * <br><br>
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
    public final String getName() {
        return name;
    }

    /**
     * @return The remaining health of the unit. A negative or zero health-value signifies that the unit is dead.
     */
    public final int getHealth() {
        return health;
    }

    /**
     * @return The attack of teh unit
     */
    public final int getAttack() {
        return attack;
    }

    /**
     * @return The armor of the unit
     */
    public final int getArmor() {
        return armor;
    }

    /**
     * Makes the unit take damage to health-points. A negative or zero health-value signifies that the unit is dead.
     *
     * <br><br>
     * This method is included in favor of a setHealth method, to both encapsulate the health attribute and protect
     * it form being changed in an unforeseen way; and to create an interface that makes it possible to track the
     * number of times a unit has been attacked without introducing unexpected behaviour (ie incrementing a variable
     * tracking the number of times a unit has taken damage in a setHealth method, or in the getResistBonus method)
     *
     * <br><br>
     * If there exists a need to change the health of a unit in other ways (ie healing or resetting health), other
     * methods should be added to deal with these actions instead of implementing a setHealth method. This is to ensure
     * that setHealth (which arguably has a name that is more likely to get used if the user is not familiar with the
     * source code) is not used instead of takeDamage when dealing damage to a unit.
     *
     * @param damage The damage inflicted on the unit
     */
    public void takeDamage(int damage) {
        this.health = health - damage;
    }

    /**
     * This method should be used for debugging purpouses only. {@code WargamesCLI.unitToSimpleString(Unit unit)}
     * should be used to create a human-readable representation of a unit, ready to be printed to console.
     * @return A string representation of an instance (used for debugging)
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", attack=" + attack +
                ", armor=" + armor +
                '}';
    }

    /**
     * Creates a copy of a Unit instance.
     *
     * @param unit The original unit to copy
     * @return A copy of the original unit
     */
    public static Unit copyOf(Unit unit) {
        if (unit instanceof CommanderUnit) {
            return new CommanderUnit((CommanderUnit) unit);
        }
        if (unit instanceof CavalryUnit) {
            return new CavalryUnit((CavalryUnit) unit);
        }
        if (unit instanceof InfantryUnit) {
            return new InfantryUnit((InfantryUnit) unit);
        }
        if (unit instanceof RangedUnit) {
            return new RangedUnit((RangedUnit) unit);
        }

        throw new IllegalArgumentException("Cloning not supported for this unit");
    }

    /**
     * Check if a Unit equals another instance of Unit.
     * This does not take into account subclass specific traits such as
     * number of times attacked, or how many times the unit has been attacked.
     * @param o The object to compare to.
     * @return True if the objects are equal, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return attack == unit.attack && armor == unit.armor && health == unit.health && name.equals(unit.name);
    }

    /**
     * This method hashes name, attack, armor, health, and class name
     * @return The hashcode of the Unit.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, attack, armor, health, getClass());
    }

    /**
     * Compares two units according to the specification of Comparable.compareTo()
     *
     * <br><br>
     * This is the order of the comparisons:
     * <ol>
     *   <li>Class name (lexically ascending)</li>
     *   <li>Given name (lexically ascending)</li>
     *   <li>Attack (ascending)</li>
     *   <li>Armor (ascending)</li>
     *   <li>Health (ascending)</li>
     * </ol>
     *
     * @param other The Unit to compare to
     * @return A value less than 0 if this is deemed less than other, 0 if the objects are equivalent,
     * and a value more than 0 if other is deemed greater than 0
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
        return 0;
    }

    /**
     * Resets stats about how many times the unit has been attacked or has attacked others.
     */
    public abstract void resetStats();

    /**
     * @return The attack bonus of the unit
     */
    public abstract int getAttackBonus();

    /**
     * @return The resist bonus of the unit
     */
    public abstract int getResistBonus();
}
