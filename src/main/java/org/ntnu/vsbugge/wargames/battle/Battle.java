package org.ntnu.vsbugge.wargames.battle;

import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.units.Unit;
import org.ntnu.vsbugge.wargames.utils.enums.TerrainEnum;
import org.ntnu.vsbugge.wargames.utils.eventlisteners.EventType;
import org.ntnu.vsbugge.wargames.utils.eventlisteners.Subject;

/**
 * A class that represents a battle between two armies.
 *
 * @author vsbugge
 */
public class Battle implements Subject {
    private Army armyOne;
    private Army armyTwo;
    private TerrainEnum terrain = TerrainEnum.DEFAULT;
    private int attackTurn = 0; // Zero for army one and two for armyTwos
    private boolean simulationPaused;

    /**
     * Constructs a battle. This constructor creates an object where both armyOne og armyTwo are null.
     */
    public Battle() {
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
     * A copy constructor for the battle class. This creates a new instance of the battle class with the same attributes
     * as the copied battle. The armies of the battle are also copied.
     *
     * @param battle
     *            The battle to copy.
     */
    public Battle(Battle battle) {
        armyOne = (battle.armyOne == null) ? null : new Army(battle.armyOne);
        armyTwo = (battle.armyTwo == null) ? null : new Army(battle.armyTwo);
        attackTurn = battle.attackTurn;
        terrain = battle.terrain;
        simulationPaused = battle.simulationPaused;
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
     * The first army attacks first, then the second army attacks. This continues until an army is left with 0 units.
     *
     * @return The army that won the battle
     *
     * @throws java.lang.RuntimeException
     *             Throws an exception if neither of the armies have any units, or if one of the armies are set to null.
     */
    public Army simulate() throws RuntimeException {
        return simulate(0);
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
     * The first army attacks first, then the second army attacks. This continues until an army is left with 0 units.
     *
     * @param msDelay
     *            The delay (in milliseconds) between each attack.
     *
     * @return The army that won the battle
     *
     * @throws java.lang.RuntimeException
     *             Throws an exception if neither of the armies have any units, or if one of the armies are set to null.
     */
    public Army simulate(int msDelay) throws RuntimeException {
        prepareSimulation();

        if (getWinner() != null) {
            notifyEventListeners(EventType.FINISH);
            return getWinner();
        }

        simulationPaused = false;
        while (!simulationPaused) {
            Army attacker = getAttacker();
            Army defender = getDefender();
            Army winner = attack(attacker, defender);
            attackTurn = (attackTurn + 1) % 2;

            if (winner != null) {
                notifyEventListeners(EventType.FINISH);
                return winner;
            }
            sleep(msDelay);
        }
        return null;
    }

    /**
     * Pauses an ongoing simulation. The simulation can be restarted using the simulate method.
     */
    public void pauseSimulation() {
        simulationPaused = true;
    }

    /**
     * Gets the army that will attack in the next simulation step.
     *
     * @return The army that will attack in the next simulation step.
     */
    private Army getAttacker() {
        return attackTurn == 0 ? armyOne : armyTwo;
    }

    /**
     * Gets the army that will defend in the next simulation step.
     *
     * @return The army that will defend in the next simulation step.
     */
    private Army getDefender() {
        return attackTurn == 0 ? armyTwo : armyOne;
    }

    /**
     * Returns the winning army of the simulation if the simulation has concluded, otherwise it returns null.
     *
     * @return The winning army of the simulation if the simulation has concluded, otherwise it returns null.
     */
    public Army getWinner() {
        if (armyOne.hasUnits() && armyTwo.hasUnits()) {
            return null;
        }
        return armyOne.hasUnits() ? armyOne : armyTwo;
    }

    /**
     * Checks if the battle is in a valid state before a simulation, and removes all dead units from both armies.
     *
     * @throws java.lang.IllegalStateException
     *             Throws an exception if either of the armies are null, or if both of the armies are empty.
     */
    public void prepareSimulation() throws IllegalStateException {
        // Throws a IllegalStateException if one of the armies has not been added, or if neither of the armies has any
        // units.
        if (armyOne == null || armyTwo == null) {
            throw new IllegalStateException("Both armies need to be set to simulate a battle battle");
        }

        if (!(armyOne.hasUnits() || armyTwo.hasUnits())) {
            throw new IllegalStateException("Both armies cannot be empty when simulating a battle.");
        }

        armyOne.removeAllDeadUnits();
        armyTwo.removeAllDeadUnits();
    }

    /**
     * Pits an army against another army in one attack. This is done by picking one random unit from the attacking army
     * and make it attack a random unit from the defending unit. If the defending unit's health hits zero, the unit is
     * removed form its army.
     *
     * @param attacker
     *            The attacking army.
     * @param defender
     *            The defending army.
     *
     * @return If the attacking army attacked and killed the last unit in the defending unit, the attacking army is
     *         returned. Otherwise, null is returned.
     */
    private Army attack(Army attacker, Army defender) {
        Unit attackUnit = attacker.getRandomUnit();
        Unit defenderUnit = defender.getRandomUnit();

        attackUnit.attack(defenderUnit, terrain);
        if (defenderUnit.getHealth() <= 0) {
            defender.remove(defenderUnit);
            if (!defender.hasUnits()) {
                return attacker;
            }
        }
        notifyEventListeners(EventType.UPDATE);

        return null;
    }

    /**
     * Wraps the Thread.sleep(millis, nanos) method to ignore a delay of zero and catch InterruptedException and throw a
     * RuntimeException in stead.
     *
     * @param msDelay
     *            The delay (in milliseconds) to sleep for.
     *
     * @throws RuntimeException
     *             Throws RuntimeException if Thread.sleep produces a InterruptedException.
     */
    private void sleep(int msDelay) throws RuntimeException {
        if (msDelay != 0) {
            try {
                Thread.sleep(msDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Gets the attackTurn variable. This variable dictates whose turn it is to attack during the simulation.
     *
     * @return The attackTurn variable.
     */
    public int getAttackTurn() {
        return attackTurn;
    }

    /**
     * Sets the attackTurn variable. This variable dictates whose turn it is to attack during the simulation.
     *
     * @param attackTurn
     *            The new attackTurn variable.
     */
    public void setAttackTurn(int attackTurn) {
        this.attackTurn = attackTurn;
    }

    /**
     * Gets armyOne.
     *
     * @return armyOne.
     */
    public Army getArmyOne() {
        return armyOne;
    }

    /**
     * Sets armyOne.
     *
     * @param armyOne
     *            The new armyOne.
     */
    public void setArmyOne(Army armyOne) {
        this.armyOne = (armyOne == null) ? null : new Army(armyOne);
    }

    /**
     * Gets armyTwo.
     *
     * @return armyTwo.
     */
    public Army getArmyTwo() {
        return armyTwo;
    }

    /**
     * Sets armyTwo.
     *
     * @param armyTwo
     *            The new armyTwo.
     */
    public void setArmyTwo(Army armyTwo) {
        this.armyTwo = (armyTwo == null) ? null : new Army(armyTwo);
    }

    /**
     * Sets the terrain the battle will be conducted in. This will apply potential attack / resist bonuses to attacks
     * during the simulation.
     *
     * @param terrain
     *            The terrain of the battle.
     */
    public void setTerrain(TerrainEnum terrain) {
        this.terrain = terrain;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Battle{" + "armyOne=" + armyOne + ", armyTwo=" + armyTwo + '}';
    }
}
