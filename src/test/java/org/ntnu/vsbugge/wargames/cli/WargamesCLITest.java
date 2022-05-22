package org.ntnu.vsbugge.wargames.cli;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.models.army.Army;
import org.ntnu.vsbugge.wargames.models.units.CavalryUnit;
import org.ntnu.vsbugge.wargames.models.units.CommanderUnit;
import org.ntnu.vsbugge.wargames.models.units.RangedUnit;

import java.io.IOException;

public class WargamesCLITest extends TestCase {

    public void testLoadTestData() {
        WargamesCLI cli = new WargamesCLI();
        try {
            cli.loadTestData();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            fail("Something went wrong in the loading of test data.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Something went wrong in the loading of test data.");
        }
    }

    public void testStartWithoutDataThrowsException() {
        WargamesCLI cli = new WargamesCLI();
        try {
            cli.start();
            fail("Starting without loading in data should throw an exception");
        } catch (IllegalStateException e) {
            assertEquals("Data was not loaded before starting the battle", e.getMessage());
        } catch (Exception e) {
            fail("IllegalArgument exception should be thrown when starting without loaded data");
        }
    }

    public void testUnitToSimpleString() {
        CavalryUnit test = new CavalryUnit("cav", 10, 11, 12);
        assertEquals("cav(CavalryUnit) at 10 hp", WargamesCLI.unitToSimpleString(test));
    }

    public void testArmyToSimpleString() {
        CavalryUnit cav = new CavalryUnit("cav", 10, 11, 12);
        RangedUnit rang = new RangedUnit("rang", 10, 11, 12);
        Army army = new Army("army");
        army.add(cav, 2);
        army.add(rang);

        String sb = "2x " + WargamesCLI.unitToSimpleString(cav) + "\n1x " + WargamesCLI.unitToSimpleString(rang);

        assertEquals(sb, WargamesCLI.armyToSimpleString(army));
    }

    public void testArmyToStringGroupRegardlessOfUnitSpecificStats() {
        CavalryUnit cav = new CavalryUnit("cav", 10, 11, 12);
        RangedUnit rang = new RangedUnit("rang", 10, 11, 12);
        CommanderUnit aMountain = new CommanderUnit("THE MOUNTAIN!", 10000, 0, 10000);
        Army army = new Army("army");
        army.add(cav, 2);
        army.add(rang, 2);

        // Altering class specific stats
        army.getAllUnits().get(0).attack(aMountain);
        army.getAllUnits().get(2).takeDamage(0);

        // Constructing how the string should look
        String sb = "2x " + WargamesCLI.unitToSimpleString(cav) + "\n2x " + WargamesCLI.unitToSimpleString(rang);

        assertEquals(sb, WargamesCLI.armyToSimpleString(army));
    }

}