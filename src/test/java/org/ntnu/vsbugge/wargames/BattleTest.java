package org.ntnu.vsbugge.wargames;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.units.CommanderUnit;
import org.ntnu.vsbugge.wargames.units.InfantryUnit;
import org.ntnu.vsbugge.wargames.units.RangedUnit;
import org.w3c.dom.ranges.Range;

import java.util.Arrays;
import java.util.LinkedList;

public class BattleTest extends TestCase {

    public void testSimulate() {
        Army armyOne = new Army("ArmyOne");
        Army armyTwo = new Army("ArmyTwo");
        Battle battle = new Battle(armyOne, armyTwo);

        try {
            battle.simulate();
            fail("Should throw IllegalStateException if one of the armies does not have units");
        }
        catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Both armies need to have units to simulate a battle");
        }
        catch (Exception e) {
            fail("Should throw IllegalStateException if one of the armies does not have units");
        }

        armyOne.add(new InfantryUnit("TestInfantry", 20));
        armyTwo.add(new InfantryUnit("TestInfantry", 1));
        assertEquals(battle.simulate(), armyOne);

        armyTwo.add(new CommanderUnit("TestCommander", 1000));
        assertEquals(battle.simulate(), armyTwo);

        try {
            battle.simulate();
            fail("Should throw IllegalStateException if one of the armies does not have units");
        }
        catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Both armies need to have units to simulate a battle");
        }
        catch (Exception e) {
            fail("Should throw IllegalStateException if one of the armies does not have units");
        }
    }

    public void testTestToString() {
        Army armyOne = new Army("ArmyOne");
        Army armyTwo = new Army("ArmyTwo");
        Battle battle = new Battle(armyOne, armyTwo);

        assertEquals(battle.toString(), "Battle{armyOne=Army{name='ArmyOne'}, armyTwo=Army{name='ArmyTwo'}}");
    }
}