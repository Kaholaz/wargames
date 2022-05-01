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

    public Battle(Battle battle) {
        armyOne = new Army(battle.armyOne);
        armyTwo = new Army(battle.armyTwo);
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

    public Army simulateStep() {
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
     * @throws IllegalStateException
     *             Throws an exception if one of the armies does not have any units at the start of the simulation.
     */
    public Army simulate() throws IllegalStateException {
        if (!armyOne.hasUnits() || !armyTwo.hasUnits()) {
            throw new IllegalStateException("Both armies need to have units to simulate a battle");
        }

        Army winner;
        // simulateStep returns null as long as no winner has been determined.
        while ((winner = simulateStep()) == null) {}

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
