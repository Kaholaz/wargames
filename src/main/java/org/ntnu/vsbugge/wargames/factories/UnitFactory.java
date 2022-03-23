package org.ntnu.vsbugge.wargames.units;

public class UnitFactory {

    /**
     * Creates a new Unit instance according to the given unit type
     * @param unitType The name of the type of the Unit. This name must be a name of a subclass of the Unit class.
     * @param name The name of the unit.
     * @param health The health of the unit.
     * @return A unit created by the given parameters. This unit is created using the constructor of the subclass with
     *         the name that matches the supplied unitType.
     */
    public Unit getUnit(String unitType, String name, int health) {
        return switch (unitType) {
            case "CavalryUnit" -> new CavalryUnit(name, health);
            case "CommanderUnit" -> new CommanderUnit(name, health);
            case "InfantryUnit" -> new InfantryUnit(name, health);
            case "RangedUnit" -> new RangedUnit(name, health);
            default -> throw new IllegalArgumentException(String.format("Unit type '%s' not found", unitType));
        };
    }

    /**
     * Creates a new Unit instance according to the given unit type
     * @param unitType The name of the type of the Unit. This name must be a name of a subclass of the Unit class.
     * @param name The name of the unit.
     * @param health The health of the unit.
     * @param attack The attack of the unit.
     * @param armor The armor of the unit.
     * @return A unit created by the given parameters. This unit is created using the constructor of the subclass with
     *         the name that matches the supplied unitType.
     */
    public Unit getUnit(String unitType, String name, int health, int attack, int armor) {
        return switch (unitType) {
            case "CavalryUnit" -> new CavalryUnit(name, health, attack, armor);
            case "CommanderUnit" -> new CommanderUnit(name, health, attack, armor);
            case "InfantryUnit" -> new InfantryUnit(name, health, attack, armor);
            case "RangedUnit" -> new RangedUnit(name, health, attack, armor);
            default -> throw new IllegalArgumentException(String.format("Unit type '%s' not found", unitType));
        };
    }
}
