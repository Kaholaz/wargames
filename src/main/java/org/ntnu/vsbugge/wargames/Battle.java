package org.ntnu.vsbugge.wargames;

import org.ntnu.vsbugge.wargames.units.Unit;

/**
 * A class that represents a battle between two armies.
 */
public class Battle {
    private final Army armyOne;
    private final Army armyTwo;

    /**
     * The constructor for an instance of the Battle class
     * @param armyOne The fist army
     * @param armyTwo The second army
     */
    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
    }

    /**
     * Used to simulate a battle between two armies,
     *
     * <br><br>
     * When one army attacks the other, a random unit from
     * the attacking army, is picked to attack a random unit
     * from the defending army. Whenever a unit falls bellow 1 health,
     * the unit is removed from its army.
     *
     * <br><br>
     * The first army attacks first, then the second army attacks.
     * This continues unit an army is left with 0 units.
     * @return The army that won the battle
     * @throws IllegalStateException Throws an exception if one of the armies
     * does not have any units at the start of the simulation.
     */
    public Army simulate() throws IllegalStateException {
        if (!armyOne.hasUnits() || !armyTwo.hasUnits()) {
            throw new IllegalStateException("Both armies need to have units to simulate a battle");
        }

        Army attacker = armyOne;
        armyOne.removeAllDeadUnits();

        Army defender = armyTwo;
        armyTwo.removeAllDeadUnits();

        while (armyOne.hasUnits() && armyTwo.hasUnits()) {
            Unit attackerUnit =  attacker.getRandomUnit();
            Unit defenderUnit = defender.getRandomUnit();

            attackerUnit.attack(defenderUnit);
            if (defenderUnit.getHealth() <= 0) {
                defender.remove(defenderUnit);
            }

            // Swaps attacked and defender
            Army temp = attacker;
            attacker = defender;
            defender = temp;
        }

        // The reassignment at the end of the while loop, makes the defender
        // the army that last attacked; ie the victor
        return defender;
    }

    /**
     * @return A string representation of and instance of Battle.
     */
    @Override
    public String toString() {
        return "Battle{" +
                "armyOne=" + armyOne +
                ", armyTwo=" + armyTwo +
                '}';
    }
}
