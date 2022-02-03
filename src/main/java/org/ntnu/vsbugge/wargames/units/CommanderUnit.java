package org.ntnu.vsbugge.wargames.units;

public class CommanderUnit extends CavalryUnit{
    private static final int DEFAULT_ATTACK = 25, DEFAULT_ARMOR = 15;

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
}
