package org.ntnu.vsbugge.wargames.models.battle;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.models.army.Army;
import org.ntnu.vsbugge.wargames.models.battle.Battle;
import org.ntnu.vsbugge.wargames.models.units.InfantryUnit;

public class BattleTest extends TestCase {

    public void testSimulateThrowsExceptionIfBothArmiesAreEmpty() {
        Army armyOne = new Army("ArmyOne");
        Army armyTwo = new Army("ArmyTwo");
        Battle battle = new Battle(armyOne, armyTwo);

        try {
            battle.simulate();
            fail("Should throw RuntimeException if one of the armies does not have units");
        } catch (RuntimeException e) {
            assertEquals("Both armies cannot be empty when simulating a battle.", e.getMessage());
        } catch (Exception e) {
            fail("Should throw RuntimeException if one of the armies does not have units");
        }
    }

    public void testSimulateReturnsVictor() {
        Army armyOne = new Army("ArmyOne");
        Army armyTwo = new Army("ArmyTwo");
        Battle battle = new Battle(armyOne, armyTwo);

        armyOne.add(new InfantryUnit("TestInfantry", 20));
        armyTwo.add(new InfantryUnit("TestInfantry", 1));
        assertEquals(armyOne, battle.simulate());
    }

    public void testToString() {
        Army armyOne = new Army("ArmyOne");
        Army armyTwo = new Army("ArmyTwo");
        Battle battle = new Battle(armyOne, armyTwo);

        assertEquals(
                "Battle{armyOne=Army{name='ArmyOne', armyTemplate={}}, armyTwo=Army{name='ArmyTwo', armyTemplate={}}}",
                battle.toString());
    }
}