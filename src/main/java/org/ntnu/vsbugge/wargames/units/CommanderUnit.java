package org.ntnu.vsbugge.wargames.units;

/**
 * A class that represent a single commander unit
 *
 * @author vsbugge
 */
public class CommanderUnit extends CavalryUnit {
    /** Constant <code>DEFAULT_ATTACK=25</code> */
    protected static final int DEFAULT_ATTACK = 25;
    /** Constant <code>DEFAULT_ARMOR=15</code> */
    protected static final int DEFAULT_ARMOR = 15;

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
    public CommanderUnit(String name, int health) {
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
    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Takes an existing CommanderUnit instance and creates a new instance with the same attributes.
     *
     * Please note that stats regarding number of times taken damage and number of times attacked are reset, so the new
     * instance will not necessarily be exactly the same as the original.
     *
     * @param commanderUnit
     *            An instance of CommanderUnit
     */
    public CommanderUnit(CommanderUnit commanderUnit) {
        this(commanderUnit.getName(), commanderUnit.getHealth(), commanderUnit.getAttack(), commanderUnit.getArmor());
        this.numberOfTimesAttacked = commanderUnit.numberOfTimesAttacked;
    }

    /** {@inheritDoc} */
    @Override
    public CommanderUnit copy() {
        return new CommanderUnit(this);
    }
}
