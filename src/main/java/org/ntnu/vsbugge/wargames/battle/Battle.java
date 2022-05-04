package org.ntnu.vsbugge.wargames.battle;

import org.ntnu.vsbugge.wargames.Army;
import org.ntnu.vsbugge.wargames.enums.TerrainEnum;
import org.ntnu.vsbugge.wargames.eventlisteners.EventType;
import org.ntnu.vsbugge.wargames.eventlisteners.Subject;
import org.ntnu.vsbugge.wargames.units.Unit;

/**
 * A class that represents a battle between two armies.
 */
public class Battle extends Subject {
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
        return simulate(0, 0);
    }

    public Army simulate(int msDelay, int nsDelay) throws RuntimeException {
        prepareSimulation();

        if (getWinner() != null) {
            notifyObservers(EventType.FINISH);
            return getWinner();
        }

        simulationPaused = false;
        while (!simulationPaused) {
            Army attacker = getAttacker();
            Army defender = getDefender();
            Army winner = attack(attacker, defender);

            if (winner != null) {
                notifyObservers(EventType.FINISH);
                return winner;
            }
            sleep(msDelay, nsDelay);
        }
        return null;
    }

    private Army getAttacker() {
        return attackTurn == 0 ? armyOne : armyTwo;
    }

    private Army getDefender() {
        return attackTurn == 0 ? armyTwo : armyOne;
    }

    public Army getWinner() {
        if (armyOne.hasUnits() && armyTwo.hasUnits()) {
            return null;
        }
        return armyOne.hasUnits() ? armyOne : armyTwo;
    }

    private void prepareSimulation() throws RuntimeException {
        // Throws a RunTimeException if one of the armies has not been added, or if neither of the armies has any units.
        if (armyOne == null || armyTwo == null) {
            throw new RuntimeException("Both armies need to be set to simulate a battle battle");
        }

        if (!(armyOne.hasUnits() || armyTwo.hasUnits())) {
            throw new RuntimeException("Both armies cannot be empty when simulating a battle.");
        }

        armyOne.removeAllDeadUnits();
        armyTwo.removeAllDeadUnits();
    }

    private Army attack(Army attacker, Army defender) {
        Unit attackUnit = attacker.getRandomUnit();
        Unit defenderUnit = defender.getRandomUnit();

        notifyObservers(EventType.UPDATE);
        attackUnit.attack(defenderUnit, terrain);
        if (defenderUnit.getHealth() <= 0) {
            defender.remove(defenderUnit);
            if (!defender.hasUnits()) {
                return attacker;
            }
        }

        attackTurn = (attackTurn + 1) % 2;
        return null;
    }

    private void sleep(int msDelay, int nsDelay) throws RuntimeException {
        if (msDelay != 0 || nsDelay != 0) {
            try {
                Thread.sleep(msDelay, nsDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getAttackTurn() {
        return attackTurn;
    }

    public void setAttackTurn(int attackTurn) {
        this.attackTurn = attackTurn;
    }

    public Army getArmyOne() {
        return armyOne;
    }

    public void setArmyOne(Army armyOne) {
        this.armyOne = (armyOne == null) ? null : new Army(armyOne);
    }

    public Army getArmyTwo() {
        return armyTwo;
    }

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

    /**
     * @return A string representation of and instance of Battle.
     */
    @Override
    public String toString() {
        return "Battle{" + "armyOne=" + armyOne + ", armyTwo=" + armyTwo + '}';
    }

    public void pauseSimulation() {
        simulationPaused = true;
    }
}
