package org.ntnu.vsbugge.wargames;

import org.ntnu.vsbugge.wargames.units.Unit;

/**
 * A class that represents a battle between two armies.
 */
public class Battle {
    private Army armyOne;
    private Army armyTwo;
    private int attackTurn = 0; // Zero for army one and two for armyTwos

    /**
     * Constructs a battle. This constructor creates an object where both armyOne og armyTwo are null.
     */
    public Battle() {

    }

    /**
     * A copy constructor for the battle class. This creates a new instance of the battle class with the same attributes as the copied battle.
     * The armies of the battle are also copied.
     * @param battle The battle to copy.
     */
    public Battle(Battle battle) {
        armyOne = (battle.armyOne == null) ? null : new Army(battle.armyOne);
        armyTwo = (battle.armyTwo == null) ? null : new Army(battle.armyTwo);
        attackTurn = battle.attackTurn;
    }

    /**
     * The constructor for an instance of the Battle class
     *
     * @param armyOne
     *            The fist army
     * @param armyTwo
     *            The second army
     */
    public Battle(Army armyOne, Army armyTwo) {
        this();
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
    }

    /**
     * This method simulates a battle one step at the time. The simulation is done by picking a random unit from the attacking army,
     * and a random unit form the defending army, and let the unit form the attacking army attack the unit from the defending army.
     * Which army is the defending and attacking army alternates with every simulation step where armyOne is the attacker in the first simulation step.
     * @return Returns null until a winner is determined. If a winner has been determined, the army of the winner is returned.
     * @throws RuntimeException
     *             Throws an exception if neither of the armies have any units, or if one of the armies are set to null.
     */
    public Army simulateStep() throws RuntimeException {
        // Throws a RunTimeException if one of the armies has not been added, or if neither of the armies has any units.
        if (armyOne == null || armyTwo == null || !(armyOne.hasUnits() || armyTwo.hasUnits())) {
            throw new RuntimeException("Both armies need to have units to simulate a battle");
        }

        // Check if any army is empty
        if (!armyTwo.hasUnits()) {
            return armyOne;
        }
        if (!armyOne.hasUnits()) {
            return armyTwo;
        }

        // Sets attacker and defender
        Army attacker = attackTurn == 0 ? armyOne : armyTwo;
        Army defender = attackTurn == 0 ? armyTwo : armyOne;

        // Gets random unit and removes dead unit if a dead unit was picked
        Unit attackUnit = attacker.getRandomUnit();
        if (attackUnit.getHealth() == 0) {
            attacker.removeAllDeadUnits();
            return simulateStep(); // Recursion to check if the army is empty again
        }

        // Gets random unit and removes dead unit if a dead unit was picked
        Unit defenderUnit = attacker.getRandomUnit();
        if (defenderUnit.getHealth() == 0) {
            defender.removeAllDeadUnits();
            return simulateStep(); // Recursion to check if the army is empty again
        }

        // Pit the two units against each other and remove the defender if it is killed.
        attackUnit.attack(defenderUnit);
        if (defenderUnit.getHealth() <= 0) {
            defender.remove(defenderUnit);
        }

        attackTurn = (attackTurn + 1) % 2;

        return null;
    }

    /**
     * Used to simulate a battle between two armies,
     *
     * <br>
     * <br>
     * When one army attacks the other, a random unit from the attacking army, is picked to attack a random unit from
     * the defending army. Whenever a unit falls bellow 1 health, the unit is removed from its army.
     *
     * <br>
     * <br>
     * The first army attacks first, then the second army attacks. This continues unit an army is left with 0 units.
     *
     * @return The army that won the battle
     *
     * @throws RuntimeException
     *             Throws an exception if neither of the armies have any units, or if one of the armies are set to null.
     */
    public Army simulate() throws RuntimeException {
        Army winner;
        // simulateStep returns null as long as no winner has been determined.
        while ((winner = simulateStep()) == null);
        return winner;
    }

    public Army getArmyOne() {
        return armyOne;
    }

    public void setArmyOne(Army armyOne) {
        this.armyOne = armyOne;
    }

    public Army getArmyTwo() {
        return armyTwo;
    }

    public void setArmyTwo(Army armyTwo) {
        this.armyTwo = armyTwo;
    }

    /**
     * @return A string representation of and instance of Battle.
     */
    @Override
    public String toString() {
        return "Battle{" + "armyOne=" + armyOne + ", armyTwo=" + armyTwo + '}';
    }
}
