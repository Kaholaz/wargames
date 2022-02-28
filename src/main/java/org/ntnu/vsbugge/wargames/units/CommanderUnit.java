package org.ntnu.vsbugge.wargames.units;

/**
 * A class that represent a single commander unit
 */
public class CommanderUnit extends CavalryUnit{
    protected static final int DEFAULT_ATTACK = 25, DEFAULT_ARMOR = 15;

    /**
     * Shorthand constructor that sets attack and armor to default values (15 and 10)
     * @param name The name of the unit
     * @param health Total health of the unit
     */
    public CommanderUnit(String name, int health) {
        this(name, health, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    /**
     * Calls the Unit constructor function
     * @param name Name of the unit
     * @param health Total health of the unit
     * @param attack Total Attack-damage of the unit
     * @param armor Total armor of the unit
     */
    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Takes an existing CommanderUnit instance and creates a new instance with the same attributes.
     *
     * Please note that stats regarding number of times taken damage and number of times attacked are reset,
     * so the new instance will not necessarily be exactly the same as the original.
     * @param commanderUnit An instance of CommanderUnit
     */
    public CommanderUnit(CommanderUnit commanderUnit) {
        this(
                commanderUnit.getName(),
                commanderUnit.getHealth(),
                commanderUnit.getAttack(),
                commanderUnit.getArmor()
        );
        this.hasAttacked = commanderUnit.hasAttacked;
    }

    @Override
    public CommanderUnit copy() {
        return new CommanderUnit(this);
    }
}
